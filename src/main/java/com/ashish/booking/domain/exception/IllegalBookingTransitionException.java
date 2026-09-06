package com.ashish.booking.domain.exception;

import com.ashish.booking.domain.BookingReference;
import com.ashish.booking.domain.BookingStatus;

public class IllegalBookingTransitionException extends BookingException {

    private final BookingReference reference;
    private final BookingStatus from;
    private final BookingStatus to;

    public IllegalBookingTransitionException(BookingReference reference,
                                             BookingStatus from,
                                             BookingStatus to) {
        super("ILLEGAL_BOOKING_TRANSITION",
                "Booking %s cannot move from %s to %s".formatted(reference, from, to));
        this.reference = reference;
        this.from = from;
        this.to = to;
    }

    public BookingReference reference() { return reference; }
    public BookingStatus from() { return from; }
    public BookingStatus to() { return to; }
}