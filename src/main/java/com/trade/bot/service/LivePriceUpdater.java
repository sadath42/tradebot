package com.trade.bot.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.neovisionaries.ws.client.WebSocketException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.Tick;
import com.zerodhatech.ticker.KiteTicker;
import com.zerodhatech.ticker.OnConnect;
import com.zerodhatech.ticker.OnDisconnect;
import com.zerodhatech.ticker.OnError;
import com.zerodhatech.ticker.OnOrderUpdate;
import com.zerodhatech.ticker.OnTicks;

@Service
public class LivePriceUpdater {

	private Map<Long, Tick> liveMap = Collections.synchronizedMap(new HashMap<>());

	public void tickerUsage(ArrayList<Long> tokens, KiteTicker tickerProvider)
			throws IOException, WebSocketException, KiteException {

		initialzeLiveData(tokens);
		/**
		 * To get live price use websocket connection. It is recommended to use
		 * only one websocket connection at any point of time and make sure you
		 * stop connection, once user goes out of app. custom url points to new
		 * endpoint which can be used till complete Kite Connect 3 migration is
		 * done.
		 */
		// KiteTicker tickerProvider = new
		// KiteTicker(kiteConnect.getAccessToken(), kiteConnect.getApiKey());

		tickerProvider.setOnConnectedListener(new OnConnect() {
			@Override
			public void onConnected() {
				/**
				 * Subscribe ticks for token. By default, all tokens are
				 * subscribed for modeQuote.
				 */
				tickerProvider.subscribe(tokens);
				tickerProvider.setMode(tokens, KiteTicker.modeQuote);
			}
		});

		tickerProvider.setOnDisconnectedListener(new OnDisconnect() {
			@Override
			public void onDisconnected() {
				// your code goes here
			}
		});

		/** Set listener to get order updates. */
		tickerProvider.setOnOrderUpdateListener(new OnOrderUpdate() {
			@Override
			public void onOrderUpdate(Order order) {
				System.out.println("order update " + order.orderId);
			}
		});

		/** Set error listener to listen to errors. */
		tickerProvider.setOnErrorListener(new OnError() {
			@Override
			public void onError(Exception exception) {
				// handle here.
			}

			@Override
			public void onError(KiteException kiteException) {
				// handle here.
			}
		});

		tickerProvider.setOnTickerArrivalListener(new OnTicks() {
			@Override
			public void onTicks(ArrayList<Tick> ticks) {
				NumberFormat formatter = new DecimalFormat();
				System.out.println("ticks size " + ticks.size());
				for (Tick tick : ticks) {
					liveMap.put(tick.getInstrumentToken(), tick);
					System.out.println("last price " + tick.getLastTradedPrice());
					System.out.println("open interest " + formatter.format(tick.getOi()));
					System.out.println("day high OI " + formatter.format(tick.getOpenInterestDayHigh()));
					System.out.println("day low OI " + formatter.format(tick.getOpenInterestDayLow()));
					System.out.println("change " + formatter.format(tick.getChange()));
					System.out.println("tick timestamp " + tick.getTickTimestamp());
					System.out.println("tick timestamp date " + tick.getTickTimestamp());
					System.out.println("last traded time " + tick.getLastTradedTime());
					System.out.println(tick.getMarketDepth().get("buy").size());
				}

			}
		});
		// Make sure this is called before calling connect.
		tickerProvider.setTryReconnection(true);
		// maximum retries and should be greater than 0
		tickerProvider.setMaximumRetries(10);
		// set maximum retry interval in seconds
		tickerProvider.setMaximumRetryInterval(30);

		/**
		 * connects to com.zerodhatech.com.zerodhatech.ticker server for getting
		 * live quotes
		 */
		tickerProvider.connect();

		/**
		 * You can check, if websocket connection is open or not using the
		 * following method.
		 */
		boolean isConnected = tickerProvider.isConnectionOpen();
		System.out.println(isConnected);

		/**
		 * set mode is used to set mode in which you need tick for list of
		 * tokens. Ticker allows three modes, modeFull, modeQuote, modeLTP. For
		 * getting only last traded price, use modeLTP For getting last traded
		 * price, last traded quantity, average price, volume traded today,
		 * total sell quantity and total buy quantity, open, high, low, close,
		 * change, use modeQuote For getting all data with depth, use modeFull
		 */
		// tickerProvider.setMode(tokens, KiteTicker.modeLTP);

		// Unsubscribe for a token.
		// tickerProvider.unsubscribe(tokens);

		// After using com.zerodhatech.com.zerodhatech.ticker, close websocket
		// connection.
		// tickerProvider.disconnect();
	}

	private void initialzeLiveData(ArrayList<Long> tokens) {
		liveMap.clear();
	}

	public Tick getLiveTick(Long token) {
		return liveMap.get(token);
	}
}
