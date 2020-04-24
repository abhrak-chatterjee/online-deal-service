package com.deal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.deal.constants.Constants;
import com.deal.dto.DealDTO;
import com.deal.facade.IDealFacade;
import com.deal.model.DealCriteria;
import com.deal.model.DealModel;
import com.deal.model.PromotionModel;
import com.deal.repository.DealRepository;
import com.deal.service.DealService;
import com.deal.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DealServiceImpl implements DealService {

	@Autowired
	private DealRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	@Qualifier("promotionFacade")
	private IDealFacade promotionFacade;

	@Autowired
	@Qualifier("userProfileFacade")
	private IDealFacade userProfileFacade;

	@Autowired
	@Qualifier("threadPoolTaskExecutor")
	private ExecutorService executor;

	/**
	 * Method used for Saving the Service Configuration Document!.
	 * 
	 * @return
	 */
	@Override
	public DealDTO processTransformation(DealDTO dealDTO) throws Exception {
		try {
			if ((dealDTO.getUserId().isEmpty()) || (dealDTO.getBankName().isEmpty())
					|| (dealDTO.getCardType().isEmpty()) || (dealDTO.getFlightId().isEmpty())) {
				throw new Exception(Constants.INVALID_REQUEST_STR);
			}
			saveData(prepareObject(dealDTO));
			dealDTO = calculatePromotion(dealDTO);
		} catch (Exception e) {
			log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			throw e;
		}
		return dealDTO;
	}

	private DealDTO calculatePromotion(DealDTO dealDTO) throws Exception {
		try {
			Map<String, Object> userDataMap = getUserProfileDataMap(getUserDataMap(dealDTO)).get();
			List<PromotionModel> promotionDataList = getPromotionDataMap(getPromotionDataMap(dealDTO)).get();
			if (!userDataMap.isEmpty() && !promotionDataList.isEmpty()) {
				if (userDataMap.get(dealDTO.getUserId()).toString().equals(dealDTO.getUserId())) {
					for (PromotionModel promotionModel : promotionDataList) {
						if ((promotionModel.getBankName().equals(dealDTO.getBankName()))
								&& (promotionModel.getCardType().equals(dealDTO.getCardType()))
								&& (promotionModel.getCardType().equals(dealDTO.getCardType()))
								&& (promotionModel.getFlightClass().equals(dealDTO.getFlightClass()))) {
							dealDTO.setFinalAmount(setFinalAmount(dealDTO, promotionModel, Constants.USER_EXISTS));
							dealDTO.setBenefit(promotionModel.getBenefitType().get(0));
						}
					}

				} else {
					for (PromotionModel promotionModel : promotionDataList) {
						if ((promotionModel.getBankName().equals(dealDTO.getBankName()))
								&& (promotionModel.getCardType().equals(dealDTO.getCardType()))
								&& (promotionModel.getCardType().equals(dealDTO.getCardType()))
								&& (promotionModel.getFlightClass().equals(dealDTO.getFlightClass()))) {
							dealDTO.setFinalAmount(
									setFinalAmount(dealDTO, promotionModel, Constants.USER_DOES_NOT_EXIST));
							dealDTO.setBenefit(promotionModel.getBenefitType().get(0));
						}
					}
				}
			}
		} catch (Exception e) {
			log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			throw e;
		}
		return dealDTO;
	}

	private Double setFinalAmount(DealDTO dealDTO, PromotionModel promotionModel, int flag) {
		if (flag == 1) {
			return (dealDTO.getFlightAmount()
					- (Math.min((dealDTO.getFlightAmount() * promotionModel.getPromotionPercentage()),
							promotionModel.getMaximumPromotionAmount()))
					- promotionModel.getExistingUserPromotionAmount() - (promotionModel.getBenefitAmount()));
		}
		return (dealDTO.getFlightAmount()
				- (Math.min((dealDTO.getFlightAmount() * promotionModel.getPromotionPercentage()),
						promotionModel.getMaximumPromotionAmount()))
				- (promotionModel.getBenefitAmount()));
	}

	@SuppressWarnings("unchecked")
	@Async("threadPoolTaskExecutor")
	private CompletableFuture<List<PromotionModel>> getPromotionDataMap(Map<String, Object> promotionDataMap) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return (List<PromotionModel>) promotionFacade.process(mapper.writeValueAsString(promotionDataMap));
			} catch (Exception e) {
				log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e);
			}
			return null;
		}, executor);
	}

	@SuppressWarnings("unchecked")
	@Async("threadPoolTaskExecutor")
	private CompletableFuture<Map<String, Object>> getUserProfileDataMap(Map<String, Object> userDataMap) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return (Map<String, Object>) userProfileFacade.process(mapper.writeValueAsString(userDataMap));
			} catch (Exception e) {
				log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e);
			}
			return null;
		}, executor);
	}

	private Map<String, Object> getUserDataMap(DealDTO dealDTO) {
		Map<String, Object> requestMap = new HashMap<>();
		try {
			requestMap.put(Constants.USER_ID, dealDTO.getUserId());
		} catch (Exception e) {
			log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			throw e;
		}
		return requestMap;
	}

	private Map<String, Object> getPromotionDataMap(DealDTO dealDTO) throws Exception {
		Map<String, Object> requestMap = new HashMap<>();
		try {
			requestMap.put(Constants.BANK_NAME, dealDTO.getBankName());
			requestMap.put(Constants.CARD_TYPE, dealDTO.getCardType());
			requestMap.put(Constants.FLIGHT_CLASS, dealDTO.getFlightClass());
		} catch (Exception e) {
			log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			throw e;
		}
		return requestMap;
	}

	private DealModel saveData(DealModel model) {
		return repository.save(model);
	}

	/**
	 * Method used for searching the profile by criteria!.
	 */
	@Override
	public List<DealModel> getUserDealsByCriteria(DealCriteria criteria) throws Exception {
		List<DealModel> list = null;
		try {
			list = repository.findByCriteria(criteria);
		} catch (Exception e) {
			log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			throw e;
		}
		return list;
	}

	/**
	 * Method used for preparing the Object!.
	 * 
	 * @param dealDTO
	 * @return
	 */
	private DealModel prepareObject(DealDTO dealDTO) {
		DealModel model = new DealModel();
		try {
			model = modelMapper.map(dealDTO, DealModel.class);
			if (!StringUtils.isEmpty(dealDTO.getDealId())) {
				model.setUpdatedDate(CommonUtil.getCurrentDateInString());
				model.setUpdatedBy(Constants.DEAL_SERVICE);
			} else {
				model.setCreatedDate(CommonUtil.getCurrentDateInString());
				model.setCreatedBy(Constants.DEAL_SERVICE);
			}
		} catch (Exception e) {
			log.info(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
			throw e;
		}
		return model;
	}

}
