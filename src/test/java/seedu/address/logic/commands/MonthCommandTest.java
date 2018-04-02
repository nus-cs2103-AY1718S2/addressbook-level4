package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.appointment.TypicalCalendar.FIRST_YEAR_MONTH;
import static seedu.address.commons.core.appointment.TypicalCalendar.SECOND_YEAR_MONTH;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowMonthRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.MonthCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author trafalgarandre
public class MonthCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validMonth_success() {
        assertExecutionSuccess("2018-02");
    }

    @Test
    public void execute_invalidMonth_failure() {
        assertExecutionFailure("a", MonthCommand.MESSAGE_YEAR_MONTH_CONSTRAINTS);
    }

    @Test
    public void equals() {
        MonthCommand monthFirstCommand = new MonthCommand(FIRST_YEAR_MONTH);
        MonthCommand monthSecondCommand = new MonthCommand(SECOND_YEAR_MONTH);
        MonthCommand nullMonthCommand = new MonthCommand(null);

        // same object -> returns true
        assertTrue(monthFirstCommand.equals(monthFirstCommand));

        // same values -> returns true
        MonthCommand MonthFirstCommandCopy = new MonthCommand(FIRST_YEAR_MONTH);
        assertTrue(monthFirstCommand.equals(MonthFirstCommandCopy));

        // both null
        MonthCommand nullMonthCommandCopy = new MonthCommand(null);
        assertTrue(nullMonthCommand.equals(nullMonthCommandCopy));

        // different types -> returns false
        assertFalse(monthFirstCommand.equals(1));

        // null -> returns false
        assertFalse(monthFirstCommand.equals(null));

        // different date -> returns false
        assertFalse(monthFirstCommand.equals(monthSecondCommand));
    }

    /**
     * Executes a {@code monthCommand} with the given {@code month}, and checks that {@code handleShowMonthRequestEvent}
     * is raised with the correct month.
     */
    private void assertExecutionSuccess(String yearMonth) {

        try {
            MonthCommand monthCommand = prepareCommand(yearMonth);
            CommandResult commandResult = monthCommand.execute();
            assertEquals(String.format(MonthCommand.MESSAGE_SUCCESS, yearMonth),
                    commandResult.feedbackToUser);

            ShowMonthRequestEvent lastEvent = (ShowMonthRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(ParserUtil.parseYearMonth(yearMonth), lastEvent.targetYearMonth);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes a {@code MonthCommand} with the given {@code yearMonth}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String yearMonth, String expectedMessage) {

        try {
            MonthCommand monthCommand = prepareCommand(yearMonth);
            monthCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (IllegalValueException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code MonthCommand} with parameters {@code yearMonth}.
     */
    private MonthCommand prepareCommand(String yearMonth) throws IllegalValueException {
        MonthCommand monthCommand = new MonthCommand(ParserUtil.parseYearMonth(yearMonth));
        return monthCommand;
    }
}
