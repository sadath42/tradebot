package com.trade.bot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trade.bot.service.TradeBotService;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeBotService.class);

	@ExceptionHandler(value = BotException.class)
	public ResponseEntity<String> handleBotException(BotException botException) {
		LOGGER.error("Error while processing request {}", botException);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
