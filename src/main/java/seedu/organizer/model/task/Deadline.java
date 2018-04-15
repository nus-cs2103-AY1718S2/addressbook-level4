package seedu.organizer.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Logger;

import seedu.organizer.commons.core.LogsCenter;
import seedu.organizer.commons.exceptions.IllegalValueException;

//@@author guekling
/**
 * Represents a Task's deadline in the organizer.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Task deadlines should be in the format YYYY-MM-DD, and it should not be blank. Dates should also not be "
                + "invalid (e.g. 2018-02-31 is invalid as there is no such date).";

    /*
     * The first character must not be a whitespace, otherwise " " (a blank string) becomes a valid input.
     * Format of string is YYYY-MM-DD.
     */
    public static final String DEADLINE_VALIDATION_REGEX = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])";

    public final LocalDate date;

    /**
     * Constructs an {@code Deadline}.
     *
     * @param deadline A valid deadline.
     * @throws IllegalValueException if the {@code LocalDate} class is unable to parse {@code deadline}.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_DEADLINE_CONSTRAINTS);
        this.date = LocalDate.parse(deadline);
    }

    /**
     * Returns true if a given string is a valid task deadline.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX) && isValidDate(test);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        Logger logger = LogsCenter.getLogger(Deadline.class);

        try {
            String format = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            sdf.parse(test);

            return true;

        } catch (ParseException e) {
            logger.warning("Invalid date. Deadline cannot be parsed.");

            return false;
        }
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
