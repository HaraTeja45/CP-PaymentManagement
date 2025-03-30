package com.cp.paymentmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cp.paymentmanagement.model.SpinActivity;

public interface SpinActivityRepository extends JpaRepository<SpinActivity, Long> {

	public SpinActivity findByCustomerIdAndIsActive(Long customerId, Integer isActive);

}
