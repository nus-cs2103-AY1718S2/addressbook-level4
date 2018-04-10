package seedu.address.commons.util;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import javafx.collections.ObservableList;
import seedu.address.model.event.CalendarEntry;

/**
 * Provides utilities to convert between {@code Entry} used in CalendarFX and its Model Version, {@code CalendarEntry}.
 */
public class CalendarUtil {

    /**
     * Converts {@code CalendarEntry} to {@code Entry} used in CalendarFX.
     */
    public static Entry<String> convertToEntry(CalendarEntry calEntry) {
        requireNonNull(calEntry);

        Interval entryInterval = new Interval(calEntry.getStartDate().getLocalDate(),
                calEntry.getStartTime().getLocalTime(),
                calEntry.getEndDate().getLocalDate(),
                calEntry.getEndTime().getLocalTime());

        return new Entry<>(calEntry.getEntryTitle().toString(), entryInterval);
    }

    /**
     * Converts given list of calendarEntries to {@code Entry} used in CalendarFX and return list of {@code Entry}.
     */
    public static List<Entry<?>> convertEntireListToEntries(ObservableList<CalendarEntry> calendarEntries) {
        List<Entry<?>> convertedEntries = new ArrayList<>();

        for (CalendarEntry ce: calendarEntries) {
            convertedEntries.add(CalendarUtil.convertToEntry(ce));
        }

        return convertedEntries;
    }
}
