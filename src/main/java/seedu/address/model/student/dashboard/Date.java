package seedu.address.model.student.dashboard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author yapni
/**
 * Represents a date in a Student's Dashboard
 * Guarantees: immutable.
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be of the format DD/MM/YYYY hh:mm.\n"
            + "The date should also be a valid day in the calendar.\n"
            + "The time must be in 24-hour notation.\n "
            + "There must be a single space between the date and the time.";

    // Regex for the date format
    private static final String DAY_PART_REGEX = "([0-9]{2})";
    private static final String MONTH_PART_REGEX = "([0-9]{2})";
    private static final String YEAR_PART_REGEX = "([0-9]{4})";
    private static final String HOUR_PART_REGEX = "([0-9]{2})";
    private static final String MINUTE_PART_REGEX = "([0-9]{2})";
    private static final String DATE_SEPARATOR = "/";
    private static final String TIME_SEPARATOR = ":";
    public static final String DATE_VALIDATION_REGEX = DAY_PART_REGEX + DATE_SEPARATOR + MONTH_PART_REGEX
            + DATE_SEPARATOR + YEAR_PART_REGEX + "\\s" + HOUR_PART_REGEX + TIME_SEPARATOR + MINUTE_PART_REGEX;

    private static final Pattern dateFormatPattern = Pattern.compile(DATE_VALIDATION_REGEX);

    // Indexes of capturing group in the Date Matcher's Pattern
    private static final int GROUP_DAY = 1;
    private static final int GROUP_MONTH = 2;
    private static final int GROUP_YEAR = 3;
    private static final int GROUP_HOUR = 4;
    private static final int GROUP_MINUTE = 5;

    private final String value;

    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);

        String trimmedDate = date.trim();
        value = trimmedDate;
    }

    /**
     * Returns if a given string is a valid date
     * @throws NullPointerException if input is null
     */
    public static boolean isValidDate(String input) {
        requireNonNull(input);

        /* Check if input matches the required regex pattern */
        Matcher matcher = dateFormatPattern.matcher(input.trim());
        if (!matcher.matches()) {
            return false;
        }

        int day = Integer.parseInt(matcher.group(GROUP_DAY));
        int month = Integer.parseInt(matcher.group(GROUP_MONTH));
        int year = Integer.parseInt(matcher.group(GROUP_YEAR));
        int hour = Integer.parseInt(matcher.group(GROUP_HOUR));
        int min = Integer.parseInt(matcher.group(GROUP_MINUTE));

        /* Check if the given date is a valid day in the calendar */
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd" + DATE_SEPARATOR + "MM" + DATE_SEPARATOR + "yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(day + DATE_SEPARATOR + month + DATE_SEPARATOR + year);
        } catch (ParseException e) {
            return false;
        }

        /* Check if the given time is valid in the 24 hour format */
        return (hour >= 0 && hour <= 23) && (min >= 0 && min <= 59);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
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
