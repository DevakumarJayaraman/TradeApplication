package com.trade.domain;

public class TradeEvent {

	public TradeEvent(int tradeId, int tradeVersion, String securityId, int quantity, String direction,
			String accountNumber, String operation) {
		super();
		this.tradeId = tradeId;
		this.tradeVersion = tradeVersion;
		this.securityId = securityId;
		this.quantity = quantity;
		this.direction = direction;
		this.accountNumber = accountNumber;
		this.operation = operation;
	}

	private int tradeId;
	private int tradeVersion;
	private String securityId;
	private int quantity;
	private String direction;
	private String accountNumber;
	private String operation;

	public int getTradeId() {
		return tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public int getTradeVersion() {
		return tradeVersion;
	}

	public void setTradeVersion(int tradeVersion) {
		this.tradeVersion = tradeVersion;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}