package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.HideDetailPanelEvent;
import seedu.address.logic.LogicManager;
import seedu.address.model.person.HideAllPersonPredicate;


/**
 * Locks the app with a password
 * */
public class LockCommand extends Command {

    public static final String COMMAND_WORD = "lock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been locked!";

    private final HideAllPersonPredicate predicate = new HideAllPersonPredicate();

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new HideDetailPanelEvent());
        LogicManager.lock();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
