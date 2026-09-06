package com.ashish.booking.domain;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED;

    private static final Map<BookingStatus, Set<BookingStatus>> TRANSITIONS = Map.of(
        PENDING,   EnumSet.of(CONFIRMED, CANCELLED),
        CONFIRMED, EnumSet.of(CANCELLED, COMPLETED),
        CANCELLED, EnumSet.noneOf(BookingStatus.class),
        COMPLETED, EnumSet.noneOf(BookingStatus.class)
    );

    public boolean canTransitionTo(BookingStatus target) {
        return TRANSITIONS.get(this).contains(target);
    }

    public boolean isTerminal() {
        return TRANSITIONS.get(this).isEmpty();
    }
}