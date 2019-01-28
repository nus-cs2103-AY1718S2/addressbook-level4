package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.testutil.TypicalThemes;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author aquarinte
public class ChangeThemeCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void constructor_nullTheme_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeThemeCommand(null);
    }

    @Test
    public void execute_lightTheme_changeThemeSuccessful() {
        CommandResult result = new ChangeThemeCommand(TypicalThemes.LIGHT).execute();
        assertEquals("Current theme: light", result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeThemeRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_darkTheme_changeThemeSuccessful() {
        CommandResult result = new ChangeThemeCommand(TypicalThemes.DARK).execute();
        assertEquals("Current theme: dark", result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeThemeRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
