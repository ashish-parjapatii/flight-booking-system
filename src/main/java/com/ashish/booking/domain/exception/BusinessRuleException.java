package com.ashish.booking.domain.exception;


public abstract class BusinessRuleException extends BookingException {
    protected BusinessRuleException(String errorCode, String message) {
        super(errorCode, message);
    }
}