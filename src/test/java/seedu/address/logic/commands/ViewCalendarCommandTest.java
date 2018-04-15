package seedu.address.logic.commands;
//@@author SuxianAlicia
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.util.CalendarFxUtil.DAY_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.MONTH_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.WEEK_VIEW;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ChangeCalendarViewRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ViewCalendarCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_showDayView_success() {
        assertShowDayViewSuccess("Day"); // Matches exact keyword in ViewCalendarCommand.DAY_VIEW
        assertShowDayViewSuccess("DAY"); // Case insensitive -> Success
        assertShowDayViewSuccess(""); // Empty string -> Success as Calendar shows Day page by default.
        assertShowDayViewSuccess("invalidDay"); // Parameter not matching the accepted keyword -> Success
    }

    @Test
    public void execute_showWeekView_success() {
        assertShowWeekViewSuccess("Week"); // Matches exact keyword in ViewCalendarCommand.WEEK_VIEW
        assertShowWeekViewSuccess("WEEK"); // Case insensitive -> Success
        assertShowWeekViewSuccess("  Week  "); //Trailing whitespaces -> Success
    }

    @Test
    public void execute_showMonthView_success() {
        assertShowMonthViewSuccess("Month"); // Matches exact keyword in ViewCalendarCommand.MONTH_VIEW
        assertShowMonthViewSuccess("MONTH"); // Case insensitive -> Success
        assertShowMonthViewSuccess("  month  "); //Trailing whitespaces -> Success
    }

    @Test
    public void equals() {
        ViewCalendarCommand viewCalendarFirstCommand = new ViewCalendarCommand(DAY_VIEW);
        ViewCalendarCommand viewCalendarSecondCommand = new ViewCalendarCommand(WEEK_VIEW);

        // same object -> returns true
        assertTrue(viewCalendarFirstCommand.equals(viewCalendarFirstCommand));

        // same values -> returns true
        ViewCalendarCommand selectFirstCommandCopy = new ViewCalendarCommand(DAY_VIEW);
        assertTrue(viewCalendarFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewCalendarFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewCalendarFirstCommand.equals(null));

        // different view -> returns false
        assertFalse(viewCalendarFirstCommand.equals(viewCalendarSecondCommand));
    }

    /**
     * Executes a {@code ViewCalendarCommand} with the given {@code view},
     * and checks that {@code ChangeCalendarViewRequestEvent} is raised with the day view.
     */
    private void assertShowDayViewSuccess(String view) {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(view);

        try {
            CommandResult commandResult = viewCalendarCommand.execute();
            assertEquals(String.format(ViewCalendarCommand.MESSAGE_SHOW_CALENDAR_SUCCESS, DAY_VIEW),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeCalendarViewRequestEvent);
        ChangeCalendarViewRequestEvent lastEvent = (ChangeCalendarViewRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(DAY_VIEW, lastEvent.getView());
    }

    /**
     * Executes a {@code ViewCalendarCommand} with the given {@code view},
     * and checks that {@code ChangeCalendarViewRequestEvent} is raised with the week view.
     */
    private void assertShowWeekViewSuccess(String view) {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(view);

        try {
            CommandResult commandResult = viewCalendarCommand.execute();
            assertEquals(String.format(ViewCalendarCommand.MESSAGE_SHOW_CALENDAR_SUCCESS,
                    WEEK_VIEW), commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeCalendarViewRequestEvent);
        ChangeCalendarViewRequestEvent lastEvent = (ChangeCalendarViewRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(WEEK_VIEW, lastEvent.getView());
    }

    /**
     * Executes a {@code ViewCalendarCommand} with the given {@code view},
     * and checks that {@code ChangeCalendarViewRequestEvent} is raised with the month view.
     */
    private void assertShowMonthViewSuccess(String view) {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(view);

        try {
            CommandResult commandResult = viewCalendarCommand.execute();
            assertEquals(String.format(ViewCalendarCommand.MESSAGE_SHOW_CALENDAR_SUCCESS,
                    MONTH_VIEW), commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeCalendarViewRequestEvent);
        ChangeCalendarViewRequestEvent lastEvent = (ChangeCalendarViewRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(MONTH_VIEW, lastEvent.getView());
    }
}
