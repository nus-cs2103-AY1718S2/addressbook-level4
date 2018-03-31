//@@author jaronchan
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SwitchCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_switchFeature_success() {
        CommandResult result = new SwitchCommand("details").execute();
        assertEquals("Switched to details tab", result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof LoadMapPanelEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 3);
    }
}
