package com.cp.paymentmanagement.bean;

import java.util.UUID;

public class PaymentResponse {

	private UUID transactionId;

	private String status;

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
