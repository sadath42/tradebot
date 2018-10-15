package com.trade.bot.service;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.trade.bot.config.TradeProperties;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.LTPQuote;
import com.zerodhatech.models.Tick;

@Service
public class TradeScheduler {

	private static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

	@Autowired
	private TradeProperties tradeProperties;

	@Autowired
	private KiteConnect kiteConnect;
	
	@Autowired
	private LivePriceUpdater livePriceUpdater;

	@Scheduled(cron = "${schedule}")
	public void sucheduleTrades() {
		String[] symbols = tradeProperties.getSymbols();

		try {
			ArrayList<Long> tokens = getTokens(symbols);
			for (Long token : tokens) {
			Tick tick=	livePriceUpdater.getLiveTick(token);
			tick.getLowPrice();
			

			}

		} catch (IOException | JSONException | KiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<Long> getTokens(String[] symbols) throws KiteException, IOException, JSONException {
		Map<String, LTPQuote> ltp = kiteConnect.getLTP(symbols);
		Set<Entry<String, LTPQuote>> ltpEntries = ltp.entrySet();
		ArrayList<Long> tokens = new ArrayList<>();
		for (Entry<String, LTPQuote> entry : ltpEntries) {
			LOGGER.info(" Symbol {}  Token {}", entry.getKey(), entry.getValue().instrumentToken);
			tokens.add(entry.getValue().instrumentToken);
		}
		return tokens;
	}

}
