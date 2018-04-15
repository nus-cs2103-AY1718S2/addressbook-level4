package seedu.address.model.building.exceptions;

//@@author Caijun7
/**
 * Signals that week schedule is in incorrect format
 */
public class InvalidWeekScheduleException extends Exception {
    public InvalidWeekScheduleException() {
        super("Week Schedule is in incorrect format, venueinformation.json file is corrupted.");
    }
}
