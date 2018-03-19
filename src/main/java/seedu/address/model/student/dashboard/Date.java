package seedu.address.model.student.dashboard;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a date in a Student's Dashboard
 * Guarantees: immutable.
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be of the format dd/mm/yyyy hh:mm"
            + "where dd must be between 01 and 31"
            + ", mm between 01 and 12"
            + ", yyyy between 0000 and 9999"
            + ", hh between 00 to 23"
            + " and mm between 00 to 59."
            + " There must be a single space between the date and the time.";

    // Regex for the date format
    private static final String DAY_PART_REGEX = "([0-9]{2})";
    private static final String MONTH_PART_REGEX = "([0-9]{2})";
    private static final String YEAR_PART_REGEX = "([0-9]{4})";
    private static final String HOUR_PART_REGEX = "([0-9]{2})";
    private static final String MINUTE_PART_REGEX = "([0-9]{2})";
    public static final String DATE_VALIDATION_REGEX = DAY_PART_REGEX + "/" + MONTH_PART_REGEX + "/" + YEAR_PART_REGEX
            + "\\s" + HOUR_PART_REGEX + ":" + MINUTE_PART_REGEX;

    private static final Pattern dateFormatPattern = Pattern.compile(DATE_VALIDATION_REGEX);

    // Indexes of capturing group in the Date Matcher's Pattern
    private static final int GROUP_DAY = 1;
    private static final int GROUP_MONTH = 2;
    private static final int GROUP_YEAR = 3;
    private static final int GROUP_HOUR = 4;
    private static final int GROUP_MINUTE = 5;

    private final LocalDateTime value;

    public Date(LocalDateTime value) {
        this.value = LocalDateTime.of(value.getYear(), value.getMonth(), value.getDayOfMonth(),
                value.getHour(), value.getMinute());
    }

    public Date(String date) {
        assert date != null;
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);

        Matcher matcher = dateFormatPattern.matcher(date.trim());
        matcher.matches();
        int day = Integer.parseInt(matcher.group(GROUP_DAY));
        int month = Integer.parseInt(matcher.group(GROUP_MONTH));
        int year = Integer.parseInt(matcher.group(GROUP_YEAR));
        int hour = Integer.parseInt(matcher.group(GROUP_HOUR));
        int min = Integer.parseInt(matcher.group(GROUP_MINUTE));

        value = LocalDateTime.of(year, month, day, hour, min);
    }

    /**
     * Returns if a given string is a valid date
     */
    public static boolean isValidDate(String input) {
        Matcher matcher = dateFormatPattern.matcher(input.trim());
        if (!matcher.matches()) {
            return false;
        }

        int day = Integer.parseInt(matcher.group(GROUP_DAY));
        int month = Integer.parseInt(matcher.group(GROUP_MONTH));
        int year = Integer.parseInt(matcher.group(GROUP_YEAR));
        int hour = Integer.parseInt(matcher.group(GROUP_HOUR));
        int min = Integer.parseInt(matcher.group(GROUP_MINUTE));

        return (day >= 1 && day <= 31) && (month >= 1 && month <= 12) && (year >= 1 && year <= 9999)
                && (hour >= 0 && hour <= 23) && (min >=0 && min <= 59);
    }

    public LocalDateTime getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Date: " + value.getDayOfMonth() + " " + value.getMonth() + " " + value.getYear()
                + " Time: " + value.getHour() + ":" + value.getMinute();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Date
                && this.value.equals(((Date) obj).getValue()));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
