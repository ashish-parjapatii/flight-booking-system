package com.ashish.booking.domain.exception;

import com.ashish.booking.domain.FlightNumber;
import com.ashish.booking.domain.SeatNumber;

public class SeatAlreadyBookedException extends BookingException {
    private final FlightNumber flightNumber;
    private final SeatNumber seat;

    public SeatAlreadyBookedException(FlightNumber flightNumber, SeatNumber seat) {
        super("SEAT_ALREADY_BOOKED",
              "Seat %s on flight %s is already booked".formatted(seat, flightNumber));
        this.flightNumber = flightNumber;
        this.seat = seat;
    }

    public FlightNumber flightNumber() { return flightNumber; }
    public SeatNumber seat() { return seat; }
}