package com.cp.paymentmanagement.config;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.MapWriterAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cp.paymentmanagement.dao.CustomerRewardsRepository;
import com.cp.paymentmanagement.dao.SpinActivityRepository;
import com.cp.paymentmanagement.helper.PaymentServiceConstant;
import com.cp.paymentmanagement.model.CustomerRewards;
import com.cp.paymentmanagement.model.SpinActivity;

@Configuration
public class CacheConfig {

	@Autowired
	@Qualifier("redissonClient")
	private RedissonClient redissonClient;

	@Autowired
	private CustomerRewardsRepository customerRewardsRepository;

	@Autowired
	private SpinActivityRepository spinActivityRepository;

	@Bean("rewardPointsCache")
	RMapCache<String, Integer> rewardPointsCache() {

		MapWriterAsync<String, Integer> writer = new MapWriterAsync<String, Integer>() {

			@Override
			public CompletionStage<Void> write(Map<String, Integer> map) {

				return CompletableFuture.runAsync(() -> {

					map.forEach((userId, points) -> {
						userId = userId.split(":")[1];

						CustomerRewards customerRewards = customerRewardsRepository
								.findByCustomerIdAndIsActive(Long.valueOf(userId), PaymentServiceConstant.ISACTIVE);

						if (customerRewards != null) {
							customerRewards.setRewardPoints(Integer.sum(points, customerRewards.getRewardPoints()));
							customerRewardsRepository.save(customerRewards);
						} else {
							CustomerRewards newCustomerRewards = new CustomerRewards();

							newCustomerRewards.setCustomerId(Long.valueOf(userId));

							newCustomerRewards.setIsActive(PaymentServiceConstant.ISACTIVE);
							newCustomerRewards.setRewardPoints(points);
							customerRewardsRepository.save(newCustomerRewards);
						}

					});

				});
			}

			@Override
			public CompletionStage<Void> delete(Collection<String> keys) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		MapOptions<String, Integer> options = MapOptions.<String, Integer>defaults().writerAsync(writer)
				.writeMode(MapOptions.WriteMode.WRITE_BEHIND).writeBehindBatchSize(5).writeBehindDelay(15000);

		return redissonClient.getMapCache("rewardPointsCache", options);

	}

	@Bean("spinCache")
	RMapCache<String, Integer> spinCache() {

		MapWriterAsync<String, Integer> writer = new MapWriterAsync<String, Integer>() {

			@Override
			public CompletionStage<Void> write(Map<String, Integer> map) {

				return CompletableFuture.runAsync(() -> {

					map.forEach((userId, spinCount) -> {

						userId = userId.split(":")[1];

						SpinActivity existSpinActivity = spinActivityRepository
								.findByCustomerIdAndIsActive(Long.valueOf(userId), PaymentServiceConstant.ISACTIVE);

						if (existSpinActivity != null) {
							existSpinActivity.setSpinCount(spinCount);
							spinActivityRepository.save(existSpinActivity);
						} else {

							SpinActivity newSpinActivity = new SpinActivity();
							newSpinActivity.setIsActive(PaymentServiceConstant.ISACTIVE);
							newSpinActivity.setSpinCount(spinCount);
							newSpinActivity.setCustomerId(Long.valueOf(userId));

							spinActivityRepository.save(newSpinActivity);

						}

					});

				});
			}

			@Override
			public CompletionStage<Void> delete(Collection<String> keys) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		MapOptions<String, Integer> options = MapOptions.<String, Integer>defaults().writerAsync(writer)
				.writeMode(MapOptions.WriteMode.WRITE_BEHIND).writeBehindBatchSize(5).writeBehindDelay(5000);

		return redissonClient.getMapCache("spinCache", options);

	}

}
