package seedu.address.testutil;
//@@author SuxianAlicia
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.model.event.CalendarEntry;

/**
 * A utility class for {@code CalendarEntry}.
 */
public class CalendarEntryUtil {

    /**
     * Returns an add entry command string for adding the {@code calendarEntry}.
     */
    public static String getAddEntryCommand(CalendarEntry calendarEntry) {
        return AddEntryCommand.COMMAND_WORD + " " + getCalendarEntryDetails(calendarEntry);
    }

    /**
     * returns part of command string for the given {@code calEvent}'s details.
     */
    public static String getCalendarEntryDetails(CalendarEntry calEntry) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ENTRY_TITLE + calEntry.getEntryTitle().toString() + " ");
        sb.append(PREFIX_START_DATE + calEntry.getStartDate().toString() + " ");
        sb.append(PREFIX_END_DATE + calEntry.getEndDate().toString() + " ");
        sb.append(PREFIX_START_TIME + calEntry.getStartTime().toString() + " ");
        sb.append(PREFIX_END_TIME + calEntry.getEndTime().toString());
        return sb.toString();
    }
}
