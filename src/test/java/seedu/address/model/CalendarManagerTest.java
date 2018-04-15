package seedu.address.model;
//@@author SuxianAlicia
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCalendarEntries.MEETING_BOSS;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entry.CalendarEntry;

public class CalendarManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CalendarManager calendarManager = new CalendarManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), calendarManager.getCalendarEntryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        calendarManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyCalendarManager_replacesData() {
        CalendarManager newData = getTypicalCalendarManagerWithEntries();
        calendarManager.resetData(newData);
        assertEquals(newData, calendarManager);
    }

    @Test
    public void resetData_withDuplicateCalendarEntries_throwsAssertionError() {
        // Repeat MEETING_BOSS twice
        List<CalendarEntry> newEntries = Arrays.asList(MEETING_BOSS, MEETING_BOSS);
        CalendarManagerStub newData = new CalendarManagerStub(newEntries);

        thrown.expect(AssertionError.class);
        calendarManager.resetData(newData);
    }


    @Test
    public void getCalendarEntryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        calendarManager.getCalendarEntryList().remove(0);
    }


    private static class CalendarManagerStub implements ReadOnlyCalendarManager {
        private final ObservableList<CalendarEntry> calendarEntries = FXCollections.observableArrayList();

        CalendarManagerStub(Collection<CalendarEntry> calendarEntries) {
            this.calendarEntries.setAll(calendarEntries);
        }

        @Override
        public ObservableList<CalendarEntry> getCalendarEntryList() {
            return calendarEntries;
        }
    }
}
