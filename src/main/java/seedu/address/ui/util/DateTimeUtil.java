package seedu.address.ui.util;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;

import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Task;

//@@author Kyomian
/**
 * Formats DateTime for display in UI
 * Example: 01/08/2018 08:00 is displayed as 1 Aug 2018 08:00 in the UI
 */
public class DateTimeUtil {

    private static final String DISPLAYED_DATETIME_FORMAT = "d MMM y HH:mm";
    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern(DISPLAYED_DATETIME_FORMAT);

    /**
     * Formats DateTime of task as day, name of month, year, hours and minutes
     */
    public static String getDisplayedDateTime(Task task) throws DateTimeException {
        DateTime dateTime = task.getDateTime();
        String displayedDateTime = displayFormatter.format(dateTime.getLocalDateTime());
        return displayedDateTime;
    }

    /**
     * Formats StartDateTime of event as day, name of month, year, hours and minutes
     */
    public static String getDisplayedStartDateTime(Event event) throws DateTimeException {
        DateTime dateTime = event.getStartDateTime();
        String displayedDateTime = displayFormatter.format(dateTime.getLocalDateTime());
        return displayedDateTime;
    }

    /**
     * Formats EndDateTime of event as day, name of month, year, hours and minutes
     */
    public static String getDisplayedEndDateTime(Event event) throws DateTimeException {
        DateTime dateTime = event.getEndDateTime();
        String displayedDateTime = displayFormatter.format(dateTime.getLocalDateTime());
        return displayedDateTime;
    }
}
