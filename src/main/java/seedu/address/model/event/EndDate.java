package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents Ending Date of a {@code CalendarEvent}.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDate(String)}
 */
public class EndDate {
    public static final String MESSAGE_END_DATE_CONSTRAINTS =
            "End Date should be DD-MM-YYYY, and it should not be blank";

    public static final String END_DATE_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}"; // format
    public static final String END_DATE_VALIDATION_FORMAT = "dd-MM-yyyy"; // legal dates

    private final String endDate;

    /**
     * Constructs {@code EndDate}.
     *
     * @param endDate Valid end date.
     */
    public EndDate(String endDate) {
        requireNonNull(endDate);
        checkArgument(isValidEndDate(endDate), MESSAGE_END_DATE_CONSTRAINTS);
        this.endDate = endDate;

    }

    /**
     * Returns true if given string is a valid end date.
     */
    public static boolean isValidEndDate(String test) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(END_DATE_VALIDATION_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(test);
        } catch (ParseException e) {
            return false;
        }

        return test.matches(END_DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return endDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDate // instanceof handles nulls
                && this.endDate.equals(((EndDate) other).endDate)); // state check
    }

    @Override
    public int hashCode() {
        return endDate.hashCode();
    }
}
