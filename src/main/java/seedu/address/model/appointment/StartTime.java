package seedu.address.model.appointment;

//@@author jlks96
/**
 * Represents an Appointment's starting time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class StartTime extends Time {

    /**
     * Constructs a {@code StartTime}.
     *
     * @param startTime A valid startTime.
     */
    public StartTime(String startTime) {
        super(startTime);
    }
}
