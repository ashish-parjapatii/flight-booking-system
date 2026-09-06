package com.ashish.booking.domain;

public record Airport(String code) {
    public Airport {
        if (code == null || !code.matches("^[A-Z]{3}$")) {
            throw new IllegalArgumentException("Airport code must be 3 letters, got: " + code);
        }
    }
    public static Airport of(String code) { return new Airport(code.toUpperCase()); }
    @Override public String toString() { return code; }
}