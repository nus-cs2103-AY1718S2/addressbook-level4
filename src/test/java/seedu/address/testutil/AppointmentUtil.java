package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.model.appointment.Appointment;

//@@author jlks96
/**
 * A utility class for Appointment
 */
public class AppointmentUtil {
    /**
     * Returns an add appointment command string for adding the {@code appointment}.
     */
    public static String getAddAppointmentCommand(Appointment appointment) {
        return AddAppointmentCommand.COMMAND_WORD + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns an add appointment alias string for adding the {@code appointment}.
     */
    public static String getAddAppointmentAlias(Appointment appointment) {
        return AddAppointmentCommand.COMMAND_ALIAS + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns a delete appointment command string for deleting the {@code appointment}.
     */
    public static String getDeleteAppointmentCommand(Appointment appointment) {
        return DeleteAppointmentCommand.COMMAND_WORD + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns a delete appointment alias string for deleting the {@code appointment}.
     */
    public static String getDeleteAppointmentAlias(Appointment appointment) {
        return DeleteAppointmentCommand.COMMAND_ALIAS + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns the part of command string for the given {@code appointment}'s details.
     */
    public static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + appointment.getName().fullName + " ");
        sb.append(PREFIX_DATE + appointment.getDate().date + " ");
        sb.append(PREFIX_STARTTIME + appointment.getStartTime().time + " ");
        sb.append(PREFIX_ENDTIME + appointment.getEndTime().time + " ");
        sb.append(PREFIX_LOCATION + appointment.getLocation().value + " ");
        return sb.toString();
    }
}
