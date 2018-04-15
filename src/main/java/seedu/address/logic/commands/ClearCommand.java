package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ResetPersonPanelRequestEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;

/**
 * Clears the list of persons, orders and calendar entries in ContactSails.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBook(), new CalendarManager());
        EventsCenter.getInstance().post(new ResetPersonPanelRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
