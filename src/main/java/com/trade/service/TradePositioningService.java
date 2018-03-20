package com.trade.service;

import java.util.Map;

import com.trade.domain.TradeEvent;
import com.trade.domain.TradePosition;

public interface TradePositioningService {

	/**
	 * Placing incoming trade events into position.
	 * 
	 * @param event
	 */
	public void placeTradeEvent(TradeEvent event);

	/**
	 * Processing all the positions after receiving all the events.
	 */
	public Map<String, TradePosition> processTradePositions();

}