package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.appointment.TypicalCalendar.FIRST_WEEK;
import static seedu.address.commons.core.appointment.TypicalCalendar.FIRST_YEAR;
import static seedu.address.commons.core.appointment.TypicalCalendar.SECOND_WEEK;
import static seedu.address.commons.core.appointment.TypicalCalendar.SECOND_YEAR;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowWeekRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.WeekCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author trafalgarandre
public class WeekCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validWeek_success() {
        assertExecutionSuccess("2018 18");
    }

    @Test
    public void execute_invalidDate_failure() {
        assertExecutionFailure("2018", WeekCommand.MESSAGE_WEEK_CONSTRAINTS);
    }

    @Test
    public void equals() {
        WeekCommand weekFirstCommand = new WeekCommand(FIRST_YEAR, FIRST_WEEK);
        WeekCommand weekSecondCommand = new WeekCommand(SECOND_YEAR, SECOND_WEEK);
        WeekCommand nullWeekCommand = new WeekCommand(null, 0);

        // same object -> returns true
        assertTrue(weekFirstCommand.equals(weekFirstCommand));

        // same values -> returns true
        WeekCommand WeekFirstCommandCopy = new WeekCommand(FIRST_YEAR, FIRST_WEEK);
        assertTrue(weekFirstCommand.equals(WeekFirstCommandCopy));

        // both null
        WeekCommand nullWeekCommandCopy = new WeekCommand(null, 0);
        assertTrue(nullWeekCommand.equals(nullWeekCommandCopy));

        // different types -> returns false
        assertFalse(weekFirstCommand.equals(1));

        // null -> returns false
        assertFalse(weekFirstCommand.equals(null));

        // different week -> returns false
        assertFalse(weekFirstCommand.equals(weekSecondCommand));
    }

    /**
     * Executes a {@code weekCommand} with the given {@code week}, and checks that {@code handleShowWeekRequestEvent}
     * is raised with the correct date.
     */
    private void assertExecutionSuccess(String str) {

        try {
            WeekCommand weekCommand = prepareCommand(str);
            CommandResult commandResult = weekCommand.execute();
            assertEquals(String.format(WeekCommand.MESSAGE_SUCCESS, str.substring(5) + " " + str.substring(0, 4)),
                    commandResult.feedbackToUser);

            ShowWeekRequestEvent lastEvent = (ShowWeekRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(ParserUtil.parseWeek(str), lastEvent.targetWeek);
            assertEquals(ParserUtil.parseYearOfWeek(str), lastEvent.targetYear);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes a {@code WeekCommand} with the given {@code week}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String str, String expectedMessage) {

        try {
            WeekCommand weekCommand = prepareCommand(str);
            weekCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (IllegalValueException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code WeekCommand} with parameters {@code week}.
     */
    private WeekCommand prepareCommand(String str) throws IllegalValueException {
        WeekCommand weekCommand = new WeekCommand(ParserUtil.parseYearOfWeek(str), ParserUtil.parseWeek(str));
        return weekCommand;
    }
}
