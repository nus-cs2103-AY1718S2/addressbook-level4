package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SearchPersonOnFacebookEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Search for a person with the input name on facebook.
 */
public class SearchFacebookCommand extends Command {

    public static final String COMMAND_WORD = "searchfb";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search for a person with the input name on facebook.\n"
            + "Parameters: NAME (must only contain alphanumeric characters and spaces, and it should not be blank)\n"
            + "Example: " + COMMAND_WORD + " Tom";

    public static final String MESSAGE_SEARCH_PERSON_SUCCESS = "Searched Person: %1$s" + " on Facebook";

    private final String targetName;

    public SearchFacebookCommand(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new SearchPersonOnFacebookEvent(targetName));
        return new CommandResult(String.format(MESSAGE_SEARCH_PERSON_SUCCESS, targetName));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetName.equals(((SearchFacebookCommand) other).targetName)); // state check
    }
}
