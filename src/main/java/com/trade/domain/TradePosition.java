package com.trade.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.trade.utils.TradeDirection;
import com.trade.utils.TradeOperation;

public class TradePosition {

	public TradePosition(String account, String instrument) {
		super();
		this.account = account;
		this.instrument = instrument;
	}

	public TradePosition(String account, String instrument, int quantity, Set<Integer> trades) {
		super();
		this.account = account;
		this.instrument = instrument;
		this.quantity = quantity;
		this.trades = trades;
	}

	private String account;
	private String instrument;
	private int quantity;
	private Map<Integer, TradeEvent> tradeEvents = new HashMap<>();
	private Set<Integer> trades = new TreeSet<>();

	public String getAccount() {
		return account;
	}

	public String getInstrument() {
		return instrument;
	}

	public int getQuantity() {
		return quantity;
	}

	public Set<Integer> getTrades() {
		return trades;
	}

	public void addTradeEventId(int tradeEventId) {
		this.trades.add(tradeEventId);
	}

	public Map<Integer, TradeEvent> getTradeEvents() {
		return tradeEvents;
	}

	/**
	 * Process trade position based on positioning rules
	 */
	public void processTradePosition() {
		TradeDirection tradeDirection;
		TradeOperation tradeOperation;
		for (TradeEvent p : tradeEvents.values()) {
			tradeDirection = TradeDirection.valueOf(p.getDirection());
			tradeOperation = TradeOperation.valueOf(p.getOperation());
			if (tradeDirection == TradeDirection.BUY) {
				if (tradeOperation == TradeOperation.NEW || tradeOperation == TradeOperation.AMEND) {
					quantity = quantity + p.getQuantity();
				} else if (tradeOperation == TradeOperation.CANCEL) {
					quantity = quantity - p.getQuantity();
				}
			} else if (tradeDirection == TradeDirection.SELL) {
				if (tradeOperation == TradeOperation.NEW || tradeOperation == TradeOperation.AMEND) {
					quantity = quantity - p.getQuantity();
				} else if (tradeOperation == TradeOperation.CANCEL) {
					quantity = quantity + p.getQuantity();
				}
			}
		}
	}

	/**
	 * If TradeEvent is not attached to any TradePosition, then attach it to this
	 * TradePosition irrespective of TradeEvent's version.
	 *
	 * If different version of current TradeEvent is already attached with any other
	 * TradePosition, then compare the version of current TradeEvent.
	 * 
	 * If current TradeEvent belongs to latest version, then remove older version of
	 * the TradeEvent from attached TradePosition and then attach current TradeEvent
	 * to this TradePosition.
	 * 
	 * @param attachedPosition
	 * @param tradeEvent
	 * @return
	 */
	public boolean attachTradeEvent(TradePosition attachedPosition, TradeEvent tradeEvent) {
		addTradeEventId(tradeEvent.getTradeId());
		if (attachedPosition == null) {
			this.tradeEvents.put(tradeEvent.getTradeId(), tradeEvent);
			return true;
		} else if (attachedPosition.getTradeEvents().get(tradeEvent.getTradeId()).getTradeVersion() < tradeEvent
				.getTradeVersion()) {
			attachedPosition.getTradeEvents().remove(tradeEvent.getTradeId());
			this.tradeEvents.put(tradeEvent.getTradeId(), tradeEvent);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "TradePosition [account=" + account + ", instrument=" + instrument + ", quantity=" + quantity
				+ ", trades=" + trades + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((instrument == null) ? 0 : instrument.hashCode());
		result = prime * result + quantity;
		result = prime * result + ((trades == null) ? 0 : trades.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradePosition other = (TradePosition) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (instrument == null) {
			if (other.instrument != null)
				return false;
		} else if (!instrument.equals(other.instrument))
			return false;
		if (quantity != other.quantity)
			return false;
		if (trades == null) {
			if (other.trades != null)
				return false;
		} else if (!trades.equals(other.trades))
			return false;
		return true;
	}
}