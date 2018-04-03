package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEARCH_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_INTERVAL;

import java.time.LocalDateTime;
import java.util.Optional;

import com.calendarfx.model.Interval;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.calendar.AppointmentEntry;
import seedu.address.model.calendar.exceptions.AppointmentNotFoundException;
import seedu.address.model.calendar.exceptions.EditAppointmentFailException;

/**
 * Edit an appointment in the address book's calendar.
 */
public class EditAppointmentCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "edit_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment specified by "
            + "searching the title. Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_SEARCH_TEXT + "SEARCH TEXT "
            + PREFIX_NAME + "NEW NAME "
            + PREFIX_START_INTERVAL + "NEW START DATE TIME "
            + PREFIX_END_INTERVAL + "NEW END DATE TIME"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_SEARCH_TEXT + "Meet Peter "
            + PREFIX_START_INTERVAL + "14/08/2018 06:12 "
            + PREFIX_END_INTERVAL + "14/08/2018 07:12 ";

    public static final String MESSAGE_SUCCESS = "Appointment Edited: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_FAIL_EDIT_APPOINTMENT = "appointment do not exit or duplicate new title";

    private final String searchText;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    private AppointmentEntry appointmentEdited;


    /**
     * Creates an AddCommand to add the specified {@code AppointmentEntry}
     */
    public EditAppointmentCommand(String searchText, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(searchText);
        requireNonNull(editAppointmentDescriptor);

        this.searchText = searchText;
        this.editAppointmentDescriptor = editAppointmentDescriptor;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.editAppointment(searchText, appointmentEdited);
            return new CommandResult(String.format(MESSAGE_SUCCESS, appointmentEdited));
        } catch (EditAppointmentFailException e) {
            throw new CommandException(MESSAGE_FAIL_EDIT_APPOINTMENT);
        }

    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        AppointmentEntry appointmentToEdit;
        try {
            appointmentToEdit = model.findAppointment(searchText);
        } catch (AppointmentNotFoundException e) {
            throw new CommandException(MESSAGE_FAIL_EDIT_APPOINTMENT);
        }

        appointmentEdited = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);
    }

    /**
     * Creates and returns a {@code Appointment} with the details of Appointmententry found by SearchText
     * edited with {@code editAppointmentDescriptor}.
     */
    private static AppointmentEntry createEditedAppointment(AppointmentEntry appointmentToEdit,
                                                            EditAppointmentDescriptor descriptor) {
        requireNonNull(appointmentToEdit);

        String updatedTitle = descriptor.getGivenTitle().orElse(appointmentToEdit.getGivenTitle());
        LocalDateTime updatedStartDateTime =
                descriptor.getStartDateTime().orElse(appointmentToEdit.getStartDateTime());
        LocalDateTime updatedEndDateTime = descriptor.getEndDateTime().orElse(appointmentToEdit.getEndDateTime());
        Interval updatedInterval = new Interval(updatedStartDateTime, updatedEndDateTime);

        return new AppointmentEntry(updatedTitle, updatedInterval);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditAppointmentCommand // instanceof handles nulls
                && editAppointmentDescriptor.equals(((EditAppointmentCommand) other).editAppointmentDescriptor));
    }

    /**
     * Stores the details to edit the appointmentEntry with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditAppointmentDescriptor {
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private String givenTitle;

        public EditAppointmentDescriptor(){

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.givenTitle, this.startDateTime, this.endDateTime);
        }

        public void setStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Optional<LocalDateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public void setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;

        }

        public Optional<LocalDateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setGivenTitle(String givenTitle) {
            this.givenTitle = givenTitle;
        }

        public Optional<String> getGivenTitle() {
            return Optional.ofNullable(givenTitle);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentCommand.EditAppointmentDescriptor)) {
                return false;
            }

            // state check
            EditAppointmentCommand.EditAppointmentDescriptor e =
                    (EditAppointmentCommand.EditAppointmentDescriptor) other;

            return getEndDateTime().equals(e.getEndDateTime())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getGivenTitle().equals(e.getGivenTitle());
        }

    }
}
