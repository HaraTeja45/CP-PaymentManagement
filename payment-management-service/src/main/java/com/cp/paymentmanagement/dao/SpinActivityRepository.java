package com.cp.paymentmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cp.paymentmanagement.model.SpinActivity;

@Repository
public interface SpinActivityRepository extends JpaRepository<SpinActivity, Long> {

	public SpinActivity findByCustomerIdAndIsActive(Long customerId, Integer isActive);

}
