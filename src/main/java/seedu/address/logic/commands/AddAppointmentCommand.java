//@@author ongkuanyang
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEZONE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentName;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person to the address book.
 */
public class AddAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addappointment";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an appointment to the address book, persons are identified "
            + "by the index number used in the last person listing. "
            + "Parameters: "
            + "[INDEX (must be a positive integer)]... "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATETIME + "DATETIME "
            + PREFIX_TIMEZONE + "TIMEZONE\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 2 "
            + PREFIX_NAME + "Promote laptop "
            + PREFIX_DATETIME + "2018-06-13 13:25 "
            + PREFIX_TIMEZONE + "America/New_York";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book";

    private Appointment toAdd;
    private final AppointmentName name;
    private final AppointmentTime time;
    private final List<Index> indexes;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(AppointmentName name, AppointmentTime time, List<Index> indexes) {
        requireNonNull(name);
        requireNonNull(time);
        requireNonNull(indexes);
        this.name = name;
        this.time = time;
        this.indexes = indexes;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addAppointment(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        UniquePersonList persons = new UniquePersonList();

        for (Index index : indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            try {
                persons.add(lastShownList.get(index.getZeroBased()));
            } catch (DuplicatePersonException e) {
                // Ignore duplicate
            }
        }

        toAdd = new Appointment(name, time, persons);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAppointmentCommand) other).toAdd)
                && name.equals(((AddAppointmentCommand) other).name)
                && time.equals(((AddAppointmentCommand) other).time)
                && indexes.equals(((AddAppointmentCommand) other).indexes));
    }
}
//@@ author
