package seedu.address.logic.commands;
//@@author SuxianAlicia
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayCalendarEntryListEvent;
import seedu.address.model.Model;

/**
 * List and display all calendar entries in the address book to the user.
 */
public class ListCalendarEntryCommand extends Command {

    public static final String COMMAND_WORD = "entrylist";
    public static final String COMMAND_ALIAS = "el";

    public static final String MESSAGE_SUCCESS = "Listed all calendar entries";

    @Override
    public CommandResult execute() {
        model.updateFilteredCalendarEntryList(Model.PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);
        EventsCenter.getInstance().post(new DisplayCalendarEntryListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
