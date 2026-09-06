package com.ashish.booking.domain.exception;


public abstract class NotFoundException extends BookingException {
    protected NotFoundException(String errorCode, String message) {
        super(errorCode, message);
    }
}