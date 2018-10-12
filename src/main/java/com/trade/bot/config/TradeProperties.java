package com.trade.bot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.trade.bot.service.PasswordEncryptor;

@ConfigurationProperties
@Component
public class TradeProperties {
	@Autowired
	private PasswordEncryptor passwordEncryptor;

	private String[] symbols;

	private String apiKey;
	private String userId;

	private String apiSecret;

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = passwordEncryptor.decrypt(apiSecret);
	}

	public String[] getSymbols() {
		return symbols;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = passwordEncryptor.decrypt(apiKey);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = passwordEncryptor.decrypt(userId);
	}

	public void setSymbols(String[] symbols) {
		this.symbols = symbols;
	}

}
