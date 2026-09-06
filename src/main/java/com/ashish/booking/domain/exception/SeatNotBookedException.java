package com.ashish.booking.domain.exception;

import com.ashish.booking.domain.FlightNumber;
import com.ashish.booking.domain.SeatNumber;

public class SeatNotBookedException extends BookingException {
    public SeatNotBookedException(FlightNumber flightNumber, SeatNumber seat) {
        super("SEAT_NOT_BOOKED",
              "Seat %s on flight %s is not booked".formatted(seat, flightNumber));
    }
}