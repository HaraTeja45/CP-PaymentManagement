package com.cp.paymentmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cp.paymentmanagement.bean.ResponseBean;
import com.cp.paymentmanagement.service.PaymentService;

public class PaymentsController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping
	public ResponseEntity<ResponseBean> processPayment() {
		
		
		

		return new ResponseEntity<>(new ResponseBean(), HttpStatus.OK);
	}

}
