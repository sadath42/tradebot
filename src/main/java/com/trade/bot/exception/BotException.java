package com.trade.bot.exception;

public class BotException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BotException(String msg) {
		super(msg);
	}

	public BotException(String msg, Throwable e) {
		super(msg, e);
	}
}
