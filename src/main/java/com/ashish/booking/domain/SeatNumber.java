package com.ashish.booking.domain;

import java.util.regex.Pattern;

public record SeatNumber(int row, char letter) implements Comparable<SeatNumber> {

    private static final Pattern PATTERN = Pattern.compile("^(\\d{1,2})([A-F])$");
    private static final int MAX_ROW = 40;

    public SeatNumber {
        if (row < 1 || row > MAX_ROW) {
            throw new IllegalArgumentException("Row must be 1-" + MAX_ROW + ", got " + row);
        }
        if (letter < 'A' || letter > 'F') {
            throw new IllegalArgumentException("Seat letter must be A-F, got " + letter);
        }
    }

    public static SeatNumber parse(String value) {
        if (value == null) throw new IllegalArgumentException("Seat number is required");
        var matcher = PATTERN.matcher(value.strip().toUpperCase());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid seat format: '" + value + "' (expected e.g. 12A)");
        }
        return new SeatNumber(Integer.parseInt(matcher.group(1)), matcher.group(2).charAt(0));
    }

    public boolean isWindow() { return letter == 'A' || letter == 'F'; }
    public boolean isAisle()  { return letter == 'C' || letter == 'D'; }
    public boolean isMiddle() { return letter == 'B' || letter == 'E'; }

    @Override
    public int compareTo(SeatNumber other) {
        int byRow = Integer.compare(row, other.row);
        return byRow != 0 ? byRow : Character.compare(letter, other.letter);
    }

    @Override
    public String toString() { return row + String.valueOf(letter); }
}