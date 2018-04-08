package seedu.organizer.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

//@@author natania
/**
 * Represents a Task's dateCompleted in the organizer.
 * Guarantees: immutable;
 */
public class DateCompleted {

    public static final String MESSAGE_DATECOMPLETED_CONSTRAINTS =
            "Dates should be in the format YYYY-MM-DD, and it should not be blank";

    /*
     * The first character must not be a whitespace, otherwise " " (a blank string) becomes a valid input.
     * Format of string is YYYY-MM-DD.
     */
    public static final String DATECOMPLETED_VALIDATION_REGEX = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])";
    public static final String TASK_NOTCOMPLETED = "not completed";

    public final LocalDate date;

    /**
     * Constructs an {@code DateCompleted}.
     *
     * @param dateCompleted A valid date.
     */
    public DateCompleted(String dateCompleted) {
        requireNonNull(dateCompleted);
        checkArgument(isValidDateCompleted(dateCompleted), MESSAGE_DATECOMPLETED_CONSTRAINTS);
        //temporary fix for xml file bug due to PrioriTask's dependence on the current date
        if (dateCompleted.equals("current_date")) {
            this.date = LocalDate.now();
        } else {
            //actual code that is run when tests are not running
            if (dateCompleted.equals(TASK_NOTCOMPLETED)) {
                this.date = null;
            } else {
                this.date = LocalDate.parse(dateCompleted);
            }
        }
    }

    /**
     * Constructs a DateCompleted based on the current date
     */
    public DateCompleted() {
        LocalDate currentDate = LocalDate.now();
        requireNonNull(currentDate);
        this.date = currentDate;
    }

    /**
     * Constructs a DateCompleted that is empty
     */
    public DateCompleted(boolean completed) {
        if (!completed) {
            this.date = null;
        } else {
            LocalDate currentDate = LocalDate.now();
            requireNonNull(currentDate);
            this.date = currentDate;
        }
    }

    /**
     * Returns true if a given string is a valid task deadline.
     */
    public static boolean isValidDateCompleted(String test) {
        return test.matches(DATECOMPLETED_VALIDATION_REGEX) || test.matches(TASK_NOTCOMPLETED);
    }

    /**
     * Returns String representation of DateCompleted depending on whether task is completed
     */
    public String toString() {
        if (date == null) {
            return TASK_NOTCOMPLETED;
        }
        return date.toString();
    }

    /**
     * Updates DateCompleted when status is toggled
     */
    public DateCompleted toggle() {
        if (date == null) {
            return new DateCompleted();
        }
        return new DateCompleted(false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateCompleted // instanceof handles nulls
                && this.date.equals(((DateCompleted) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
