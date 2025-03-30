package com.cp.paymentmanagement.service;

import com.cp.paymentmanagement.bean.ResponseBean;

public interface RewardsService {
	
	public ResponseBean getRewardsDetails(Long customerId);
	
	
	public ResponseBean addOrUpdateRewardsDetails(Long customerId,Integer rewardPoints);
	

}
