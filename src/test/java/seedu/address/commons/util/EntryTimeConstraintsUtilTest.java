package seedu.address.commons.util;
//@@author SuxianAlicia

import static junit.framework.TestCase.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_BOSS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

public class EntryTimeConstraintsUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkCalendarEntryTimeConstraints_validInputs_success() throws IllegalValueException {
        StartDate startDate = new StartDate(VALID_START_DATE_MEET_BOSS);
        EndDate endDate = new EndDate(VALID_END_DATE_MEET_BOSS);
        StartTime startTime = new StartTime(VALID_START_TIME_MEET_BOSS);
        EndTime endTime = new EndTime(VALID_END_TIME_MEET_BOSS);

        try {
            EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(startDate, endDate, startTime, endTime);
        } catch (IllegalValueException ive) {
            fail("This test should not throw any exceptions.");
        }
    }

    @Test
    public void checkCalendarEntryTimeConstraints_startDateLaterThanEndDate_throwsIllegalValueException()
            throws IllegalValueException {
        StartDate invalidStartDate = new StartDate("06-06-2100"); //Start Date is after End Date
        EndDate endDate = new EndDate(VALID_END_DATE_MEET_BOSS);
        StartTime startTime = new StartTime(VALID_START_TIME_MEET_BOSS);
        EndTime endTime = new EndTime(VALID_END_TIME_MEET_BOSS);

        String expectedMessage = EntryTimeConstraintsUtil.START_AND_END_DATE_CONSTRAINTS;
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(expectedMessage);
        EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(invalidStartDate, endDate, startTime, endTime);
    }

    @Test
    public void checkCalendarEntryTimeConstraints_startTimeLaterThanEndTime_throwsIllegalValueException()
            throws IllegalValueException {

        StartDate startDate = new StartDate(VALID_START_DATE_MEET_BOSS);
        EndDate endDate = new EndDate(VALID_END_DATE_MEET_BOSS);
        StartTime invalidStartTime = new StartTime("23:59");
        EndTime endTime = new EndTime(VALID_END_TIME_MEET_BOSS);

        String expectedMessage = EntryTimeConstraintsUtil.START_AND_END_TIME_CONSTRAINTS;
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(expectedMessage);
        EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(startDate, endDate, invalidStartTime, endTime);
    }

    @Test
    public void checkCalendarEntryTimeConstraints_durationShorterThanFifteenMinutes_throwsIllegalValueException()
            throws IllegalValueException {

        StartDate startDate = new StartDate("05-06-2018");
        EndDate endDate = new EndDate("06-06-2018");
        StartTime startTime = new StartTime("23:50");
        EndTime endTime = new EndTime("00:00");

        String expectedMessage = EntryTimeConstraintsUtil.ENTRY_DURATION_CONSTRAINTS;
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(expectedMessage);
        EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(startDate, endDate, startTime, endTime);
    }
}
