//@@author SuxianAlicia
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_SUPPLIER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.CalendarManager;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

/**
 * A utility class containing a list of {@code CalendarEntry} objects to be used in tests.
 */
public class TypicalCalendarEntries {

    public static final CalendarEntry MEETING_BOSS = new CalendarEntryBuilder()
            .withEntryTitle("Meeting with boss")
            .withStartDate("06-06-2018")
            .withEndDate("06-06-2018")
            .withStartTime("10:00")
            .withEndTime("12:00").build();

    public static final CalendarEntry GET_STOCKS = new CalendarEntryBuilder()
            .withEntryTitle("Get stocks from supplier")
            .withStartDate("01-07-2018")
            .withEndDate("01-07-2018")
            .withStartTime("08:00")
            .withEndTime("12:30").build();

    public static final CalendarEntry ROAD_SHOW = new CalendarEntryBuilder()
            .withEntryTitle("Road Show at Orchard")
            .withStartDate("02-05-2018")
            .withEndDate("06-05-2018")
            .withStartTime("09:00")
            .withEndTime("19:00").build();

    public static final CalendarEntry WORKSHOP = new CalendarEntryBuilder()
            .withEntryTitle("Workshop")
            .withStartDate("28-05-2018")
            .withEndDate("29-05-2018")
            .withStartTime("10:00")
            .withEndTime("15:00").build();

    // Manually added - Calendar Entry's details found in {@code CommandTestUtil}
    public static final CalendarEntry MEET_SUPPLIER = new CalendarEntryBuilder()
            .withEntryTitle(VALID_ENTRY_TITLE_MEET_SUPPLIER)
            .withStartDate(VALID_START_DATE_MEET_SUPPLIER)
            .withEndDate(VALID_END_DATE_MEET_SUPPLIER)
            .withStartTime(VALID_START_TIME_MEET_SUPPLIER)
            .withEndTime(VALID_END_TIME_MEET_SUPPLIER).build();

    public static final CalendarEntry GET_BOOKS = new CalendarEntryBuilder()
            .withEntryTitle(VALID_ENTRY_TITLE_GET_BOOKS)
            .withStartDate(VALID_START_DATE_GET_BOOKS)
            .withEndDate(VALID_END_DATE_GET_BOOKS)
            .withStartTime(VALID_START_TIME_GET_BOOKS)
            .withEndTime(VALID_END_TIME_GET_BOOKS).build();

    private TypicalCalendarEntries() {} // prevents instantiation

    public static CalendarManager getTypicalCalendarManagerWithEntries() {
        CalendarManager cm = new CalendarManager();

        for (CalendarEntry calEvent : getTypicalCalendarEntries()) {
            try {
                cm.addCalendarEntry(calEvent);
            } catch (DuplicateCalendarEntryException dcee) {
                throw new AssertionError("not possible");
            }
        }
        return cm;
    }

    public static List<CalendarEntry> getTypicalCalendarEntries() {
        return new ArrayList<>(Arrays.asList(MEETING_BOSS, GET_STOCKS, ROAD_SHOW, WORKSHOP));
    }
}
