package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;

//@@author sherlynng
/**
 * Represents a Person's rating in STUtor.
 */
public class Rate {

    public static final String RATE_VALIDATION_REGEX = "^(?:5(?:\\.0)?|[0-4](?:\\.[0-9])?|0?\\.[0-9])$";
    public static final String RATE_VALIDATION_REGEX_ABSOLUTE = "^(?:5(?:\\.0)?|[0-4](?:\\.[0-9])?|0?\\.[0-9])" + "-";
    public static final String MESSAGE_RATE_CONSTRAINTS =
            "Rate must be a number between 0 and 5 (inclusive) with at most 1 decimal place";

    private double value;
    private int count;
    private boolean isAbsolute;

    /**
     * Constructs an {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rate (double rating, boolean isAbsolute) {
        requireNonNull(rating);

        this.value = rating;
        this.isAbsolute = isAbsolute;
    }

    /**
     * Initializes a person's rating.
     * @return {@code Rate} with value of 0.0 and count 0.
     */
    public static Rate initializeRate() {
        Rate initializedRate = new Rate(0.0, true);
        initializedRate.setCount(0);

        return initializedRate;
    }

    /**
     * Accumulates a person's rating value.
     * @param oldRate
     * @param newRate
     * @return {@code Rate} that contains updated value and count.
     */
    public static Rate accumulatedValue (Rate oldRate, Rate newRate) {
        double newValue;

        newValue = oldRate.getValue() + newRate.getValue();
        newRate = new Rate(newValue, true);
        newRate.setCount(oldRate.getCount() + 1);

        return newRate;
    }

    /**
     * Returns true if a given string is a valid person rate.
     */
    public static boolean isValidRate(String test) {
        return test.matches(RATE_VALIDATION_REGEX) || test.matches(RATE_VALIDATION_REGEX_ABSOLUTE);
    }

    public double getValue() {
        return this.value;
    }

    /**
     * Gets rate value to be displayed.
     * @return {@code double} rate value rounded off to nearest 1 decimal place.
     */
    public double getDisplayedValue() {
        double displayedValue = 0;

        if (count != 0) {
            displayedValue = (double) Math.round(((value / count) * 10)) / 10;
        }
        return displayedValue;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getIsAbsolute() {
        return isAbsolute;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rate // instanceof handles nulls
                && this.value == ((Rate) other).value
                && this.count == ((Rate) other).count
                && this.isAbsolute == ((Rate) other).isAbsolute); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}
