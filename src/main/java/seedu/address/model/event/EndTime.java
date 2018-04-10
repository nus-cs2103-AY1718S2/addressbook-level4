package seedu.address.model.event;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.TimeUtil.convertStringToTime;
import static seedu.address.commons.util.TimeUtil.isValidTime;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Represents ending Time of a {@code CalendarEntry}.
 * Guarantees: immutable; is valid as declared in {@link seedu.address.commons.util.TimeUtil#isValidTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_END_TIME_CONSTRAINTS =
            "End Time should be HH:mm (24Hour Format), and it should not be blank";

    private final String endTimeString;
    private final LocalTime endTime;

    /**
     * Constructs {@code EndTime}.
     * @param endTime Valid end time.
     */
    public EndTime (String endTime) {
        requireNonNull(endTime);
        checkArgument(isValidTime(endTime), MESSAGE_END_TIME_CONSTRAINTS);
        try {
            this.endTime = convertStringToTime(endTime);
            this.endTimeString = endTime;
        } catch (DateTimeParseException dtpe) {
            throw new AssertionError("Given start time should be valid for conversion.");
        }
    }

    public LocalTime getLocalTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return endTimeString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.endTime.equals(((EndTime) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }
}
