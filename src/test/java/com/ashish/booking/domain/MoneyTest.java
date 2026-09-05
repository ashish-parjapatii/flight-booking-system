package com.ashish.booking.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class MoneyTest {

    @Test
    void addsAmountsOfSameCurrency() {
        assertThat(Money.inr("100.50").add(Money.inr("200.25")))
                .isEqualTo(Money.inr("300.75"));
    }

    @Test
    void rejectsMixedCurrencies() {
        assertThatThrownBy(() -> Money.inr("100.00").add(new Money(new BigDecimal("50.00"), Currency.USD)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot combine");
    }

    @Test
    void rejectsExcessivePrecision() {
        assertThatThrownBy(() -> Money.inr("100.999"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void equalAmountsAreEqual() {
        assertThat(Money.inr("100.00")).isEqualTo(Money.inr("100.00"));
    }

    @Test
    void isImmutable() {
        Money original = Money.inr("100.00");
        original.add(Money.inr("50.00"));
        assertThat(original).isEqualTo(Money.inr("100.00"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.00", "1.00", "999999.99"})
    void acceptsValidAmounts(String amount) {
        assertThatCode(() -> Money.inr(amount)).doesNotThrowAnyException();
    }

    @Test
    void floatingPointComparison() {
        // The bug this class exists to prevent
        double a = 0.1 + 0.2;
        assertThat(a).isNotEqualTo(0.3);
        assertThat(Money.inr("0.10").add(Money.inr("0.20"))).isEqualTo(Money.inr("0.30"));
    }
}