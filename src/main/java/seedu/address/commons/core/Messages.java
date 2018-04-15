package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_UNKNOWN_CALENDAR_VIEW = "Unknown calendarview";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX = "The appointment index provided is"
            + " invalid";
    public static final String MESSAGE_NOT_CELEBRITY_INDEX = "At least one of the celebrity indices provided does not "
            + "correspond to a celebrity.";
    public static final String MESSAGE_NOT_POINT_OF_CONTACT_INDEX = "At least one of the point of contact indices "
            + "provided corresponds to a celebrity.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_MUST_SHOW_LIST_OF_APPOINTMENTS = "List of appointments must be shown "
            + "before editing or deleting an appointment";
    public static final String MESSAGE_START_DATE_TIME_NOT_BEFORE_END_DATE_TIME = "Start date/time is not "
            + "chronologically before end date/time.";
}
