package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.appointment.TypicalCalendar.FIRST_YEAR;
import static seedu.address.commons.core.appointment.TypicalCalendar.SECOND_YEAR;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowYearRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.YearCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author trafalgarandre
public class YearCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validYear_success() {
        assertExecutionSuccess("2018");
    }

    @Test
    public void execute_invalidYear_failure() {
        assertExecutionFailure("a", YearCommand.MESSAGE_YEAR_CONSTRAINTS);
    }

    @Test
    public void equals() {
        YearCommand yearFirstCommand = new YearCommand(FIRST_YEAR);
        YearCommand yearSecondCommand = new YearCommand(SECOND_YEAR);
        YearCommand nullYearCommand = new YearCommand(null);

        // same object -> returns true
        assertTrue(yearFirstCommand.equals(yearFirstCommand));

        // same values -> returns true
        YearCommand YearFirstCommandCopy = new YearCommand(FIRST_YEAR);
        assertTrue(yearFirstCommand.equals(YearFirstCommandCopy));

        // both null
        YearCommand nullYearCommandCopy = new YearCommand(null);
        assertTrue(nullYearCommand.equals(nullYearCommandCopy));

        // different types -> returns false
        assertFalse(yearFirstCommand.equals(1));

        // null -> returns false
        assertFalse(yearFirstCommand.equals(null));

        // different date -> returns false
        assertFalse(yearFirstCommand.equals(yearSecondCommand));
    }

    /**
     * Executes a {@code yearCommand} with the given {@code year}, and checks that {@code handleShowYearRequestEvent}
     * is raised with the correct month.
     */
    private void assertExecutionSuccess(String year) {

        try {
            YearCommand yearCommand = prepareCommand(year);
            CommandResult commandResult = yearCommand.execute();
            assertEquals(String.format(YearCommand.MESSAGE_SUCCESS, year),
                    commandResult.feedbackToUser);

            ShowYearRequestEvent lastEvent = (ShowYearRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(ParserUtil.parseYear(year), lastEvent.targetYear);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes a {@code YearCommand} with the given {@code year}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String year, String expectedMessage) {

        try {
            YearCommand yearCommand = prepareCommand(year);
            yearCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (IllegalValueException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code YearCommand} with parameters {@code year}.
     */
    private YearCommand prepareCommand(String year) throws IllegalValueException {
        YearCommand yearCommand = new YearCommand(ParserUtil.parseYear(year));
        return yearCommand;
    }
}
