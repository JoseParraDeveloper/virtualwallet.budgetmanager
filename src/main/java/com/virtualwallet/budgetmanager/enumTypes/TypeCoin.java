package com.virtualwallet.budgetmanager.enumTypes;

public enum TypeCoin {

	DOLLAR("Dolares", "U$S"), PESOS("Pesos", "$");

	private String coin;
	private String symbol;

	private TypeCoin(String coin, String symbol) {
		this.coin = coin;
		this.symbol = symbol;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
