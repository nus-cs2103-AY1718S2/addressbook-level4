package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.appointment.Appointment;

//@@author wynonaK
/**
 * A utility class for Appointment.
 */
public class AppointmentUtil {
    /**
     * Returns the part of command string for the given {@code appointment}'s details.
     */
    public static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NRIC + appointment.getOwnerNric().toString() + " ");
        sb.append(PREFIX_NAME + appointment.getPetPatientName().toString() + " ");
        sb.append(PREFIX_REMARK + appointment.getRemark().value + " ");
        sb.append(PREFIX_DATE + appointment.getFormattedLocalDateTime() + " ");
        appointment.getAppointmentTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
