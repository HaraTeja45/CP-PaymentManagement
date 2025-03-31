package com.cp.paymentmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cp.paymentmanagement.model.TransactionDetails;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {

	public TransactionDetails findByTransactionIdAndIsActive(Long transactionId, Integer isActive);

}
