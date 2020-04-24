//package com.deal.publisher;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.deal.constants.Constants;
//
//import lombok.extern.log4j.Log4j2;
//
//@Component
//@Log4j2
//public class EventPublisher {
//
//	@Autowired
//	private AmqpTemplate amqpTemplate;
//
//	@Value("${deal.exchange}")
//	private String dealExchange;
//
//	public void process(String message) throws Exception {
//		try {
//			amqpTemplate.convertAndSend(this.dealExchange, Constants.ROUTING_KEY, message);
//		} catch (Exception e) {
//			log.error(Constants.EXCEPTION_OCCURED_WHILE_PROCESSING, e.getMessage());
//			throw e;
//		}
//	}
//}
