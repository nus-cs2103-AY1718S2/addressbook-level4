package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.SwitchTabRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class GoogleLoginCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute() {
        GoogleLoginCommand googleLoginCommand = new GoogleLoginCommand();

        // Success feedback to user
        CommandResult commandResult = googleLoginCommand.execute();
        assertEquals(String.format(GoogleLoginCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);

        // Switch to the correct tab in details panel
        SwitchTabRequestEvent lastEvent = (SwitchTabRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(GoogleLoginCommand.TAB_ID, lastEvent.tabId);
    }
}
