package com.cp.paymentmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cp.paymentmanagement.model.CustomerRewards;

@Repository
public interface CustomerRewardsRepository extends JpaRepository<CustomerRewards, Long> {

	public CustomerRewards findByCustomerIdAndIsActive(Long customerId, Integer isactive);

}
