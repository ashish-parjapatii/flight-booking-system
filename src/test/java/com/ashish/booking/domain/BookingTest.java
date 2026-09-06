package com.ashish.booking.domain;

import com.ashish.booking.domain.exception.IllegalBookingTransitionException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

class BookingTest {

    private Booking booking;
    private final Instant now = Instant.parse("2026-09-05T10:00:00Z");

    @BeforeEach
    void setUp() {
        booking = Booking.create(FlightNumber.of("AI809"), SeatNumber.parse("12A"),
            new PassengerDetails("Ashish Parjapati", "a@example.com", "9999999999"),
            Money.inr("4599.00"), now);
    }

    @Test
    void startsPending() {
        assertThat(booking.status()).isEqualTo(BookingStatus.PENDING);
    }

    @Test
    void pendingCanBeConfirmed() {
        booking.confirm();
        assertThat(booking.status()).isEqualTo(BookingStatus.CONFIRMED);
    }

    @Test
    void cancelledCannotBeConfirmed() {
        booking.cancel(now);
        assertThatThrownBy(() -> booking.confirm())
            .isInstanceOf(IllegalBookingTransitionException.class);
    }

    @Test
    void completedIsTerminal() {
        booking.confirm();
        booking.complete();
        assertThatThrownBy(() -> booking.cancel(now))
            .isInstanceOf(IllegalBookingTransitionException.class);
    }

    @ParameterizedTest
    @CsvSource({
        "48, 4599.00",   // >24h  → full refund
        "12, 2299.50",   // 2-24h → 50%
        "1,  0.00"       // <2h   → nothing
    })
    void refundDependsOnCancellationTiming(int hoursBefore, String expected) {
        var departure = LocalDateTime.ofInstant(now, ZoneId.systemDefault()).plusHours(hoursBefore);
        booking.cancel(now);
        assertThat(booking.refundAmount(now, departure)).isEqualTo(Money.inr(expected));
    }
}