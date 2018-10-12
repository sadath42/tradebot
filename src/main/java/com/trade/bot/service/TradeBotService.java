package com.trade.bot.service;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.trade.bot.config.TradeProperties;
import com.trade.bot.exception.BotException;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;

@Service
public class TradeBotService {

	@Autowired
	private KiteConnect kiteConnect;

	@Autowired
	private TradeProperties tradeProperties;

	public void genrateSeesion(String requestToken, String status) {
		if (StringUtils.isEmpty(requestToken)) {
			throw new BotException("Request token is null");
		}
		try {
			User userModel = kiteConnect.generateSession(requestToken, tradeProperties.getApiSecret());
			kiteConnect.setAccessToken(userModel.accessToken);
			kiteConnect.setPublicToken(userModel.publicToken);
		} catch (JSONException | IOException | KiteException e) {
			throw new BotException("Error while genrationg session", e);
		}
	}

	public String getLoginUrl() {
		return kiteConnect.getLoginURL();
	}
}
