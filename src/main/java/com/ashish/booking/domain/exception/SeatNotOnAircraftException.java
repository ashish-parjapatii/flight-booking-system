package com.ashish.booking.domain.exception;

import com.ashish.booking.domain.FlightNumber;
import com.ashish.booking.domain.SeatNumber;

public class SeatNotOnAircraftException extends BusinessRuleException {
    private final FlightNumber flightNumber;
    private final SeatNumber seat;

    public SeatNotOnAircraftException(FlightNumber flightNumber, SeatNumber seat) {
        super("SEAT_NOT_ON_AIRCRAFT",
              "Seat %s does not exist on flight %s".formatted(seat, flightNumber));
        this.flightNumber = flightNumber;
        this.seat = seat;
    }

    public FlightNumber flightNumber() { return flightNumber; }
    public SeatNumber seat() { return seat; }
}