//@@author ongkuanyang
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEZONE;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentName;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Edits the details of an existing appointment in the address book.
 */
public class EditAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editappointment";
    public static final String COMMAND_ALIAS = "ea";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment identified "
            + "by the index number used in the last appontment listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[PERSON INDEX (must be a positive integer)]..."
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_TIMEZONE + "TIMEZONE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "3 4 "
            + PREFIX_NAME + "Sell laptop "
            + PREFIX_DATETIME + "2018-06-13 13:25";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book.";

    private final Index index;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    private Appointment appointmentToEdit;
    private Appointment editedAppointment;

    /**
     * @param index of the appointment in the filtered appointment list to edit
     * @param editAppointmentDescriptor details to edit the appointment with
     */
    public EditAppointmentCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAppointmentDescriptor);

        this.index = index;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateAppointment(appointmentToEdit, editedAppointment);
        } catch (DuplicateAppointmentException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        } catch (AppointmentNotFoundException anfe) {
            throw new AssertionError("The target appointment cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        appointmentToEdit = lastShownList.get(index.getZeroBased());
        editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit, EditAppointmentDescriptor editAppointmentDescriptor) {
        assert appointmentToEdit != null;

        AppointmentName updatedName = editAppointmentDescriptor.getName().orElse(appointmentToEdit.getName());
        AppointmentTime updatedTime = editAppointmentDescriptor.getTime().orElse(appointmentToEdit.getTime());
        ObservableList<Person> personList = editAppointmentDescriptor.getPersons().orElse(appointmentToEdit.getPersons());
        UniquePersonList updatedPersons = new UniquePersonList();
        try {
            updatedPersons.setPersons(personList);
        } catch (DuplicatePersonException e) {
            // Ignore exception, just don't add the person
        }

        return new Appointment(updatedName, updatedTime, updatedPersons);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentCommand)) {
            return false;
        }

        // state check
        EditAppointmentCommand e = (EditAppointmentCommand) other;
        return index.equals(e.index)
                && editAppointmentDescriptor.equals(e.editAppointmentDescriptor)
                && Objects.equals(appointmentToEdit, e.appointmentToEdit);
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditAppointmentDescriptor {
        private AppointmentName name;
        private AppointmentTime time;
        private UniquePersonList persons;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code persons} is used internally.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setName(toCopy.name);
            setTime(toCopy.time);
            UniquePersonList newPersons = new UniquePersonList();
            newPersons.setPersons(toCopy.persons);
            setPersons(newPersons);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.time, this.persons);
        }

        public void setName(AppointmentName name) {
            this.name = name;
        }

        public Optional<AppointmentName> getName() {
            return Optional.ofNullable(name);
        }

        public void setTime(AppointmentTime time) {
            this.time = time;
        }

        public Optional<AppointmentTime> getTime() {
            return Optional.ofNullable(time);
        }

        public void setPersons(UniquePersonList persons) {
            this.persons = persons;
        }

        public Optional<ObservableList<Person>> getPersons() {
            return Optional.ofNullable(Objects.isNull(persons) ? null : persons.asObservableList());
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            }

            // state check
            EditAppointmentDescriptor e = (EditAppointmentDescriptor) other;

            return getName().equals(e.getName())
                    && getTime().equals(e.getTime())
                    && getPersons().equals(e.getPersons());
        }
    }
}
