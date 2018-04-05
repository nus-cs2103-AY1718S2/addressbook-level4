package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SearchPersonOnAllPlatformEvent;
import seedu.address.logic.commands.exceptions.CommandException;

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

    private final String targetName;

    public SearchAllCommand(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new SearchPersonOnAllPlatformEvent(targetName));
        return new CommandResult(String.format(MESSAGE_SEARCH_PERSON_SUCCESS, targetName));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetName.equals(((SearchAllCommand) other).targetName)); // state check
    }
}
