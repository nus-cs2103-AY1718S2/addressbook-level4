package seedu.address.model;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.calendarfx.model.Calendar;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CalendarEntryConversionUtil;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.UniqueCalendarEntryList;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

/**
 * Manages {@code Calendar} as defined in CalendarFX and {@code UniqueCalendarEntryList},
 * which contains {@code CalendarEntry} to be added to {@code Calendar}.
 */
public class CalendarManager implements ReadOnlyCalendarManager {
    private final Calendar calendar;
    private final UniqueCalendarEntryList calendarEntryList;

    public CalendarManager() {
        calendarEntryList = new UniqueCalendarEntryList();
        calendar = new Calendar();
        calendar.setReadOnly(true);
        calendar.setStyle(Calendar.Style.STYLE1);
    }

    public CalendarManager(ReadOnlyCalendarManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code CalendarManager} with {@code newData}.
     * Updates the Calendar with calendar entries in {@code calEntries}.
     */
    public void resetData(ReadOnlyCalendarManager newData) {
        requireNonNull(newData);

        List<CalendarEntry> calEntries = new ArrayList<>(newData.getCalendarEntryList());

        try {
            setCalendarEntries(calEntries);
        } catch (DuplicateCalendarEntryException dcee) {
            throw new AssertionError("Calendar Manager should not have duplicate calendar entries.");
        }
        updateCalendar();
    }

    /**
     * Updates Calendar with entries converted from {@code calendarEntryList}.
     */
    private void updateCalendar() {
        calendar.clear();
        calendar.addEntries(
                CalendarEntryConversionUtil.convertEntireListToEntries(calendarEntryList.asObservableList()));
    }

    /**
     * Sets {@code calendarEntryList} to match the given list of calendar entries.
     */
    private void setCalendarEntries(List<CalendarEntry> calEntries)
            throws DuplicateCalendarEntryException {
        calendarEntryList.setCalEntryList(calEntries);
    }

    @Override
    public ObservableList<CalendarEntry> getCalendarEntryList() {
        return calendarEntryList.asObservableList();
    }

    public Calendar getCalendar() {
        return calendar;
    }

    // Managing CalendarEntries operations

    /**
     * Adds a calendar entries to list of calendar entries in calendar manager.
     * @throws DuplicateCalendarEntryException
     * if there exist an equivalent calendar entry in calendar manager.
     */
    public void addCalendarEntry(CalendarEntry toAdd) throws DuplicateCalendarEntryException {
        calendarEntryList.add(toAdd);
        updateCalendar();
    }

    /**
     * Removes an existing calendar entry in list of calendar entries and from the calendar itself.
     * @throws CalendarEntryNotFoundException
     * if given calendar entry does not exist in list of calendar entry
     */
    public void deleteCalendarEntry(CalendarEntry entryToDelete) throws CalendarEntryNotFoundException {
        if (!calendarEntryList.remove(entryToDelete)) {
            throw new CalendarEntryNotFoundException();
        } else {
            updateCalendar();
        }
    }

    /**
     * Replaces the given calendar entry {@code target} in the list with {@code editedEntry}.
     * Updates the Calendar to show the new result.
     */
    public void updateCalendarEntry(CalendarEntry entryToEdit, CalendarEntry editedEntry)
            throws DuplicateCalendarEntryException, CalendarEntryNotFoundException {
        requireNonNull(editedEntry);
        calendarEntryList.setCalendarEntry(entryToEdit, editedEntry);
        updateCalendar();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarManager // instanceof handles nulls
                && this.calendarEntryList.equals(((CalendarManager) other).calendarEntryList));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(calendar, calendarEntryList);
    }
}
