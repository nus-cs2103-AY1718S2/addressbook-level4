package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_PERSONS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author ongkuanyang
/**
 * Unarchives a person identified using it's last displayed index from the address book.
 */
public class UnarchiveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unarchive";
    public static final String COMMAND_ALIAS = "uar";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_UNARCHIVED = "Person is already not archived!";

    private final Index targetIndex;

    private Person personToUnarchive;

    public UnarchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(personToUnarchive);
        try {
            model.unarchivePerson(personToUnarchive);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_UNARCHIVED_PERSONS);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS, personToUnarchive));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToUnarchive = lastShownList.get(targetIndex.getZeroBased());

        if (!personToUnarchive.isArchived()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_UNARCHIVED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnarchiveCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnarchiveCommand) other).targetIndex) // state check
                && Objects.equals(this.personToUnarchive, ((UnarchiveCommand) other).personToUnarchive));
    }
}
