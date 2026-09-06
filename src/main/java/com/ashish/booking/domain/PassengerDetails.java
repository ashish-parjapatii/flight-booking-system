package com.ashish.booking.domain;

public record PassengerDetails(String fullName, String email, String phone) {
    public PassengerDetails {
        if (fullName == null || fullName.isBlank())
            throw new IllegalArgumentException("Passenger name is required");
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
            throw new IllegalArgumentException("Valid email is required");
        fullName = fullName.strip();
        email = email.strip().toLowerCase();
    }
}