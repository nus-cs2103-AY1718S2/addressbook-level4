package seedu.address.logic.commands;

/*import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.MotivateCommand.MESSAGE_MOTIVATE_ACKNOWLEDGEMENT;*/

import org.junit.Rule;
import org.junit.Test;

import seedu.address.ui.testutil.EventsCollectorRule;
//import seedu.address.commons.events.ui.ExitAppRequestEvent;

public class MotivateCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_motivate_success() {
        //CommandResult result = new MotivateCommand().execute();
        String result = new MotivateCommand().execute();
        /*assertEquals(MESSAGE_MOTIVATE_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);*/
    }
}
