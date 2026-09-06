package com.ashish.booking.domain;

import java.security.SecureRandom;

public record BookingReference(String value) {

    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // no I,O,0,1
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int LENGTH = 6;

    public BookingReference {
        if (value == null || value.length() != LENGTH) {
            throw new IllegalArgumentException("Booking reference must be " + LENGTH + " characters");
        }
        value = value.toUpperCase();
    }

    public static BookingReference generate() {
        var sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new BookingReference(sb.toString());
    }

    @Override
    public String toString() { return value; }
}