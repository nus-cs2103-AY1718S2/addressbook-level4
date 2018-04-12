package seedu.address.model.appointment;

//@@author jlks96
/**
 * Represents an Appointment's ending time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EndTime extends Time {

    /**
     * Constructs a {@code EndTime}.
     *
     * @param endTime A valid endTime.
     */
    public EndTime(String endTime) {
        super(endTime);
    }
}
