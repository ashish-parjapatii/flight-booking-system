package com.ashish.booking.domain;

import com.ashish.booking.domain.exception.*;
import java.time.LocalDateTime;
import java.util.*;

public class Flight {

    private final FlightNumber flightNumber;
    private final Airport origin;
    private final Airport destination;
    private final LocalDateTime departure;
    private final LocalDateTime arrival;
    private final int totalSeats;
    private final Money basePrice;
    private final Set<SeatNumber> bookedSeats;
    private FlightStatus status;

    public Flight(FlightNumber flightNumber, Airport origin, Airport destination,
                  LocalDateTime departure, LocalDateTime arrival,
                  int totalSeats, Money basePrice) {

        this.flightNumber = Objects.requireNonNull(flightNumber, "flightNumber is required");
        this.origin = Objects.requireNonNull(origin, "origin is required");
        this.destination = Objects.requireNonNull(destination, "destination is required");
        this.departure = Objects.requireNonNull(departure, "departure is required");
        this.arrival = Objects.requireNonNull(arrival, "arrival is required");
        this.basePrice = Objects.requireNonNull(basePrice, "basePrice is required");

        if (origin.equals(destination)) {
            throw new IllegalArgumentException("Origin and destination must differ");
        }
        if (!arrival.isAfter(departure)) {
            throw new IllegalArgumentException("Arrival must be after departure");
        }
        if (totalSeats < 1 || totalSeats > 600) {
            throw new IllegalArgumentException("Total seats must be 1-600, got " + totalSeats);
        }
        if (basePrice.isNegative()) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }

        this.totalSeats = totalSeats;
        this.bookedSeats = new HashSet<>();
        this.status = FlightStatus.SCHEDULED;
    }

    // --- queries ---

    public int seatsBooked()    { return bookedSeats.size(); }
    public int seatsAvailable() { return totalSeats - bookedSeats.size(); }
    public boolean isFull()     { return seatsAvailable() == 0; }

    public double occupancyRate() {
        return (double) bookedSeats.size() / totalSeats;
    }

    public boolean isSeatBooked(SeatNumber seat) {
        return bookedSeats.contains(seat);
    }

    public boolean isBookable() {
        return status == FlightStatus.SCHEDULED
                && !isFull()
                && departure.isAfter(LocalDateTime.now());
    }

    public Set<SeatNumber> bookedSeats() {
        return Collections.unmodifiableSet(bookedSeats);
    }

    // --- commands ---

    public void reserveSeat(SeatNumber seat) {
        Objects.requireNonNull(seat, "seat is required");

        if (status != FlightStatus.SCHEDULED) {
            throw new FlightNotBookableException(flightNumber, "flight is " + status);
        }
        if (departure.isBefore(LocalDateTime.now())) {
            throw new FlightNotBookableException(flightNumber, "flight has departed");
        }
        if (seat.row() > rowsAvailable()) {
            throw new SeatNotOnAircraftException(flightNumber, seat);
        }
        if (bookedSeats.contains(seat)) {
            throw new SeatAlreadyBookedException(flightNumber, seat);
        }
        bookedSeats.add(seat);
    }

    public void releaseSeat(SeatNumber seat) {
        if (!bookedSeats.remove(seat)) {
            throw new SeatNotBookedException(flightNumber, seat);
        }
    }

    public void cancel() {
        if (status == FlightStatus.DEPARTED) {
            throw new IllegalStateException("Cannot cancel a departed flight");
        }
        this.status = FlightStatus.CANCELLED;
    }

    private int rowsAvailable() {
        return (int) Math.ceil(totalSeats / 6.0);
    }

    // --- accessors ---

    public FlightNumber flightNumber()  { return flightNumber; }
    public Airport origin()             { return origin; }
    public Airport destination()        { return destination; }
    public LocalDateTime departure()    { return departure; }
    public LocalDateTime arrival()      { return arrival; }
    public int totalSeats()             { return totalSeats; }
    public Money basePrice()            { return basePrice; }
    public FlightStatus status()        { return status; }

    // --- identity ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return flightNumber.equals(((Flight) o).flightNumber);
    }

    @Override
    public int hashCode() { return flightNumber.hashCode(); }

    @Override
    public String toString() {
        return "Flight[%s %s→%s %s, %d/%d booked]".formatted(
                flightNumber, origin, destination, departure, seatsBooked(), totalSeats);
    }
}