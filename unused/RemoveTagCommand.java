package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author alexawangzi
/**
 * Deletes a tag from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeTag";
    public static final String COMMAND_WORD_ALIAS = "rmt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a tag from the addressbook.\n"
            + "Parameters: tag name(must be a valid tag existing in addressbook\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";

    private Tag targetTag;

    public RemoveTagCommand(Tag targetTag) {
        this.targetTag = targetTag;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(targetTag);
        try {
            model.deleteTag(targetTag);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, targetTag));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        Set<Tag> tagsInPersons = lastShownList.stream()
                .map(Person::getTags)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
        if (!tagsInPersons.contains(targetTag)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.targetTag.equals(((RemoveTagCommand) other).targetTag)); // state check
    }
}

