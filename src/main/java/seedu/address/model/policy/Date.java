package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Date.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(Integer, Month, Integer)}
 */
public class Date {
    public static final String DATE_CONSTRAINTS =
            "Day must be between 1 and 28, 29, 30 or 31 depending on the month and year. Year must be from 1950 to 2150.";

    public final Integer day;
    public final Month month;
    public final Integer year;

    public Date(Integer day, Month month, Integer year) {
        requireAllNonNull(day, month, year);
        checkArgument(isValidDate(day, month, year), DATE_CONSTRAINTS);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    private static boolean isLeapYear(Integer year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    public static boolean isValidDate(Integer day, Month month, Integer year) {
        boolean yearCorrect = year >= 1950 && year <= 2150;

        int daysInMonth = 31;
        if(month == Month.APRIL || month == Month.JUNE || month == Month.SEPTEMBER || month == Month.NOVEMBER) {
            daysInMonth = 30;
        } else if (month == Month.FEBRUARY) {
            daysInMonth = isLeapYear(year)? 29 : 28;
        }

        return yearCorrect && day >= 0 && day <= daysInMonth;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(month.name().charAt(0) + month.name().substring(1).toLowerCase())
                .append(" ")
                .append(day)
                .append(", ")
                .append(year);
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.policy.Date)) {
            return false;
        }

        seedu.address.model.policy.Date otherDate = (seedu.address.model.policy.Date) other;
        return otherDate.day.equals(this.day)
                && otherDate.month.equals(this.month)
                && otherDate.year.equals(this.year);
    }

}
