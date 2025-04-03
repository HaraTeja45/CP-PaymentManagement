package com.cp.paymentmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cp.paymentmanagement.model.TransactionDetails;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {

	public TransactionDetails findByTransactionIdAndIsActive(Long transactionId, Integer isActive);

}
