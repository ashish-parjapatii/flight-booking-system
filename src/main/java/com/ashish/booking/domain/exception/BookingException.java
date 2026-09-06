package com.ashish.booking.domain.exception;

public abstract class BookingException extends RuntimeException {

    private final String errorCode;

    protected BookingException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected BookingException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String errorCode() { return errorCode; }
}