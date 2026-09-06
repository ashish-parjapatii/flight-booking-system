package com.ashish.booking.domain.exception;

import com.ashish.booking.domain.BookingReference;

public class BookingNotFoundException extends NotFoundException {

    private final BookingReference reference;

    public BookingNotFoundException(BookingReference reference) {
        super("BOOKING_NOT_FOUND", "Booking %s not found".formatted(reference));
        this.reference = reference;
    }

    public BookingReference reference() { return reference; }
}