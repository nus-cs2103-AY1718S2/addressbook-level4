package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.SessionLogs;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds a session log to an existing person in the address book.
 */
public class AddSessionLogCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add-log";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds to session log of "
            + "by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) "
            +  PREFIX_LOG + "LOG "
            + "Example: " + COMMAND_WORD
            + " 1 "
            + PREFIX_LOG + "The patient report that he has been feeling down lately. She has a flat affect and "
            + "speaks in a quiet tone of voice...";

    public static final String MESSAGE_ADD_SESSION_LOG_SUCCESS = "Added new log to %1$s";

    public static final String SESSION_LOG_DIVIDER = "\n\n======================================================"
            + "===============";
    public static final String SESSION_LOG_DATE_PREFIX = "\nSession log date added: ";


    private final Index index;
    private final String sessionLogsToAdd;

    private Person personToReplace;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param sessionLogsToAdd details to edit the person with
     */
    public AddSessionLogCommand(Index index, String sessionLogsToAdd) {
        requireNonNull(index);
        requireNonNull(sessionLogsToAdd);

        this.index = index;
        this.sessionLogsToAdd = sessionLogsToAdd;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        try {
            model.addLogToPerson(personToReplace, editedPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_SESSION_LOG_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToReplace = lastShownList.get(index.getZeroBased());
        editedPerson = createNewPerson(personToReplace, sessionLogsToAdd);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createNewPerson(Person personToReplace, String sessionLogsToAdd) {
        assert personToReplace != null;

        Name name = personToReplace.getName();
        Phone phone = personToReplace.getPhone();
        Email email = personToReplace.getEmail();
        Address address = personToReplace.getAddress();
        Set<Tag> tags = personToReplace.getTags();
        SessionLogs sessionLogs = new SessionLogs(personToReplace.getSessionLogs().toString()
                + formatNewSessionLog(sessionLogsToAdd));

        return new Person(name, phone, email, address, tags, sessionLogs);
    }

    /**
     * Creates and returns a {@code String} with the formatted session Lo {@code personToEdit}
     * @param sessionLogsToAdd
     * @return
     */
    private static String formatNewSessionLog(String sessionLogsToAdd) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return SESSION_LOG_DIVIDER + SESSION_LOG_DATE_PREFIX + dateFormat.format(date)+ "\n\n"
                + sessionLogsToAdd + SESSION_LOG_DIVIDER;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // state check
        AddSessionLogCommand e = (AddSessionLogCommand) other;
        return index.equals(e.index)
                && Objects.equals(personToReplace, e.personToReplace);
    }

}
