package com.deal.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.deal.constants.Constants;
import com.deal.facade.IDealFacade;
import com.deal.model.PromotionModel;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@Qualifier("promotionFacade")
public class PromotionFacadeImpl implements IDealFacade {

	@Value("${promotion.service.criteria.uri}")
	private String promotionServiceUri;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<PromotionModel> process(Object obj) throws Exception {
		List<PromotionModel> response = null;
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(obj.toString(), header);
		try {
			response = restTemplate.exchange(promotionServiceUri, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<PromotionModel>>() {
					}).getBody();
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			throw e;
		}
		return response;
	}

}
