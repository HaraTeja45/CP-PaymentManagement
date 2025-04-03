package com.cp.paymentmanagement.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cp.paymentmanagement.bean.PaymentRequestBean;
import com.cp.paymentmanagement.bean.PaymentResponse;
import com.cp.paymentmanagement.bean.ResponseBean;
import com.cp.paymentmanagement.dao.TransactionDetailsRepository;
import com.cp.paymentmanagement.helper.PaymentServiceConstant;
import com.cp.paymentmanagement.model.TransactionDetails;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Value("${paymentgateway.flag:true}")
	private boolean paymentgatewayFlag;

	@Autowired
	private TransactionDetailsRepository transactionDetailsRepository;

	@Override
	public ResponseBean processPayment(PaymentRequestBean paymentRequestBean) {

		UUID transactionId = UUID.randomUUID();

		TransactionDetails transactionDetails = new TransactionDetails();

		transactionDetails.setIsActive(PaymentServiceConstant.ISACTIVE);

		transactionDetails.setTransactionMode(paymentRequestBean.getPaymentMethod());

		transactionDetails.setTransactionStatus(paymentgatewayFlag ? "SUCCESS" : "FAILED");

		transactionDetails.setTransactionRefNum(transactionId.toString());

		transactionDetailsRepository.save(transactionDetails);

		PaymentResponse paymentResponse = new PaymentResponse();

		paymentResponse.setStatus(paymentgatewayFlag ? "SUCCESS" : "FAILED");

		paymentResponse.setTransactionId(transactionId);

		ResponseBean responseBean = new ResponseBean();
		responseBean.setPayload(paymentResponse);
		responseBean.setStatus(paymentResponse.getStatus());

		return responseBean;
	}

}
