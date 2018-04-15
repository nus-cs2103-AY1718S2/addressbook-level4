package seedu.address.commons.util;
//@@author SuxianAlicia

import static junit.framework.TestCase.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_SUPPLIER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;

public class EntryTimeConstraintsUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkCalendarEntryTimeConstraints_validInputs_success() throws IllegalValueException {
        StartDate startDate = new StartDate(VALID_START_DATE_MEET_SUPPLIER);
        EndDate endDate = new EndDate(VALID_END_DATE_MEET_SUPPLIER);
        StartTime startTime = new StartTime(VALID_START_TIME_MEET_SUPPLIER);
        EndTime endTime = new EndTime(VALID_END_TIME_MEET_SUPPLIER);

        try {
            EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(startDate, endDate, startTime, endTime);
        } catch (IllegalValueException ive) {
            fail("This exception should not be thrown as the inputs are valid.");
        }
    }

    @Test
    public void checkCalendarEntryTimeConstraints_startDateLaterThanEndDate_throwsIllegalValueException()
            throws IllegalValueException {
        StartDate invalidStartDate = new StartDate("06-06-2100"); //Start Date is after End Date
        EndDate endDate = new EndDate(VALID_END_DATE_MEET_SUPPLIER);
        StartTime startTime = new StartTime(VALID_START_TIME_MEET_SUPPLIER);
        EndTime endTime = new EndTime(VALID_END_TIME_MEET_SUPPLIER);

        String expectedMessage = EntryTimeConstraintsUtil.START_AND_END_DATE_CONSTRAINTS;
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(expectedMessage);
        EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(invalidStartDate, endDate, startTime, endTime);
    }

    @Test
    public void checkCalendarEntryTimeConstraints_startTimeLaterThanEndTime_throwsIllegalValueException()
            throws IllegalValueException {

        StartDate startDate = new StartDate(VALID_START_DATE_MEET_SUPPLIER);
        EndDate endDate = new EndDate(VALID_END_DATE_MEET_SUPPLIER);
        StartTime invalidStartTime = new StartTime("23:59");
        EndTime endTime = new EndTime(VALID_END_TIME_MEET_SUPPLIER);

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
