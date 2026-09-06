package com.ashish.booking.domain.exception;

import com.ashish.booking.domain.FlightNumber;
import com.ashish.booking.domain.FlightStatus;

public class IllegalFlightTransitionException extends BusinessRuleException {

    private final FlightNumber flightNumber;
    private final FlightStatus from;
    private final FlightStatus to;

    public IllegalFlightTransitionException(FlightNumber flightNumber,
                                            FlightStatus from,
                                            FlightStatus to) {
        super("ILLEGAL_FLIGHT_TRANSITION",
              "Flight %s cannot move from %s to %s".formatted(flightNumber, from, to));
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
    }

    public FlightNumber flightNumber() { return flightNumber; }
    public FlightStatus from() { return from; }
    public FlightStatus to() { return to; }
}