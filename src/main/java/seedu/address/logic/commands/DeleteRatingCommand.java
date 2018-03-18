package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes the rating of a person identified using it's last displayed index from HR+.
 */
public class DeleteRatingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteRating";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the rating of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RATING_SUCCESS = "Deleted rating of person named %1$s";

    private final Index targetIndex;

    private Person targetPerson;

    public DeleteRatingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(targetPerson);

        // DELETE RATING!

        return new CommandResult(String.format(MESSAGE_DELETE_RATING_SUCCESS, targetPerson.getName()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        targetPerson = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRatingCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteRatingCommand) other).targetIndex) // state check
                && Objects.equals(this.targetPerson, ((DeleteRatingCommand) other).targetPerson));
    }
}
