package com.trade.bot.service;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.trade.bot.config.TradeProperties;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.LTPQuote;

@Service
public class TradeScheduler {

	@Autowired
	private TradeProperties tradeProperties;
	
	@Autowired
	private KiteConnect kiteConnect;

	@Scheduled(cron = "${schedule}")
	public void sucheduleTrades() {
		String[] symbols = tradeProperties.getSymbols();
		
	  try {
		Map<String, LTPQuote> ltp = kiteConnect.getLTP(symbols);
		//kiteConnect.getInstruments();
	} catch (IOException | JSONException | KiteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

}
