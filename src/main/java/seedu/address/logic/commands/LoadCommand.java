//@@author ZhangYijiong
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoadPageChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Selects a person identified using it's last displayed index from the address book
 * and show the path to the address of the person identified
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Load the inputted web page "
            + "Parameters: web page link\n"
            + "Example: " + COMMAND_WORD + " https://www.google.com.sg "
            + "WARNING: PLEASE ENSURE URL ENTERED IS VALID AS SYSTEM WOULD NOT "
            + " FEEDBACK ERROR MESSAGE WHEN PAGE IS INVALID";

    public static final String MESSAGE_LOAD_PAGE_SUCCESS = "Load Page: %1$s, need to have https://\n"
            + "It could take a while if internet connection is slow.\n"
            + "However, if page does not load after a long time, please double check url entered";

    private final String url;

    public LoadCommand(String url) {
        this.url = url;
    }

    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new LoadPageChangedEvent(url));
        return new CommandResult(String.format(MESSAGE_LOAD_PAGE_SUCCESS, url));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoadCommand // instanceof handles nulls
                && this.url.equals(((LoadCommand) other).url)); // state check
    }
}


