package com.cp.paymentmanagement.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CustomerRewards {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerRewardKey;

	private Long customerId;

	private Integer rewardPoints;

	@UpdateTimestamp
	private Timestamp lastUpdatedDateTime;

	@CreationTimestamp
	private Timestamp createdDateTime;

	private Integer isActive;

	public Long getCustomerRewardKey() {
		return customerRewardKey;
	}

	public void setCustomerRewardKey(Long customerRewardKey) {
		this.customerRewardKey = customerRewardKey;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Integer getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}

	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

}
