package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowNotifManRequestEvent;

/**
 * Lists all coins in the address book to the user.
 */
public class ListNotifsCommand extends Command {

    public static final String COMMAND_WORD = "listnotifs";
    public static final String COMMAND_ALIAS = "ln";

    public static final String MESSAGE_SUCCESS = "Opening notification manager...";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowNotifManRequestEvent(model.getRuleList()));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
