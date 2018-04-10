package seedu.organizer.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

import seedu.organizer.commons.exceptions.IllegalValueException;

//@@author dominickenn
/**
 * Represents a Task's dateAdded in the organizer.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateAdded(String)}
 */
public class DateAdded {

    public static final String MESSAGE_DATEADDED_CONSTRAINTS =
            "Dates should be in the format YYYY-MM-DD, and should not be blank";

    /*
     * The first character must not be a whitespace, otherwise " " (a blank string) becomes a valid input.
     * Format of string is YYYY-MM-DD.
     */
    public static final String DATEADDED_VALIDATION_REGEX = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])";

    public final LocalDate date;

    /**
     * Constructs an {@code DateAdded}.
     *
     * @param dateAdded A valid DateAdded.
     * @throws IllegalValueException if the {@code LocalDate} class is unable to parse {@code dateAdded}.
     */
    public DateAdded(String dateAdded) {
        requireNonNull(dateAdded);
        checkArgument(isValidDateAdded(dateAdded), MESSAGE_DATEADDED_CONSTRAINTS);
        if (dateAdded.equals("current_date")) {
            //fix for xml file bug due to PrioriTask's dependence on the current date
            this.date = LocalDate.now();
        } else {
            //actual code that is run when tests are not running
            this.date = LocalDate.parse(dateAdded);
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
        return test.matches("current_date") //fix for xml file bug due to PrioriTask's dependence on the current date
                || test.matches(DATEADDED_VALIDATION_REGEX); //actual code that is run when tests are not running
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateAdded // instanceof handles nulls
                && this.date.equals(((DateAdded) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
