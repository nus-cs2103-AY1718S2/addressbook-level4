package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author ongkuanyang
/**
 * Archives a person identified using it's last displayed index from the address book.
 */
public class ArchiveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "archive";
    public static final String COMMAND_ALIAS = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_ARCHIVED = "Person is already archived!";

    private final Index targetIndex;

    private Person personToArchive;

    public ArchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(personToArchive);
        try {
            model.archivePerson(personToArchive);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS, personToArchive));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToArchive = lastShownList.get(targetIndex.getZeroBased());

        if (personToArchive.isArchived()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_ARCHIVED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveCommand // instanceof handles nulls
                && this.targetIndex.equals(((ArchiveCommand) other).targetIndex) // state check
                && Objects.equals(this.personToArchive, ((ArchiveCommand) other).personToArchive));
    }
}
