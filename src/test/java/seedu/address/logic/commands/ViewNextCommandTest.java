package seedu.address.logic.commands;
//@@author SuxianAlicia
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ViewNextCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_viewNextCommand_success() {
        assertShowNextPageSuccess();
    }

    @Test
    public void equals() {
        ViewNextCommand viewNextFirstCommand = new ViewNextCommand();

        // same object -> returns true
        assertTrue(viewNextFirstCommand.equals(viewNextFirstCommand));

        // same values -> returns true
        ViewNextCommand viewNextFirstCommandCopy = new ViewNextCommand();
        assertTrue(viewNextFirstCommand.equals(viewNextFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewNextFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewNextFirstCommand.equals(null));
    }

    /**
     * Executes a {@code ViewNextCommand},
     * and checks that {@code ChangeCalendarPageRequestEvent} is raised with the Request Type "Next".
     */
    private void assertShowNextPageSuccess() {
        ViewNextCommand viewNextCommand = new ViewNextCommand();

        try {
            CommandResult commandResult = viewNextCommand.execute();
            assertEquals(ViewNextCommand.MESSAGE_VIEW_CALENDAR_NEXT_SUCCESS,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ChangeCalendarPageRequestEvent lastEvent = (ChangeCalendarPageRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(ViewNextCommand.REQUEST_NEXT, lastEvent.getRequestType());
    }
}
