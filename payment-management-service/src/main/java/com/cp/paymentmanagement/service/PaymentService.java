package com.cp.paymentmanagement.service;

import com.cp.paymentmanagement.bean.PaymentRequestBean;
import com.cp.paymentmanagement.bean.ResponseBean;

public interface PaymentService {

	public ResponseBean processPayment(PaymentRequestBean paymentRequestBean);  
	
	
}
