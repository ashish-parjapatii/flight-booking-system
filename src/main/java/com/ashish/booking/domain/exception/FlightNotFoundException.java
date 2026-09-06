package com.ashish.booking.domain.exception;

import com.ashish.booking.domain.FlightNumber;

public class FlightNotFoundException extends NotFoundException {

    private final FlightNumber flightNumber;

    public FlightNotFoundException(FlightNumber flightNumber) {
        super("FLIGHT_NOT_FOUND", "Flight %s not found".formatted(flightNumber));
        this.flightNumber = flightNumber;
    }

    public FlightNumber flightNumber() { return flightNumber; }
}