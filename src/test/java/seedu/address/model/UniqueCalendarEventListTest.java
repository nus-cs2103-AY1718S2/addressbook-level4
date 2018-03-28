package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCalendarEvents.GET_STOCKS;
import static seedu.address.testutil.TypicalCalendarEvents.MEETING_BOSS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.UniqueCalendarEventList;

public class UniqueCalendarEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() throws UniqueCalendarEventList.DuplicateCalendarEventException {
        UniqueCalendarEventList firstEventsList =  new UniqueCalendarEventList();
        firstEventsList.add(MEETING_BOSS);
        UniqueCalendarEventList secondEventsList = new UniqueCalendarEventList();
        secondEventsList.add(GET_STOCKS);

        // Same object -> True
        assertTrue(firstEventsList.equals(firstEventsList));

        // different type -> false
        assertFalse(firstEventsList.equals(1));

        // different objects, same type -> false
        assertFalse(firstEventsList.equals(secondEventsList));
    }

    @Test
    public void asOrderInsensitiveList_compareListsWithSameItemsInDiffOrder_assertEqual()
            throws UniqueCalendarEventList.DuplicateCalendarEventException {

        UniqueCalendarEventList firstEventsList =  new UniqueCalendarEventList();
        firstEventsList.add(MEETING_BOSS);
        firstEventsList.add(GET_STOCKS);
        UniqueCalendarEventList secondEventsList = new UniqueCalendarEventList();
        secondEventsList.add(GET_STOCKS);
        secondEventsList.add(MEETING_BOSS);

        assertTrue(firstEventsList.equalsOrderInsensitive(secondEventsList));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueCalendarEventList calendarEventList = new UniqueCalendarEventList();
        thrown.expect(UnsupportedOperationException.class);
        calendarEventList.asObservableList().remove(0);
    }

    @Test
    public void asUniqueList_addDuplicateCalendarEvents_throwsDuplicateCalendarEventException()
            throws UniqueCalendarEventList.DuplicateCalendarEventException {

        UniqueCalendarEventList calendarEventList = new UniqueCalendarEventList();
        thrown.expect(UniqueCalendarEventList.DuplicateCalendarEventException.class);
        calendarEventList.add(MEETING_BOSS);
        calendarEventList.add(MEETING_BOSS);
    }
}
