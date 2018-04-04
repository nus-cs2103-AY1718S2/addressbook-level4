package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_INTERVAL;

import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.calendar.AppointmentEntry;
//@@author yuxiangSg
/**
 * A utility class for AppointmentEntry.
 */
public class AppointmentUtil {
    /**
     * Returns an addAppointment command string for adding the {@code entry}.
     */
    public static String getAddAppointmentCommand(AppointmentEntry entry) {
        return AddAppointmentCommand.COMMAND_WORD + " " + geEntryDetails(entry);
    }

    /**
     * Returns the part of command string for the given {@code entry}'s details.
     */
    public static String geEntryDetails(AppointmentEntry entry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppointmentEntry.DATE_VALIDATION);
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + entry.getGivenTitle() + " ");
        sb.append(PREFIX_START_INTERVAL + entry.getStartDateTime().format(formatter) + " ");
        sb.append(PREFIX_END_INTERVAL  + entry.getEndDateTime().format(formatter));

        return sb.toString();
    }
}
