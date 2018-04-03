//@@author kokonguyen191
package seedu.recipe.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.logic.commands.ChangeThemeCommand.SHOWING_CHANGE_THEME_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.recipe.commons.events.ui.ChangeThemeRequestEvent;
import seedu.recipe.ui.testutil.EventsCollectorRule;

public class ChangeThemeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_changeTheme_success() {
        CommandResult result = new ChangeThemeCommand().execute();
        assertEquals(SHOWING_CHANGE_THEME_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeThemeRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
