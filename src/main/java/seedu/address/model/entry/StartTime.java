package seedu.address.model.entry;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.TimeUtil.convertStringToTime;
import static seedu.address.commons.util.TimeUtil.isValidTime;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Represents starting Time of a {@code CalendarEntry}.
 * Guarantees: immutable; is valid as declared in {@link seedu.address.commons.util.TimeUtil#isValidTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_START_TIME_CONSTRAINTS =
            "Start Time should be HH:mm (24Hour Format), and it should not be blank";

    private final String startTimeString;
    private final LocalTime startTime;

    /**
     * Constructs {@code StartTime}.
     *
     * @param startTime Valid start time.
     */
    public StartTime (String startTime) {
        requireNonNull(startTime);
        checkArgument(isValidTime(startTime), MESSAGE_START_TIME_CONSTRAINTS);

        try {
            this.startTime = convertStringToTime(startTime);
            this.startTimeString = startTime;
        } catch (DateTimeParseException dtpe) {
            throw new AssertionError("Given Start time should be valid for conversion.");
        }
    }

    public LocalTime getLocalTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return startTimeString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.startTime.equals(((StartTime) other).startTime)
                && this.startTimeString.equals(((StartTime) other).startTimeString)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }
}
