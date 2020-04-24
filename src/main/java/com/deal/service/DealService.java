package com.deal.service;

import java.util.List;

import com.deal.dto.DealDTO;
import com.deal.model.DealCriteria;
import com.deal.model.DealModel;

public interface DealService {

	public DealDTO processTransformation(DealDTO dealDTO) throws Exception;
	public List<DealModel> getUserDealsByCriteria(DealCriteria criteria) throws Exception;
}
