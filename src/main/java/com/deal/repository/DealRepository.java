package com.deal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.deal.model.DealModel;

@Repository
public interface DealRepository extends MongoRepository<DealModel, String>,DealRepositoryCriteria {

}
