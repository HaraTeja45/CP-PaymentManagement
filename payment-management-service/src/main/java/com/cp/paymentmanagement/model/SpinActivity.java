package com.cp.paymentmanagement.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SpinActivity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long spinActivityKey;

	private Long customerId;

	private Integer spinCount;

	@CreationTimestamp
	private Timestamp createdDateTime;

	@UpdateTimestamp
	private Timestamp updatedDateTime;

	private Integer isActive;

	public Long getSpinActivityKey() {
		return spinActivityKey;
	}

	public void setSpinActivityKey(Long spinActivityKey) {
		this.spinActivityKey = spinActivityKey;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public Integer getSpinCount() {
		return spinCount;
	}

	public void setSpinCount(Integer spinCount) {
		this.spinCount = spinCount;
	}

}
