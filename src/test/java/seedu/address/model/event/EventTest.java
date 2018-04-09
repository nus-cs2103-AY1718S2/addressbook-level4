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

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.EventBuilder;

public class EventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Event(null, null, null, null, null));
    }

    @Test
    public void isValidEventName_null_throwsNullPointerException() {
        // null event name
        Assert.assertThrows(NullPointerException.class, () -> Event.isValidName(null));
    }

    @Test
    public void isValidName() {
        assertTrue(Event.isValidName(VALID_EVENT_NAME_F1));
        assertTrue(Event.isValidName(VALID_EVENT_NAME_NDP));
        assertFalse(Event.isValidName(INVALID_EVENT_NAME_DESC));
    }

    @Test
    public void isValidDate() {
        assertTrue(Event.isValidDate(VALID_EVENT_DATE_F1));
        assertTrue(Event.isValidDate(VALID_EVENT_DATE_NDP));
        assertFalse(Event.isValidDate(INVALID_EVENT_DATE_DESC));
    }

    @Test
    public void isValidTime() {
        assertTrue(Event.isValidTime(VALID_EVENT_START_TIME_F1));
        assertTrue(Event.isValidTime(VALID_EVENT_START_TIME_NDP));
        assertTrue(Event.isValidTime(VALID_EVENT_END_TIME_F1));
        assertTrue(Event.isValidTime(VALID_EVENT_END_TIME_NDP));
        assertFalse(Event.isValidTime(INVALID_EVENT_START_TIME_DESC));
        assertFalse(Event.isValidTime(INVALID_EVENT_END_TIME_DESC));
    }

    @Test
    public void equals() {
        Event f1Race1 = new EventBuilder().build();
        Event f1Race2 = new EventBuilder().withName(VALID_EVENT_NAME_F1).withDate(VALID_EVENT_DATE_F1)
                .withStartTime(VALID_EVENT_START_TIME_F1).withEndTime(VALID_EVENT_END_TIME_F1).build();

        assertTrue(f1Race1.equals(f1Race1));
        assertFalse(f1Race1.equals(1));
        assertTrue(f1Race1.equals(f1Race2));
        assertFalse(f1Race1.equals(new Event()));
    }

    @Test
    public void toString_test() {
        Event f1Race1 = new EventBuilder().build();
        Event f1Race2 = new EventBuilder().withName(VALID_EVENT_NAME_F1).withDate(VALID_EVENT_DATE_F1)
                .withStartTime(VALID_EVENT_START_TIME_F1).withEndTime(VALID_EVENT_END_TIME_F1).build();

        assertTrue(f1Race1.toString().equals(f1Race1.toString()));
        assertTrue(f1Race1.toString().equals(f1Race2.toString()));
        assertFalse(f1Race1.toString().equals(new Event().toString()));
    }
}
