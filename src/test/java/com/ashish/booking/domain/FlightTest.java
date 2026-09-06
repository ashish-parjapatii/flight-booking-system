package com.ashish.booking.domain;

import static org.assertj.core.api.Assertions.*;
import com.ashish.booking.domain.exception.*;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;

class FlightTest {

    private Flight flight;

    @BeforeEach
    void setUp() {
        flight = new Flight(
            FlightNumber.of("AI809"), Airport.of("DEL"), Airport.of("BLR"),
            LocalDateTime.now().plusDays(7), LocalDateTime.now().plusDays(7).plusHours(3),
            180, Money.inr("4599.00"));
    }

    @Test
    void newFlightHasAllSeatsAvailable() {
        assertThat(flight.seatsAvailable()).isEqualTo(180);
        assertThat(flight.isFull()).isFalse();
        assertThat(flight.status()).isEqualTo(FlightStatus.SCHEDULED);
    }

    @Test
    void reservingSeatReducesAvailability() {
        flight.reserveSeat(SeatNumber.parse("12A"));
        assertThat(flight.seatsAvailable()).isEqualTo(179);
        assertThat(flight.isSeatBooked(SeatNumber.parse("12A"))).isTrue();
    }

    @Test
    void cannotBookSameSeatTwice() {
        var seat = SeatNumber.parse("12A");
        flight.reserveSeat(seat);
        assertThatThrownBy(() -> flight.reserveSeat(seat))
            .isInstanceOf(SeatAlreadyBookedException.class);
    }

    @Test
    void cannotBookSeatNotOnAircraft() {
        assertThatThrownBy(() -> flight.reserveSeat(SeatNumber.parse("40F")))
            .isInstanceOf(SeatNotOnAircraftException.class);
    }

    @Test
    void cannotCancelADepartedFlight() {
        flight.markDeparted();
        assertThatThrownBy(flight::cancel)
                .isInstanceOf(IllegalFlightTransitionException.class);
    }

    @Test
    void cannotBookCancelledFlight() {
        flight.cancel();
        assertThatThrownBy(() -> flight.reserveSeat(SeatNumber.parse("12A")))
            .isInstanceOf(FlightNotBookableException.class);
    }

    @Test
    void cannotDepartACancelledFlight() {
        flight.cancel();
        assertThatThrownBy(flight::markDeparted)
                .isInstanceOf(IllegalFlightTransitionException.class);
    }

    @Test
    void bookedSeatsCannotBeModifiedExternally() {
        flight.reserveSeat(SeatNumber.parse("12A"));
        assertThatThrownBy(() -> flight.bookedSeats().clear())
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void sameFlightNumberMeansSameFlight() {
        var other = new Flight(FlightNumber.of("AI809"), Airport.of("DEL"), Airport.of("BLR"),
            LocalDateTime.now().plusDays(7), LocalDateTime.now().plusDays(7).plusHours(3),
            180, Money.inr("9999.00"));
        assertThat(flight).isEqualTo(other);   // different price, same flight
    }

    @Test
    void rejectsSameOriginAndDestination() {
        assertThatThrownBy(() -> new Flight(FlightNumber.of("AI809"),
            Airport.of("DEL"), Airport.of("DEL"),
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1),
            180, Money.inr("100.00")))
            .isInstanceOf(IllegalArgumentException.class);
    }
}