package com.deal.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.deal.constants.Constants;
import com.deal.facade.IDealFacade;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@Qualifier("userProfileFacade")
public class UserFacadeImpl implements IDealFacade {

	@Value("${user.profile.service.criteria.uri}")
	private String userServiceUri;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Map<String, Object> process(Object obj) throws Exception {
		Map<String, Object> map = new HashMap<>();
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(obj.toString(), header);
		try {
			List<Map<String, Object>> response = restTemplate
					.exchange(userServiceUri, HttpMethod.POST, request, List.class).getBody();
			if (response != null && !response.isEmpty()) {
				for (Map<String, Object> entry : response) {
					map.put((String) entry.get(Constants.USER_ID), entry.get(Constants.USER_ID));
				}
			}
		} catch (Exception e) {
			log.error(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			throw e;
		}
		return map;
	}

}
