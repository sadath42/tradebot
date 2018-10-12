package com.trade.bot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zerodhatech.kiteconnect.KiteConnect;

@Configuration
public class TradeBotConfig {
	@Autowired
	private TradeProperties tradeProperties;

	@Bean
	public KiteConnect kiteConnect() {
		KiteConnect kiteConnect = new KiteConnect(tradeProperties.getApiKey());
		kiteConnect.setUserId(tradeProperties.getUserId());
		return kiteConnect;

	}

}
