package com.ashish.booking.domain;

public enum Currency {
    INR("₹", 2),
    USD("$", 2);

    private final String symbol;
    private final int decimalPlaces;

    private Currency(String symbol, int decimalPlaces) {
        this.symbol = symbol;
        this.decimalPlaces = decimalPlaces;
    }
    public String symbol() { return symbol; };
    public int decimalPlaces() { return decimalPlaces; }
}