package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.model.event.CalendarEntry;

/**
 * A utility class for {@code CalendarEntry}.
 */
public class CalendarEntryUtil {

    /**
     * returns part of command string for the given {@code calEvent}'s details.
     */
    public static String getCalendarEventDetails(CalendarEntry calEvent) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ENTRY_TITLE + calEvent.getEntryTitle().toString() + " ");
        sb.append(PREFIX_START_DATE + calEvent.getStartDate().toString() + " ");
        sb.append(PREFIX_END_DATE + calEvent.getEndDate().toString() + " ");
        sb.append(PREFIX_START_TIME + calEvent.getStartTime().toString() + " ");
        sb.append(PREFIX_END_TIME + calEvent.getEndTime().toString());
        return sb.toString();
    }
}
