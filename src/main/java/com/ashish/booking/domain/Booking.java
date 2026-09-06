package com.ashish.booking.domain;

import com.ashish.booking.domain.exception.IllegalBookingTransitionException;

import java.time.Instant;
import java.util.Objects;

public class Booking {

    private final BookingReference reference;
    private final FlightNumber flightNumber;
    private final SeatNumber seat;
    private final PassengerDetails passenger;
    private final Money pricePaid;
    private final Instant createdAt;
    private BookingStatus status;
    private Instant cancelledAt;

    private Booking(BookingReference reference, FlightNumber flightNumber, SeatNumber seat,
                    PassengerDetails passenger, Money pricePaid, Instant createdAt) {
        this.reference = Objects.requireNonNull(reference);
        this.flightNumber = Objects.requireNonNull(flightNumber);
        this.seat = Objects.requireNonNull(seat);
        this.passenger = Objects.requireNonNull(passenger);
        this.pricePaid = Objects.requireNonNull(pricePaid);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.status = BookingStatus.PENDING;

        if (pricePaid.isNegative()) {
            throw new IllegalArgumentException("Price paid cannot be negative");
        }
    }

    public static Booking create(FlightNumber flightNumber, SeatNumber seat,
                                 PassengerDetails passenger, Money price, Instant now) {
        return new Booking(BookingReference.generate(), flightNumber, seat, passenger, price, now);
    }

    public void confirm() {
        transitionTo(BookingStatus.CONFIRMED, null);
    }

    public void cancel(Instant now) {
        transitionTo(BookingStatus.CANCELLED, now);
    }

    public void complete() {
        transitionTo(BookingStatus.COMPLETED, null);
    }

    private void transitionTo(BookingStatus target, Instant at) {
        if (!status.canTransitionTo(target)) {
            throw new IllegalBookingTransitionException(reference, status, target);
        }
        this.status = target;
        if (target == BookingStatus.CANCELLED) this.cancelledAt = at;
    }

    public boolean isActive() {
        return status == BookingStatus.PENDING || status == BookingStatus.CONFIRMED;
    }

    public Money refundAmount(Instant now, java.time.LocalDateTime departure) {
        if (status != BookingStatus.CANCELLED) return Money.zero(pricePaid.currency());
        long hoursBefore = java.time.Duration.between(
            now, departure.atZone(java.time.ZoneId.systemDefault()).toInstant()).toHours();
        if (hoursBefore >= 24) return pricePaid;
        if (hoursBefore >= 2)  return pricePaid.percentage(new java.math.BigDecimal("0.50"));
        return Money.zero(pricePaid.currency());
    }

    public BookingReference reference()   { return reference; }
    public FlightNumber flightNumber()    { return flightNumber; }
    public SeatNumber seat()              { return seat; }
    public PassengerDetails passenger()   { return passenger; }
    public Money pricePaid()              { return pricePaid; }
    public BookingStatus status()         { return status; }
    public Instant createdAt()            { return createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return reference.equals(((Booking) o).reference);
    }

    @Override
    public int hashCode() { return reference.hashCode(); }

    @Override
    public String toString() {
        return "Booking[%s %s seat %s %s]".formatted(reference, flightNumber, seat, status);
    }
}