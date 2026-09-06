package com.ashish.booking.domain.exception;

import com.ashish.booking.domain.FlightNumber;
import com.ashish.booking.domain.SeatNumber;

public class SeatNotOnAircraftException extends BookingException {
    public SeatNotOnAircraftException(FlightNumber flightNumber, SeatNumber seat) {
        super("SEAT_NOT_ON_AIRCRAFT",
              "Seat %s does not exist on flight %s".formatted(seat, flightNumber));
    }
}