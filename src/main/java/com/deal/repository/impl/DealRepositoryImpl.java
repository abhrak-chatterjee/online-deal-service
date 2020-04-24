package com.deal.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.deal.constants.Constants;
import com.deal.model.DealCriteria;
import com.deal.model.DealModel;
import com.deal.repository.DealRepositoryCriteria;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class DealRepositoryImpl implements DealRepositoryCriteria {

	@Autowired
	private MongoTemplate repository;

	@Override
	public List<DealModel> findByCriteria(DealCriteria criteria) throws Exception {
		List<DealModel> list = null;
		try {
			list = repository.find(getWhereClause(criteria), DealModel.class);
		} catch (Exception e) {
			log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			throw e;
		}
		return list;
	}

	/**
	 * Method used for Preparing the Where Clause
	 * 
	 * @param criteria
	 * @param builder
	 * @param model
	 * @return
	 */
	private Query getWhereClause(DealCriteria criteria) {
		Query whereClause = new Query();
		if (!StringUtils.isEmpty(criteria.getUserId())) {
			whereClause.addCriteria(Criteria.where(Constants.USER_ID).is(criteria.getUserId()));
		}
		if (criteria.getStatus() != null && criteria.getStatus() > 0) {
			whereClause.addCriteria(Criteria.where(Constants.STATUS).is(criteria.getStatus()));
		}
		return whereClause;
	}

}
