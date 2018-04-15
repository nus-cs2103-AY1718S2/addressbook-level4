package seedu.address.model.export.exceptions;

import seedu.address.commons.exceptions.GoogleCalendarException;

//@@author daviddalmaso
/**
 * Exception to handle users trying to export calendar without internet connectivity
 */
public class ConnectivityIssueException extends GoogleCalendarException {
    public ConnectivityIssueException() {
        super("Unable to connect to the internet");
    }
}
