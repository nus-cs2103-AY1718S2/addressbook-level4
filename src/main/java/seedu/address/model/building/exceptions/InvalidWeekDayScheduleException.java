package seedu.address.model.building.exceptions;

//@@author Caijun7
/**
 * Signals that weekday schedule is in incorrect format
 */
public class InvalidWeekDayScheduleException extends Exception {
    public InvalidWeekDayScheduleException() {
        super("Weekday Schedule is in incorrect format, venueinformation.json file is corrupted.");
    }
}
