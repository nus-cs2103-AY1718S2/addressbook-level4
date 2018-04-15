package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.appointment.TypicalCalendar.FIRST_DATE_TIME;
import static seedu.address.commons.core.appointment.TypicalCalendar.SECOND_DATE_TIME;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowDateTimeRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.DateTimeCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author trafalgarandre
public class DateTimeCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validDateTime_success() {
        assertExecutionSuccess("2018-02-27 12:00");
    }

    @Test
    public void execute_invalidDateTime_failure() {
        assertExecutionFailure("a", DateTimeCommand.MESSAGE_DATE_TIME_CONSTRAINTS);
    }

    @Test
    public void equals() {
        DateTimeCommand dateTimeFirstCommand = new DateTimeCommand(FIRST_DATE_TIME);
        DateTimeCommand dateTimeSecondCommand = new DateTimeCommand(SECOND_DATE_TIME);

        // same object -> returns true
        assertTrue(dateTimeFirstCommand.equals(dateTimeFirstCommand));

        // same values -> returns true
        DateTimeCommand dateTimeFirstCommandCopy = new DateTimeCommand(FIRST_DATE_TIME);
        assertTrue(dateTimeFirstCommand.equals(dateTimeFirstCommandCopy));

        // different types -> returns false
        assertFalse(dateTimeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(dateTimeFirstCommand.equals(null));

        // different date -> returns false
        assertFalse(dateTimeFirstCommand.equals(dateTimeSecondCommand));
    }

    /**
     * Executes a {@code dateTimeCommand} with the given {@code dateTime},
     * and checks that {@code handleShowDateRequestEvent}
     * is raised with the correct date.
     */
    private void assertExecutionSuccess(String dateTime) {

        try {
            DateTimeCommand dateTimeCommand = prepareCommand(dateTime);
            CommandResult commandResult = dateTimeCommand.execute();
            assertEquals(String.format(DateTimeCommand.MESSAGE_SUCCESS, dateTime),
                    commandResult.feedbackToUser);

            ShowDateTimeRequestEvent lastEvent =
                    (ShowDateTimeRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(ParserUtil.parseDateTime(dateTime), lastEvent.targetDateTime);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes a {@code DateTimeCommand} with the given {@code dateTime}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String dateTime, String expectedMessage) {

        try {
            DateTimeCommand dateTimeCommand = prepareCommand(dateTime);
            dateTimeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (IllegalValueException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code DateTimeCommand} with parameters {@code dateTime}.
     */
    private DateTimeCommand prepareCommand(String dateTime) throws IllegalValueException {
        DateTimeCommand dateTimeCommand = new DateTimeCommand(ParserUtil.parseDateTime(dateTime));
        return dateTimeCommand;
    }
}
