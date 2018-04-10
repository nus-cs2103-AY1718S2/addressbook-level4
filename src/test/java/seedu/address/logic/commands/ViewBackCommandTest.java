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

public class ViewBackCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_viewBackCommand_success() {
        assertShowPreviousPageSuccess();
    }

    @Test
    public void equals() {
        ViewBackCommand viewBackCommand = new ViewBackCommand();

        // same object -> returns true
        assertTrue(viewBackCommand.equals(viewBackCommand));

        // same values -> returns true
        ViewBackCommand viewBackCommandCopy = new ViewBackCommand();
        assertTrue(viewBackCommand.equals(viewBackCommandCopy));

        // different types -> returns false
        assertFalse(viewBackCommand.equals(1));

        // null -> returns false
        assertFalse(viewBackCommand.equals(null));
    }

    /**
     * Executes a {@code ViewBackCommand},
     * and checks that {@code ChangeCalendarPageRequestEvent} is raised with the Request Type "Back".
     */
    private void assertShowPreviousPageSuccess() {
        ViewBackCommand viewBackCommand = new ViewBackCommand();

        try {
            CommandResult commandResult = viewBackCommand.execute();
            assertEquals(ViewBackCommand.MESSAGE_VIEW_CALENDAR_BACK_SUCCESS,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ChangeCalendarPageRequestEvent lastEvent = (ChangeCalendarPageRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(ViewBackCommand.REQUEST_BACK, lastEvent.getRequestType());
    }
}
