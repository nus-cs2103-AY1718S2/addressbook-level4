package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Search for a person with the input name on all available social media platform.
 */
public class SearchAllCommand extends Command {

    public static final String COMMAND_WORD = "searchall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search for a person with the input name on all available social media platform.\n"
            + "Parameters: NAME (must only contain alphanumeric characters and spaces, and it should not be blank)\n"
            + "Example: " + COMMAND_WORD + " Tom";

    public static final String MESSAGE_SEARCH_PERSON_SUCCESS = "Searched Person: %1$s";

    private final Index targetIndex;

    public SearchAllCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SEARCH_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SearchAllCommand) other).targetIndex)); // state check
    }
}
