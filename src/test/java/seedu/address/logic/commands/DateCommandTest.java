package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.appointment.TypicalCalendar.FIRST_DATE;
import static seedu.address.commons.core.appointment.TypicalCalendar.SECOND_DATE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowDateRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.DateCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author trafalgarandre
public class DateCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validDate_success() {
        assertExecutionSuccess("2018-02-27");
    }

    @Test
    public void execute_invalidDate_failure() {
        assertExecutionFailure("a", DateCommand.MESSAGE_DATE_CONSTRAINTS);
    }

    @Test
    public void equals() {
        DateCommand dateFirstCommand = new DateCommand(FIRST_DATE);
        DateCommand dateSecondCommand = new DateCommand(SECOND_DATE);
        DateCommand nullDateCommand = new DateCommand(null);

        // same object -> returns true
        assertTrue(dateFirstCommand.equals(dateFirstCommand));

        // same values -> returns true
        DateCommand dateFirstCommandCopy = new DateCommand(FIRST_DATE);
        assertTrue(dateFirstCommand.equals(dateFirstCommandCopy));

        // both null
        DateCommand nullDateCommandCopy = new DateCommand(null);
        assertTrue(nullDateCommand.equals(nullDateCommandCopy));

        // different types -> returns false
        assertFalse(dateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(dateFirstCommand.equals(null));

        // different date -> returns false
        assertFalse(dateFirstCommand.equals(dateSecondCommand));
    }

    /**
     * Executes a {@code dateCommand} with the given {@code date}, and checks that {@code handleShowDateRequestEvent}
     * is raised with the correct date.
     */
    private void assertExecutionSuccess(String date) {

        try {
            DateCommand dateCommand = prepareCommand(date);
            CommandResult commandResult = dateCommand.execute();
            assertEquals(String.format(DateCommand.MESSAGE_SUCCESS, date),
                    commandResult.feedbackToUser);

            ShowDateRequestEvent lastEvent = (ShowDateRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(ParserUtil.parseDate(date), lastEvent.targetDate);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes a {@code DateCommand} with the given {@code date}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String date, String expectedMessage) {

        try {
            DateCommand dateCommand = prepareCommand(date);
            dateCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (IllegalValueException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code DateCommand} with parameters {@code date}.
     */
    private DateCommand prepareCommand(String date) throws IllegalValueException {
        DateCommand dateCommand = new DateCommand(ParserUtil.parseDate(date));
        return dateCommand;
    }
}
