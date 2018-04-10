package seedu.address.model.appointment;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;

//@@author trafalgarandre
/**
 * Represents a Appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    public static final String MESSAGE_APPOINTMENT_CONSTRAINTS = "Start date time must be before end date time.";

    private final Title title;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;

    /**
     * Every field must be present and not null.
     */
    public Appointment(Title title, StartDateTime startDateTime, EndDateTime endDateTime) {
        requireAllNonNull(title, startDateTime, endDateTime);
        try {
            checkArgument(isSdtLessThanEdt(startDateTime, endDateTime), MESSAGE_APPOINTMENT_CONSTRAINTS);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Invalid date time");
        }
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Title getTitle() {
        return title;
    }

    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Check whethere the appointment has sdt < edt
     */
    private boolean isSdtLessThanEdt(StartDateTime startDateTime, EndDateTime endDateTime)
            throws IllegalValueException {
        LocalDateTime edt = ParserUtil.parseDateTime(endDateTime.endDateTime);
        LocalDateTime sdt = ParserUtil.parseDateTime(startDateTime.startDateTime);
        return edt.isAfter(sdt);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Appointment)) {
            return false;
        }
        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getTitle().equals(this.getTitle())
                && otherAppointment.getStartDateTime().equals(this.getStartDateTime())
                && otherAppointment.getEndDateTime().equals(this.getEndDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, startDateTime, endDateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Start Date Time: ")
                .append(getStartDateTime())
                .append(" End Date Time: ")
                .append(getEndDateTime());
        return builder.toString();
    }
}
