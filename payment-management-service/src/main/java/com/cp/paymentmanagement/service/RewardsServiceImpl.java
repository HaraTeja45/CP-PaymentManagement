package com.cp.paymentmanagement.service;

import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cp.paymentmanagement.bean.ResponseBean;
import com.cp.paymentmanagement.dao.CustomerRewardsRepository;
import com.cp.paymentmanagement.dao.SpinActivityRepository;
import com.cp.paymentmanagement.dao.SubscriptionDetailsRepository;
import com.cp.paymentmanagement.helper.PaymentServiceConstant;
import com.cp.paymentmanagement.model.CustomerRewards;
import com.cp.paymentmanagement.model.SpinActivity;
import com.cp.paymentmanagement.model.SubscriptionDetails;

@Service
public class RewardsServiceImpl implements RewardsService {

	@Autowired
	@Qualifier("rewardPointsCache")
	RMapCache<String, Integer> rewardsCache;
	@Autowired
	@Qualifier("spinCache")
	RMapCache<String, Integer> spinCache;

	@Autowired
	@Qualifier("redissonClient")
	RedissonClient redissonClient;

	@Autowired
	SpinActivityRepository spinActivityRepository;

	@Autowired
	private CustomerRewardsRepository customerRewardsRepository;

	@Autowired
	private SubscriptionDetailsRepository subscriptionDetailsRepository;

	@Value("${redis.rewards.cacheKey.prefix}")
	private String rewardsCacheKeyPefix;

	@Value("${redis.spin.cacheKey.prefix}")
	private String spinCacheKeyPefix;

	@Value("${redis.cache.ttl}")
	private Long cacheTTL;

	@Override
	public ResponseBean getRewardsDetails(Long customerId) {

		ResponseBean responseBean = new ResponseBean();

		Object object = rewardsCache.get(rewardsCacheKeyPefix + customerId);


		if (object == null) {

			CustomerRewards customerRewards = customerRewardsRepository.findByCustomerIdAndIsActive(customerId,
					PaymentServiceConstant.ISACTIVE);
			responseBean.setPayload(customerRewards);
		} else {
			responseBean.setPayload(object);
		}

		return responseBean;
	}

	@Override
	public ResponseBean addOrUpdateRewardsDetails(Long customerId, Integer rewardPoints) {

		ResponseBean responseBean = new ResponseBean();

		RLock lock = redissonClient.getLock(rewardsCacheKeyPefix + customerId);

		try {
			boolean isLockAcquired = lock.tryLock(10, 30, TimeUnit.SECONDS);

			if (isLockAcquired) {

				boolean canSpin = canSpin(customerId);

				if (canSpin) {
					Integer updatedRewardPoints = rewardsCache.compute(rewardsCacheKeyPefix + customerId,
							(key, currVal) -> {

								if (currVal != null) {
									return Integer.sum(currVal, rewardPoints);
								} else {
									return rewardPoints;
								}
							});

					rewardsCache.put(rewardsCacheKeyPefix + customerId, updatedRewardPoints, cacheTTL,
							TimeUnit.MILLISECONDS);
				} else {
					// throw error

				}

			}

		} catch (InterruptedException e) {
			// throw custom exception
		} catch (Exception e) {
// throw custom exception
		} finally {

			if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
				lock.unlock();

			}
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", "Success");
		jsonObject.put("Message", "Successfully added in cache");

		responseBean.setPayload(jsonObject);

		return responseBean;
	}

	public boolean canSpin(Long customerId) {

		try {

			SubscriptionDetails subscriptionDetails = subscriptionDetailsRepository
					.findByCustomerIdAndPlanStatusAndIsActive(customerId, PaymentServiceConstant.PLAN_STS_ACTIVE,
							PaymentServiceConstant.ISACTIVE);

			boolean isSubscriber = subscriptionDetails != null ? true : false;

			int spinLimit = 10;

			if (isSubscriber) {

				String spinCacheKey = spinCacheKeyPefix + customerId;

				Integer currentSpinCount = spinCache.getOrDefault(spinCacheKey, 0);

				if (currentSpinCount < spinLimit) {
					spinCache.put(spinCacheKey, currentSpinCount + 1, 60000, TimeUnit.MILLISECONDS);

					return true;

				} else {

					return false;

				}

			} else {

				SpinActivity existSpinActivity = spinActivityRepository.findByCustomerIdAndIsActive(customerId,
						PaymentServiceConstant.ISACTIVE);

				if (existSpinActivity != null) {

					Integer spinCount = existSpinActivity.getSpinCount();

					if (spinCount < spinLimit) {
						existSpinActivity.setSpinCount(spinCount++);
						spinActivityRepository.save(existSpinActivity);
					} else {
						return false;
					}

				} else {

					SpinActivity newSpinActivity = new SpinActivity();
					newSpinActivity.setIsActive(PaymentServiceConstant.ISACTIVE);
					newSpinActivity.setSpinCount(1);
					newSpinActivity.setCustomerId(customerId);

					spinActivityRepository.save(newSpinActivity);

				}

				return true;

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;

	}

}
