package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowChartsEvent;

//@@author dannyngmx94
/**
 * Show statistical data from the address book.
 */
public class ViewStatsCommand extends Command {

    public static final String COMMAND_WORD = "viewStats";

    public static final String MESSAGE_VIEW_STATS_SUCCESS = "Show chart";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowChartsEvent());
        return new CommandResult(MESSAGE_VIEW_STATS_SUCCESS);

    }
}
