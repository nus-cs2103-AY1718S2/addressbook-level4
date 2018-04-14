package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ResetPersonPanelRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CalendarManager;

/**
 * Clears the calendar entry list.
 */
public class EntryListClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "entryclear";
    public static final String COMMAND_ALIAS = "ec";
    public static final String MESSAGE_SUCCESS = "Calendar Entry List has been cleared!";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        model.resetData(model.getAddressBook(), new CalendarManager());
        EventsCenter.getInstance().post(new ResetPersonPanelRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
