package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.CalendarEvent.CalendarEvent;
import seedu.address.testutil.EventBuilder;

//@@author wayneong95

/**Test for AddEvent command*/
public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }
    /* Works only on manual testing as it requires authentication by the user.
    @Test
    public void execute_event_addSuccessful() throws Exception {
        CalendarEvent validEvent = new EventBuilder().build();

        CommandResult commandResult = getAddEventCommandForEvent(validEvent).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
    } */

    /**
     * Tests for same command created by the same event.
     */
    @Test
    public void addEvent_sameEvent() {
        CalendarEvent event1 = new EventBuilder().withEventName("Weights Training").build();
        AddEventCommand command1 = new AddEventCommand(event1);
        AddEventCommand command2 = new AddEventCommand(event1);

        //Tests if command1 is equals to command2.
        assertTrue(command1.equals(command2));
    }
    /**
     * Generates a new AddEventCommand with the details of the given CalendarEvent.
     */
    private AddEventCommand getAddEventCommandForEvent(CalendarEvent event) {
        AddEventCommand command = new AddEventCommand(event);
        return command;
    }
}
