package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.ChangeDayViewRequestEvent;
import seedu.address.commons.events.ui.ChangeMonthViewRequestEvent;
import seedu.address.commons.events.ui.ChangeWeekViewRequestEvent;
import seedu.address.commons.events.ui.ChangeYearViewRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author wynonaK
public class ListAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_getYear_success() throws CommandException {
        CommandResult result = new ListAppointmentCommand(1, Year.now()).execute();
        assertEquals(String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "year"), result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeYearViewRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_getMonth_success() throws CommandException {
        CommandResult result = new ListAppointmentCommand(2, YearMonth.now()).execute();
        assertEquals(String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "month"), result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeMonthViewRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_getWeek_success() throws CommandException {
        CommandResult result = new ListAppointmentCommand(3, LocalDate.now()).execute();
        assertEquals(String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "week"), result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeWeekViewRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_getDate_success() throws CommandException {
        CommandResult result = new ListAppointmentCommand(4, LocalDate.now()).execute();
        assertEquals(String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "day"), result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeDayViewRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_getPastYearNoAppt_failure() throws CommandException {
        Year year = Year.of(2017);
        thrown.expect(NullPointerException.class);
        new ListAppointmentCommand(1, year).execute();
    }

    @Test
    public void execute_getPastMonthYearNoAppt_failure() throws CommandException {
        YearMonth yearMonth = YearMonth.of(2017, 01);
        thrown.expect(NullPointerException.class);
        new ListAppointmentCommand(2, yearMonth).execute();
    }

    @Test
    public void execute_getPastWeekNoAppt_failure() throws CommandException {
        LocalDate date = LocalDate.of(2017, 01, 01);
        thrown.expect(NullPointerException.class);
        new ListAppointmentCommand(3, date).execute();
    }

    @Test
    public void execute_getPastDayNoAppt_failure() throws CommandException {
        LocalDate date = LocalDate.of(2017, 01, 01);
        thrown.expect(NullPointerException.class);
        new ListAppointmentCommand(4, date).execute();
    }
}
