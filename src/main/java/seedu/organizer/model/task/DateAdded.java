package seedu.organizer.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

//@@author dominickenn
/**
 * Represents a Task's dateAdded in the organizer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class DateAdded {

    public static final String MESSAGE_DATEADDED_CONSTRAINTS =
            "Dates should be in the format YYYY-MM-DD, and it should not be blank";

    /*
     * The first character must not be a whitespace, otherwise " " (a blank string) becomes a valid input.
     * Format of string is YYYY-MM-DD.
     */
    public static final String DATEADDED_VALIDATION_REGEX = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])";

    public final LocalDate date;

    /**
     * Constructs an {@code DateAdded}.
     *
     * @param dateadded A valid date.
     */
    public DateAdded(String dateadded) {
        requireNonNull(dateadded);
        checkArgument(isValidDateAdded(dateadded), MESSAGE_DATEADDED_CONSTRAINTS);
        //temporary fix for xml file bug due to PrioriTask's dependence on the current date
        if (dateadded.equals("current_date")) {
            this.date = LocalDate.now();
        } else {
            //actual code that is run when tests are not running
            this.date = LocalDate.parse(dateadded);
        }
    }

    /**
     * Constructs a DateAdded based on the current date
     */
    public DateAdded() {
        LocalDate currentDate = LocalDate.now();
        requireNonNull(currentDate);
        this.date = currentDate;
    }

    /**
     * Returns true if a given string is a valid task deadline.
     */
    public static boolean isValidDateAdded(String test) {
        return test.matches("current_date") || test.matches(DATEADDED_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.date.equals(((Deadline) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
