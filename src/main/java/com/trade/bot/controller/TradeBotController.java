package com.trade.bot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trade.bot.service.TradeBotService;

@RestController
public class TradeBotController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TradeBotController.class);
	@Autowired
	private TradeBotService tradeBotService;

	@GetMapping("/requestToken")
	public ResponseEntity<String> requestAccessToken(@RequestParam("request_token") String requestToken,
			@RequestParam("status") String status) {
		LOGGER.info("Recive token referesh request");
		tradeBotService.genrateSeesion(requestToken, status);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/login")
	public ResponseEntity<String> getLoginUrl() {
		return new ResponseEntity<>(tradeBotService.getLoginUrl(),HttpStatus.OK);
	} 
}
