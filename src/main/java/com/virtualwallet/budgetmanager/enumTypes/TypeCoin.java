package com.virtualwallet.budgetmanager.enumTypes;

public enum TypeCoin {

	DOLLAR("DOLAR", "U$S"), PESOS("PESOS", "$");

	private String coin;
	private String symbol;

	private TypeCoin(String coin, String symbol) {
		this.coin = coin;
		this.symbol = symbol;
	}

	public String getCoin() {
		return coin;
	}

	public String getSymbol() {
		return symbol;
	}

}
