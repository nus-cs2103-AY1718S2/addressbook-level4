package seedu.address.model;
//@@author SuxianAlicia
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCalendarEntries.GET_STOCKS;
import static seedu.address.testutil.TypicalCalendarEntries.MEETING_BOSS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.entry.UniqueCalendarEntryList;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

public class UniqueCalendarEntryListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() throws DuplicateCalendarEntryException {
        UniqueCalendarEntryList firstEntriesList =  new UniqueCalendarEntryList();
        firstEntriesList.add(MEETING_BOSS);
        UniqueCalendarEntryList secondEntriesList = new UniqueCalendarEntryList();
        secondEntriesList.add(GET_STOCKS);

        // Same object -> True
        assertTrue(firstEntriesList.equals(firstEntriesList));

        // different type -> false
        assertFalse(firstEntriesList.equals(1));

        // different calendar entries, same type -> false
        assertFalse(firstEntriesList.equals(secondEntriesList));
    }

    @Test
    public void asOrderInsensitiveList_compareListsWithSameItemsInDiffOrder_assertEqual()
            throws DuplicateCalendarEntryException {

        UniqueCalendarEntryList firstEntriesList =  new UniqueCalendarEntryList();
        firstEntriesList.add(MEETING_BOSS);
        firstEntriesList.add(GET_STOCKS);
        UniqueCalendarEntryList secondEntries = new UniqueCalendarEntryList();
        secondEntries.add(GET_STOCKS);
        secondEntries.add(MEETING_BOSS);

        assertTrue(firstEntriesList.equalsOrderInsensitive(secondEntries));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueCalendarEntryList calendarEntriesList = new UniqueCalendarEntryList();
        thrown.expect(UnsupportedOperationException.class);
        calendarEntriesList.asObservableList().remove(0);
    }

    @Test
    public void asUniqueList_addDuplicateCalendarEntries_throwsDuplicateCalendarEntryException()
            throws DuplicateCalendarEntryException {

        UniqueCalendarEntryList calendarEntriesList = new UniqueCalendarEntryList();
        thrown.expect(DuplicateCalendarEntryException.class);
        calendarEntriesList.add(MEETING_BOSS);
        calendarEntriesList.add(MEETING_BOSS);
    }
}
