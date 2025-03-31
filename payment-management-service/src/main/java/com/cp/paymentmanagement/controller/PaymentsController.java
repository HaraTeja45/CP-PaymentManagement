package com.cp.paymentmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cp.paymentmanagement.bean.PaymentRequestBean;
import com.cp.paymentmanagement.bean.ResponseBean;
import com.cp.paymentmanagement.service.PaymentService;

public class PaymentsController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping
	public ResponseEntity<ResponseBean> processPayment(@RequestBody PaymentRequestBean paymentRequestBean) {

		ResponseBean responseBean = paymentService.processPayment(paymentRequestBean);

		return new ResponseEntity<>(responseBean, HttpStatus.OK);
	}

}
