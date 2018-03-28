package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents Starting Date of a {@code CalendarEvent}.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {
    public static final String MESSAGE_START_DATE_CONSTRAINTS =
            "Start Date should be DD-MM-YYYY, and it should not be blank";

    public static final String START_DATE_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}"; // format
    public static final String START_DATE_VALIDATION_FORMAT = "dd-MM-yyyy"; // legal dates

    private final String startDate;

    /**
     * Constructs {@code StartDate}.
     *
     * @param startDate Valid start date.
     */
    public StartDate(String startDate) {
        requireNonNull(startDate);
        checkArgument(isValidStartDate(startDate), MESSAGE_START_DATE_CONSTRAINTS);
        this.startDate = startDate;

    }

    /**
     * Returns true if given string is a valid start date.
     */
    public static boolean isValidStartDate(String test) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(START_DATE_VALIDATION_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(test);
        } catch (ParseException e) {
            return false;
        }

        return test.matches(START_DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.startDate.equals(((StartDate) other).startDate)); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }
}
