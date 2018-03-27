//@@author ongkuanyang
package seedu.address.model.appointment;

import static java.time.ZoneId.getAvailableZoneIds;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an appointment's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class AppointmentTime {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Year, month, day, hour and minute should constitute a valid date and time. " +
            "Timezone should be of the form '{area}/{city}', such as 'Europe/Paris' or 'America/New_York'";

    public final ZonedDateTime time;

    /**
     * Constructs an {@code AppointmentTime}
     *
     * @param year
     * @param month
     * @param dayOfMonth
     * @param hour
     * @param minute
     * @param timezone region IDs of the form '{area}/{city}', such as 'Europe/Paris' or 'America/New_York'
     */
    public AppointmentTime(int year, int month, int dayOfMonth, int hour, int minute, String timezone) {
        requireNonNull(timezone);
        checkArgument(isValidTime(year, month, dayOfMonth, hour, minute, timezone), MESSAGE_TIME_CONSTRAINTS);
        this.time = ZonedDateTime.of(LocalDateTime.of(year, month, dayOfMonth, hour, minute), ZoneId.of(timezone));
    }

    /**
     * Returns true if a parameters give a valid time and timezone.
     */
    public static boolean isValidTime(int year, int month, int dayOfMonth, int hour, int minute, String timezone) {
        try {
            LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        } catch (DateTimeException e) {
            return false;
        }

        return getAvailableZoneIds().contains(timezone);
    }


    @Override
    public String toString() {
        return time.format(DateTimeFormatter.ofPattern("d MMM uuuu HH:mm VV"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentTime // instanceof handles nulls
                && this.time.equals(((AppointmentTime) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

}
//@@author
