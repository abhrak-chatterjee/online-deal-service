package com.deal.repository;

import java.util.List;

import com.deal.model.DealCriteria;
import com.deal.model.DealModel;

@FunctionalInterface
public interface DealRepositoryCriteria {

	public List<DealModel> findByCriteria(DealCriteria criteria)
			throws Exception;
}
