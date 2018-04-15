package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DailyScheduleShownChangedEvent;
import seedu.address.commons.events.ui.RefreshDetailsPanelEvent;
import seedu.address.commons.events.ui.ResetDirectionsEvent;
import seedu.address.commons.events.ui.UpdateNumberOfButtonsEvent;
import seedu.address.logic.OAuthManager;
import seedu.address.logic.commands.exceptions.CommandException;

//@@ author kaisertanqr
/**
 * Logs user out from application
 */
public class LogoutCommand extends Command {


    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_LOGOUT_SUCCESS = "You have logged out.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        model.setLoginStatus(false);
        //@@author ifalluphill
        OAuthManager.clearCachedCalendarData();
        //@@author jaronchan
        EventsCenter.getInstance().post(new DailyScheduleShownChangedEvent(new ArrayList<>()));
        EventsCenter.getInstance().post(new ResetDirectionsEvent());
        EventsCenter.getInstance().post(new UpdateNumberOfButtonsEvent(0));
        EventsCenter.getInstance().post(new RefreshDetailsPanelEvent());
        //@@ author kaisertanqr
        return new CommandResult(MESSAGE_LOGOUT_SUCCESS);
    }

}
