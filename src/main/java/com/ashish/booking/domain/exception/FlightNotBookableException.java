package com.ashish.booking.domain.exception;

import com.ashish.booking.domain.FlightNumber;

public class FlightNotBookableException extends BusinessRuleException {
    private final FlightNumber flightNumber;
    private final String reason;

    public FlightNotBookableException(FlightNumber flightNumber, String reason) {
        super("FLIGHT_NOT_BOOKABLE",
              "Flight %s cannot be booked: %s".formatted(flightNumber, reason));
        this.flightNumber = flightNumber;
        this.reason = reason;
    }

    public FlightNumber flightNumber() { return flightNumber; }
    public String reason() { return reason; }
}