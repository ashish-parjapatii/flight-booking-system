package com.ashish.booking.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) implements Comparable<Money> {

    public Money {
        Objects.requireNonNull(amount, "amount is required");
        Objects.requireNonNull(currency, "currency is required");
        if (amount.scale() > currency.decimalPlaces()) {
            throw new IllegalArgumentException(
                    "Amount " + amount + " exceeds " + currency.decimalPlaces() +
                            " decimal places for " + currency);
        }
        amount = amount.setScale(currency.decimalPlaces(), RoundingMode.UNNECESSARY);
    }

    public static Money inr(String amount) {
        return new Money(new BigDecimal(amount), Currency.INR);
    }

    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        requireSameCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    public Money multiply(int factor) {
        return new Money(amount.multiply(BigDecimal.valueOf(factor)), currency);
    }

    public Money percentage(BigDecimal rate) {
        return new Money(
                amount.multiply(rate).setScale(currency.decimalPlaces(), RoundingMode.HALF_UP),
                currency);
    }

    public boolean isNegative() {
        return amount.signum() < 0;
    }

    private void requireSameCurrency(Money other) {
        if (currency != other.currency) {
            throw new IllegalArgumentException(
                    "Cannot combine " + currency + " and " + other.currency);
        }
    }

    @Override
    public int compareTo(Money other) {
        requireSameCurrency(other);
        return amount.compareTo(other.amount);
    }

    @Override
    public String toString() {
        return currency.symbol() + amount.toPlainString();
    }
}