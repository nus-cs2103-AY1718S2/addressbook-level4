package seedu.address.logic.commands;
//@@author SuxianAlicia

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalLocalDates.LEAP_YEAR_DATE;
import static seedu.address.testutil.TypicalLocalDates.LEAP_YEAR_DATE_STRING;
import static seedu.address.testutil.TypicalLocalDates.NORMAL_DATE;
import static seedu.address.testutil.TypicalLocalDates.NORMAL_DATE_STRING;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class CalendarJumpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CalendarJumpCommand(null, LEAP_YEAR_DATE_STRING);
    }

    @Test
    public void constructor_nullDateString_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CalendarJumpCommand(LEAP_YEAR_DATE, null);
    }

    @Test
    public void execute_validDateAndDateStringGiven_success() {
        assertSwitchDateSuccess(LEAP_YEAR_DATE, LEAP_YEAR_DATE_STRING);
    }

    @Test
    public void equals() {
        CalendarJumpCommand calendarJumpFirstCommand = new CalendarJumpCommand(LEAP_YEAR_DATE, LEAP_YEAR_DATE_STRING);
        CalendarJumpCommand calendarJumpSecondCommand = new CalendarJumpCommand(NORMAL_DATE, LEAP_YEAR_DATE_STRING);
        CalendarJumpCommand calendarJumpThirdCommand = new CalendarJumpCommand(LEAP_YEAR_DATE, NORMAL_DATE_STRING);

        // same object -> returns true
        assertTrue(calendarJumpFirstCommand.equals(calendarJumpFirstCommand));

        // same values -> returns true
        CalendarJumpCommand calendarJumpFirstCommandCopy =
                new CalendarJumpCommand(LEAP_YEAR_DATE, LEAP_YEAR_DATE_STRING);
        assertTrue(calendarJumpFirstCommand.equals(calendarJumpFirstCommandCopy));

        // different date -> returns false
        assertFalse(calendarJumpFirstCommand.equals(calendarJumpSecondCommand));

        // different dateString -> returns false
        assertFalse(calendarJumpFirstCommand.equals(calendarJumpThirdCommand));

        // different types -> returns false
        assertFalse(calendarJumpFirstCommand.equals(1));

        // null -> returns false
        assertFalse(calendarJumpFirstCommand.equals(null));
    }

    /**
     * Executes a {@code CalendarJumpCommand},
     * and checks that {@code ChangeCalendarDateRequestEvent} is raised with the given {@code LocalDate}.
     */
    private void assertSwitchDateSuccess(LocalDate date, String dateString) {
        CalendarJumpCommand calendarJumpCommand = new CalendarJumpCommand(date, dateString);

        try {
            CommandResult commandResult = calendarJumpCommand.execute();
            assertEquals(String.format(CalendarJumpCommand.MESSAGE_CALENDAR_JUMP_SUCCESS, dateString),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ChangeCalendarDateRequestEvent lastEvent = (ChangeCalendarDateRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(date, lastEvent.getDate());
    }
}
