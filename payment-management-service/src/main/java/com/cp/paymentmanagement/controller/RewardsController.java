package com.cp.paymentmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cp.paymentmanagement.bean.ResponseBean;
import com.cp.paymentmanagement.service.RewardsService;

@RestController
public class RewardsController {

	@Autowired
	private RewardsService rewardsService;

	@GetMapping("/v1/payment/getrewards")
	public ResponseEntity<ResponseBean> getRewardDetailsByCustomerId(@RequestParam(required = true) Long customerId) {

		ResponseBean responseBean = rewardsService.getRewardsDetails(customerId);

		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@PutMapping("/v1/payment/updaterewards")
	public ResponseEntity<ResponseBean> updateCacehDetailsByCustomerId(@RequestParam(required = true) Long customerId,
			@RequestParam(required = true) Integer rewardPoints) {

		ResponseBean responseBean = rewardsService.addOrUpdateRewardsDetails(customerId, rewardPoints);

		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

}
