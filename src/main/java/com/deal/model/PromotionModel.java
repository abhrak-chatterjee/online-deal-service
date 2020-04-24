package com.deal.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@JsonInclude(value = Include.NON_NULL)
@ToString
@NoArgsConstructor
public class PromotionModel implements Serializable {
	
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String promotionId;
	
	@Getter
	@Setter
	private String flightId;
	
	@Getter
	@Setter
	private Double flightAmount;

	@Getter
	@Setter
	private String flightClass;
	
	@Getter
	@Setter
	private Double promotionPercentage;
	
	@Getter
	@Setter
	private Double maximumPromotionAmount;
	
	@Getter
	@Setter
	private Double existingUserPromotionAmount;
	
	@Getter
	@Setter
	private String bankName;

	@Getter
	@Setter
	private String cardType;
	
	@Getter
	@Setter
	private Double minimumAmountForPromotion;
	
	@Getter
	@Setter
	private List<String> benefitType;
	
	@Getter
	@Setter
	private Double benefitAmount;

	@Getter
	@Setter
	private String createdBy;

	@Getter
	@Setter
	private String createdDate;

	@Getter
	@Setter
	private String updatedDate;

	@Getter
	@Setter
	private String updatedBy;

	@Getter
	@Setter
	private Integer status;

}
