package seedu.address.model.export.exceptions;

import seedu.address.commons.exceptions.GoogleCalendarException;

//@@author daviddalmaso
/**
 * Exception for handling users that do not want to allow access to their Google Calendar
 */
public class CalendarAccessDeniedException extends GoogleCalendarException {
    public CalendarAccessDeniedException() {
        super("Access denied to calendar");
    }
}
