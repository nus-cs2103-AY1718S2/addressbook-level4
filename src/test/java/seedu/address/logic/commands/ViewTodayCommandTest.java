package seedu.address.logic.commands;
//@@author SuxianAlicia
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_TODAY;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ViewTodayCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_viewTodayCommand_success() {
        assertShowTodaySuccess();
    }

    @Test
    public void equals() {
        ViewTodayCommand viewTodayCommand = new ViewTodayCommand();

        // same object -> returns true
        assertTrue(viewTodayCommand.equals(viewTodayCommand));

        // same values -> returns true
        ViewTodayCommand viewTodayCommandCopy = new ViewTodayCommand();
        assertTrue(viewTodayCommand.equals(viewTodayCommandCopy));

        // different types -> returns false
        assertFalse(viewTodayCommand.equals(1));

        // null -> returns false
        assertFalse(viewTodayCommand.equals(null));
    }

    /**
     * Executes a {@code ViewTodayCommand},
     * and checks that {@code ChangeCalendarPageRequestEvent} is raised with the Request Type "Today".
     */
    private void assertShowTodaySuccess() {
        ViewTodayCommand viewNextCommand = new ViewTodayCommand();

        try {
            CommandResult commandResult = viewNextCommand.execute();
            assertEquals(ViewTodayCommand.MESSAGE_VIEW_CALENDAR_TODAY_SUCCESS,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ChangeCalendarPageRequestEvent lastEvent = (ChangeCalendarPageRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(REQUEST_TODAY, lastEvent.getRequestType());
    }
}
