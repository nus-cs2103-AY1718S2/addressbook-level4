//@@author LeonidAgarth
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_NDP;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;

public class WeeklyEventTest {

    private WeeklyEvent event1 = new WeeklyEvent("CS2101", "COM1", "1500", "1600", "WEDNESDAY");
    private WeeklyEvent event2 = new WeeklyEvent(new Module("CS2103", "Software Engineer"), new Schedule());
    private WeeklyEvent event3 = new WeeklyEvent(new Module("CS2103", "Software Engineer"), new Schedule());
    private WeeklyEvent event4 = new WeeklyEvent("CS2103T", "I3", "1500", "1700", "WEDNESDAY");
    private WeeklyEvent event5 = new WeeklyEvent("CS2102", "COM2", "2000", "2100", "WEDNESDAY");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WeeklyEvent(null, null, null, null, (String[]) null));
        assertThrows(NullPointerException.class, () -> new WeeklyEvent(null, null));
    }

    @Test
    public void isValidWeeklyEventName_null_throwsNullPointerException() {
        // null event name
        assertThrows(NullPointerException.class, () -> WeeklyEvent.isValidName(null));
    }

    @Test
    public void isValidName() {
        assertTrue(WeeklyEvent.isValidName(VALID_EVENT_NAME_F1));
        assertTrue(WeeklyEvent.isValidName(VALID_EVENT_NAME_NDP));
        assertFalse(WeeklyEvent.isValidName(INVALID_EVENT_NAME_DESC));
    }

    @Test
    public void isValidDate() {
        assertTrue(WeeklyEvent.isValidDate(VALID_EVENT_DATE_F1));
        assertTrue(WeeklyEvent.isValidDate(VALID_EVENT_DATE_NDP));
        assertFalse(WeeklyEvent.isValidDate(INVALID_EVENT_DATE_DESC));
    }

    @Test
    public void isValidTime() {
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_START_TIME_F1));
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_START_TIME_NDP));
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_END_TIME_F1));
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_END_TIME_NDP));
        assertFalse(WeeklyEvent.isValidTime(INVALID_EVENT_START_TIME_DESC));
        assertFalse(WeeklyEvent.isValidTime(INVALID_EVENT_END_TIME_DESC));
    }

    @Test
    public void equals_test() {
        assertTrue(event1.equals(event1));
        assertTrue(event2.equals(event3));
        assertFalse(event1.equals(1));
        assertFalse(event1.equals(event2));
    }

    @Test
    public void clash() {
        assertTrue(event1.clash(event1));
        assertTrue(event1.clash(event4));
        assertTrue(event2.clash(event3));
        assertFalse(event1.clash(event2));
        assertFalse(event1.clash(event5));
        assertFalse(event3.clash(event5));
    }

    @Test
    public void toString_test() {
        assertTrue(event1.toString().equals(event1.toString()));
        assertFalse(event1.toString().equals(event2.toString()));
    }
}
