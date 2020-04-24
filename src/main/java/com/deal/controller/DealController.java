package com.deal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deal.constants.Constants;
import com.deal.dto.DealDTO;
import com.deal.model.DealCriteria;
import com.deal.model.DealModel;
import com.deal.service.DealService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@Api(value = "This API is created for Deal API Specs!")
@CrossOrigin
public class DealController {

	@Autowired
	private DealService service;

	/**
	 * This method is used for getting the User Deal Records by Criteria!
	 * 
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/api-service/deal/v1/criteria", produces = "application/json")
	@ApiOperation(httpMethod = "POST", value = "This method is used for getting the Users by Specified Criteria! ", produces = "application/json", response = DealModel.class)
	public ResponseEntity getUserDealsByCriteria(@RequestBody(required = true) DealCriteria criteria) throws Exception {
		ResponseEntity response = null;
		try {
			response = new ResponseEntity(service.getUserDealsByCriteria(criteria), HttpStatus.OK);

		} catch (Exception e) {
			log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			response = new ResponseEntity(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, HttpStatus.BAD_REQUEST);
			throw e;
		}
		return response;
	}

	/**
	 * This method is used for getting the Reason Codes by Criteria!
	 * 
	 * @param dealDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/api-service/deal/v1/promotion", produces = "application/json")
	@ApiOperation(httpMethod = "POST", value = "This method is used for displaying promotion and benefit!.", produces = "application/json", response = DealDTO.class)
	public ResponseEntity calculatePromotion(@RequestBody(required = true) DealDTO dealDTO) throws Exception {
		ResponseEntity response = null;
		try {
			response = new ResponseEntity(service.processTransformation(dealDTO), HttpStatus.OK);
		} catch (Exception e) {
			log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			response = new ResponseEntity(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, HttpStatus.BAD_REQUEST);
			throw e;
		}
		return response;
	}

}
