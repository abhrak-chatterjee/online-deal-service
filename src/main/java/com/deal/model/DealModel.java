package com.deal.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Document(value = "incoming_order")
@JsonInclude(value = Include.NON_NULL)
@ToString
@NoArgsConstructor
public class DealModel implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
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
