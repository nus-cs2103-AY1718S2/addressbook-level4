package seedu.address.commons.util;
//@@author SuxianAlicia
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

/**
 * Helper functions for checking StartDate, EndDate, StartTime, EndTime of {@code CalendarEntry}.
 */
public class EntryTimeConstraintsUtil {

    public static final String ENTRY_DURATION_CONSTRAINTS =
            "Entry must last at least 15 minutes if ending in same day."; //Constraint of CalendarFX entries
    public static final String START_AND_END_DATE_CONSTRAINTS = "Start Date cannot be later than End Date.";
    public static final String START_AND_END_TIME_CONSTRAINTS =
            "Start Time cannot be later than End Time if Entry ends on same date.";

    private static final long MINIMAL_DURATION = 15; //Constraint of CalendarFX entries

    /**
     * Returns true if duration between start time and end time is less than 15 minutes.
     * This is a constraint that CalendarFX has. Event duration must last at least 15 minutes.
     */
    private static boolean eventIsShorterThanFifteenMinutes(StartDate startDate, EndDate endDate,
                                                            StartTime startTime, EndTime endTime) {
        requireAllNonNull(startDate, endDate, startTime, endTime);

        LocalDateTime startDateAndTime = LocalDateTime.of(startDate.getLocalDate(), startTime.getLocalTime());
        LocalDateTime endDateAndTime = LocalDateTime.of(endDate.getLocalDate(), endTime.getLocalTime());
        if (Duration.between(startDateAndTime, endDateAndTime).toMinutes() < MINIMAL_DURATION) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if given start time is later than end time.
     * Start time cannot be later than End time if event ends on the same date.
     */
    private static boolean startTimeIsLaterThanEndTime(StartTime startTime, EndTime endTime) {
        requireAllNonNull(startTime, endTime);
        return startTime.getLocalTime().isAfter(endTime.getLocalTime());
    }

    /**
     * Returns true if given start date is later than end date.
     * Start Date cannot be later than End Date as it violates the meaning of the terms.
     */
    private static boolean startDateIsLaterThanEndDate(StartDate startDate, EndDate endDate) {
        requireAllNonNull(startDate, endDate);
        return startDate.getLocalDate().isAfter(endDate.getLocalDate());
    }

    /**
     * Checks 3 constraints:
     * 1. {@code StartDate} must not be after {@code EndDate}.
     * 2. {@code Start Time} must not be after {@code EndTime} if Calendar Entry ends on same Date.
     * 3. Duration of entry cannot be less than 15 minutes.
     */
    public static void checkCalendarEntryTimeConstraints(
           StartDate startDate, EndDate endDate, StartTime startTime, EndTime endTime) throws IllegalValueException {

        if (startDateIsLaterThanEndDate(startDate, endDate)) {
            throw new IllegalValueException(START_AND_END_DATE_CONSTRAINTS);
        }

        if (startDate.toString().equals(endDate.toString()) && startTimeIsLaterThanEndTime(startTime, endTime)) {
            throw new IllegalValueException(START_AND_END_TIME_CONSTRAINTS);
        }

        if (eventIsShorterThanFifteenMinutes(startDate, endDate, startTime, endTime)) {
            throw new IllegalValueException(ENTRY_DURATION_CONSTRAINTS);

        }

    }

}
