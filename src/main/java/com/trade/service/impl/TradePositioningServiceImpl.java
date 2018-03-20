package com.trade.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trade.domain.TradeEvent;
import com.trade.domain.TradePosition;
import com.trade.service.TradePositioningService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TradePositioningServiceImpl implements TradePositioningService {

	private static final Logger LOG = LoggerFactory.getLogger(TradePositioningServiceImpl.class);

	/** Map to hold TradePosition domains */
	private Map<String, TradePosition> tradePositions = new LinkedHashMap<>();

	/** Map to hold TradeEvent and TradePosition relationship */
	private Map<Integer, TradePosition> tradeEventPositionMap = new LinkedHashMap<>();

	public void placeTradeEvent(TradeEvent event) {
		boolean isTradeAttached = false;
		String tradePositionKey = event.getAccountNumber() + "-" + event.getSecurityId();
		/**
		 * Check if TradePosition already available for this account-security
		 * combination. If not creating new position.
		 */
		TradePosition tradePosition = tradePositions.get(tradePositionKey);
		if (tradePosition == null) {
			tradePosition = new TradePosition(event.getAccountNumber(), event.getSecurityId());
			tradePositions.put(tradePositionKey, tradePosition);
		}

		isTradeAttached = tradePosition.attachTradeEvent(tradeEventPositionMap.get(event.getTradeId()), event);

		/**
		 * If TradeEvent is attached to the current TradePosition, then update the
		 * mapping.
		 */
		if (isTradeAttached) {
			tradeEventPositionMap.put(event.getTradeId(), tradePosition);
		}
	}

	public Map<String, TradePosition> processTradePositions() {
		tradePositions.values().forEach(tradePosition -> {
			tradePosition.processTradePosition();
			LOG.info(tradePosition.toString());
		});
		return tradePositions;
	}
}