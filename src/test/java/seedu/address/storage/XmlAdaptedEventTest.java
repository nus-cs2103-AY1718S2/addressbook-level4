//@@author LeonidAgarth
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_F1;
import static seedu.address.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.testutil.Assert;
import seedu.address.testutil.EventBuilder;

public class XmlAdaptedEventTest {
    private static final String INVALID_NAME = "Something?!";
    private static final String INVALID_VENUE = "Some&where";
    private static final String INVALID_DATE = "30/02/2000";
    private static final String INVALID_START_TIME = "2369";
    private static final String INVALID_END_TIME = "23:59";

    private static final String VALID_NAME = VALID_EVENT_NAME_F1;
    private static final String VALID_VENUE = VALID_EVENT_VENUE_F1;
    private static final String VALID_DATE = VALID_EVENT_DATE_F1;
    private static final String VALID_START_TIME = VALID_EVENT_START_TIME_F1;
    private static final String VALID_END_TIME = VALID_EVENT_END_TIME_F1;

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(new EventBuilder().build());
        assertEquals(new EventBuilder().build(), event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(INVALID_NAME, VALID_VENUE, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_VENUE, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, INVALID_VENUE, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_VENUE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, null, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Venue");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, INVALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, null, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, INVALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, null, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "StartTime");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, VALID_START_TIME, INVALID_END_TIME);
        String expectedMessage = Event.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, VALID_START_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "EndTime");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void equals_test() {
        XmlAdaptedEvent event1 = new XmlAdaptedEvent(new EventBuilder().build());
        XmlAdaptedEvent event2 = new XmlAdaptedEvent(new EventBuilder().withName("Different").build());
        assertEquals(event1, event1);
        assertEquals(event1, new XmlAdaptedEvent(new EventBuilder().build()));
        assertNotEquals(event1, 1);
        assertNotEquals(event1, event2);
    }
}
