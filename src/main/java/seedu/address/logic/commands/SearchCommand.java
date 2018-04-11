package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SearchPersonEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.smplatform.Facebook;
import seedu.address.model.smplatform.Twitter;

//@@author KevinChuangCH
/**
 * Search for a person with the input name either on all available social media platforms
 * or the stated social media platform if it is available..
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search for a person with the input name on the input social media platform if it is available.\n"
            + "If no platform is stated, then the search will be performed on all available social media platforms.\n"
            + "User can use alias to indicate the social media platform.\n"
            + "Parameters: [PLATFORM], NAME (must only contain alphanumeric characters and spaces, and it should not be blank)\n"
            + "Example: " + COMMAND_WORD + " " + Facebook.PLATFORM_KEYWORD + ", Tom\n"
            + "or\n"
            + "Example: " + COMMAND_WORD + " " + Twitter.PLATFORM_ALIAS + ", Sam\n"
            + "or\n"
            + "Example: " + COMMAND_WORD + " Jason\n";

    public static final String MESSAGE_SEARCH_PERSON_SUCCESS = "Searched Person: %1$s";

    private final String targetPlatform;
    private final String targetName;

    public SearchCommand(String targetPlatform, String targetName) {
        this.targetName = targetName;
        this.targetPlatform = targetPlatform;
    }

    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new SearchPersonEvent(targetPlatform, targetName));
        return new CommandResult(String.format(MESSAGE_SEARCH_PERSON_SUCCESS, targetName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetName.equals(((SearchCommand) other).targetName)); // state check
    }
}
