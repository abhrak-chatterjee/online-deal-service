package com.deal.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@JsonInclude(value = Include.NON_NULL)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DealDTO {
	
	@Getter
	@Setter
	private String dealId;

	@Getter
	@Setter
	private String userId;

	@Getter
	@Setter
	private String bankName;

	@Getter
	@Setter
	private String cardType;

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
	private Double finalAmount;
	
	@Getter
	@Setter
	private String benefit;

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
}
