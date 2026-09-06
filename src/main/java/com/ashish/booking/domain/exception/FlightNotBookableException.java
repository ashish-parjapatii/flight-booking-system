package com.ashish.booking.domain.exception;

import com.ashish.booking.domain.FlightNumber;

public class FlightNotBookableException extends BookingException {
    public FlightNotBookableException(FlightNumber flightNumber, String reason) {
        super("FLIGHT_NOT_BOOKABLE",
              "Flight %s cannot be booked: %s".formatted(flightNumber, reason));
    }
}