package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person shown in the main window.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private ReadOnlyPerson personToDelete;
    private Name name;

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        return getCommandResult();
    }

    private CommandResult getCommandResult() throws CommandException {
        try {
            this.name = personToDelete.getName();
            model.deletePerson();
            model.requestHideTimetable();
        } catch (NullPointerException npe) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }
        requireNonNull(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, name));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            this.personToDelete = model.getPartner();
        } catch (NullPointerException npe) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && Objects.equals(this.personToDelete, ((DeleteCommand) other).personToDelete));
    }

}
