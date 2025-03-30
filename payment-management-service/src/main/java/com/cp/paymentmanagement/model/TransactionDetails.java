package com.cp.paymentmanagement.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TransactionDetails {

	@Id
	private Long transactionId;

	private String transactionRefNum;

	private String transactionStatus;

	private String transactionMode;

	@CreationTimestamp
	private Timestamp createdDateTime;

	@UpdateTimestamp
	private Timestamp updatedDateTime;

	private Integer isActive;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionRefNum() {
		return transactionRefNum;
	}

	public void setTransactionRefNum(String transactionRefNum) {
		this.transactionRefNum = transactionRefNum;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatuString) {
		this.transactionStatus = transactionStatuString;
	}

	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Timestamp getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Timestamp updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}

}
