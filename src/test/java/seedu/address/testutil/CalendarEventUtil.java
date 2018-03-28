package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.model.event.CalendarEvent;

/**
 * A utility class for {@code CalendarEvent}.
 */
public class CalendarEventUtil {

    /**
     * returns part of command string for the given {@code calEvent}'s details.
     */
    public static String getCalendarEventDetails(CalendarEvent calEvent) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EVENT_TITLE + calEvent.getEventTitle().toString() + " ");
        sb.append(PREFIX_START_DATE + calEvent.getStartDate().toString() + " ");
        sb.append(PREFIX_END_DATE + calEvent.getEndDate().toString() + " ");
        sb.append(PREFIX_START_TIME + calEvent.getStartTime().toString() + " ");
        sb.append(PREFIX_END_TIME + calEvent.getEndTime().toString());
        return sb.toString();
    }
}
