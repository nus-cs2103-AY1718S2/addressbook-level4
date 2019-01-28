package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.OPTION_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_OWNER;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_PET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.petpatient.PetPatientName;

//@@author wynonaK
/**
 * A utility class for Appointment.
 */
public class AppointmentUtil {
    /**
     * Returns an add command string for adding the {@code petpatient}.
     */
    public static String getAddCommand(Appointment appt, Nric ownerNric, PetPatientName petPatientName) {
        return AddCommand.COMMAND_WORD + OPTION_APPOINTMENT + " " + getAppointmentDetails(appt)
                + OPTION_OWNER + " " + PREFIX_NRIC + ownerNric.toString() + OPTION_PET + " " + petPatientName.fullName;
    }
    /**
     * Returns the part of command string for the given {@code appointment}'s details.
     */
    public static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DATE + appointment.getFormattedLocalDateTime() + " ");
        sb.append(PREFIX_REMARK + appointment.getRemark().value + " ");
        appointment.getAppointmentTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
