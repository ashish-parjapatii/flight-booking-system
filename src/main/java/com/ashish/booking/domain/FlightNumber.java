package com.ashish.booking.domain;

import java.util.regex.Pattern;

public record FlightNumber(String value) {

    private static final Pattern PATTERN = Pattern.compile("^[A-Z]{2}\\d{1,4}$");

    public FlightNumber {
        if (value == null || !PATTERN.matcher(value.strip().toUpperCase()).matches()) {
            throw new IllegalArgumentException("Invalid flight number: '" + value + "' (expected e.g. AI809)");
        }
        value = value.strip().toUpperCase();
    }

    public static FlightNumber of(String value) { return new FlightNumber(value); }

    public String airlineCode() { return value.substring(0, 2); }

    @Override
    public String toString() { return value; }
}