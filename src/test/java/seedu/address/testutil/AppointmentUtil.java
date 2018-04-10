package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.model.appointment.Appointment;

//@@author trafalgarandre
/**
 * A utility class for Appointment.
 */
public class AppointmentUtil {

    /**
     * Returns an addAppointment command string for adding the {@code appointment}.
     */
    public static String getAddAppointmentCommand(Appointment appointment) {
        return AddAppointmentCommand.COMMAND_WORD + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns the part of command string for the given {@code appointment}'s details.
     */
    public static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + appointment.getTitle().title + " ");
        sb.append(PREFIX_START_DATE_TIME + appointment.getStartDateTime().startDateTime + " ");
        sb.append(PREFIX_END_DATE_TIME + appointment.getEndDateTime().endDateTime + " ");
        return sb.toString();
    }
}
