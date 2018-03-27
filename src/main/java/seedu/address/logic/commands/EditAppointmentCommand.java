package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEARCH_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_INTERVAL;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.calendar.AppointmentEntry;
import seedu.address.model.calendar.exceptions.EditApointmentFailException;

/**
 * Edit an appointment in the address book's calendar.
 */
public class EditAppointmentCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "edit_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an appointment in the calendar. "
            + "Parameters: "
            + PREFIX_SEARCH_TEXT + "SEARCH TEXT "
            + PREFIX_NAME + "NAME "
            + PREFIX_START_INTERVAL + "START DATE TIME "
            + PREFIX_END_INTERVAL + "END DATE TIME"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_SEARCH_TEXT + "Meet Peter "
            + PREFIX_NAME + "Meet John "
            + PREFIX_START_INTERVAL + "14/08/2018 06:12:00 "
            + PREFIX_END_INTERVAL + "14/08/2018 07:12:00 ";

    public static final String MESSAGE_SUCCESS = "Appointment Edited: %1$s";
    public static final String MESSAGE_FAIL_EDIT_APPOINTMENT = "appointment do not exit or duplicate new title";

    private final AppointmentEntry toEdit;
    private final String searchText;

    /**
     * Creates an AddCommand to add the specified {@code AppointmentEntry}
     */
    public EditAppointmentCommand(String searchText, AppointmentEntry appointmentEntry) {
        requireNonNull(appointmentEntry);
        toEdit = appointmentEntry;
        this.searchText = searchText;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.editAppointment(searchText, toEdit);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));
        } catch (EditApointmentFailException e) {
            throw new CommandException(MESSAGE_FAIL_EDIT_APPOINTMENT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditAppointmentCommand // instanceof handles nulls
                && toEdit.equals(((EditAppointmentCommand) other).toEdit));
    }
}
