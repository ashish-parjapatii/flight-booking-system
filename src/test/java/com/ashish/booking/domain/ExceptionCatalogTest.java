package com.ashish.booking.domain;

import com.ashish.booking.domain.*;
import com.ashish.booking.domain.exception.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionCatalogTest {

    private static final FlightNumber AI809 = FlightNumber.of("AI809");
    private static final SeatNumber SEAT_12A = SeatNumber.parse("12A");
    private static final BookingReference REF = BookingReference.generate();

    @Test
    void errorCodesAreStable() {
        assertThat(new SeatAlreadyBookedException(AI809, SEAT_12A).errorCode())
                .isEqualTo("SEAT_ALREADY_BOOKED");
        assertThat(new SeatNotOnAircraftException(AI809, SEAT_12A).errorCode())
                .isEqualTo("SEAT_NOT_ON_AIRCRAFT");
        assertThat(new SeatNotBookedException(AI809, SEAT_12A).errorCode())
                .isEqualTo("SEAT_NOT_BOOKED");
        assertThat(new FlightNotBookableException(AI809, "departed").errorCode())
                .isEqualTo("FLIGHT_NOT_BOOKABLE");
        assertThat(new FlightNotFoundException(AI809).errorCode())
                .isEqualTo("FLIGHT_NOT_FOUND");
        assertThat(new BookingNotFoundException(REF).errorCode())
                .isEqualTo("BOOKING_NOT_FOUND");
    }

    @Test
    void notFoundIsDistinguishableFromBusinessRule() {
        assertThat(new FlightNotFoundException(AI809))
                .isInstanceOf(NotFoundException.class)
                .isNotInstanceOf(BusinessRuleException.class);

        assertThat(new SeatAlreadyBookedException(AI809, SEAT_12A))
                .isInstanceOf(BusinessRuleException.class)
                .isNotInstanceOf(NotFoundException.class);
    }

    @Test
    void exceptionsCarryStructuredData() {
        SeatAlreadyBookedException e = new SeatAlreadyBookedException(AI809, SEAT_12A);
        assertThat(e.seat()).isEqualTo(SEAT_12A);
        assertThat(e.flightNumber()).isEqualTo(AI809);
    }
}