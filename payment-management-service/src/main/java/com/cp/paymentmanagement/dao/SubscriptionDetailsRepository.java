package com.cp.paymentmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cp.paymentmanagement.model.SubscriptionDetails;

public interface SubscriptionDetailsRepository extends JpaRepository<SubscriptionDetails, Long> {

	public SubscriptionDetails findByCustomerIdAndPlanStatusAndIsActive(Long customerId, String planStatus,
			Integer isActive);

}
