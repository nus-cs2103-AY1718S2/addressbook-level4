package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedCalendarEntry.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalCalendarEntries.MEETING_BOSS;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EntryTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;
import seedu.address.testutil.Assert;

//@@author SuxianAlicia
public class XmlAdaptedCalendarEntryTest {
    private static final String INVALID_EVENT_TITLE = "M&&ting wi$h b@ss";
    private static final String INVALID_START_DATE = "30-02-2019";
    private static final String INVALID_END_DATE = "31-02-2019";
    private static final String INVALID_START_TIME = "24:60";
    private static final String INVALID_END_TIME = "25:100";

    private static final String VALID_EVENT_TITLE = MEETING_BOSS.getEntryTitle().toString();
    private static final String VALID_START_DATE = MEETING_BOSS.getStartDate().toString();
    private static final String VALID_END_DATE = MEETING_BOSS.getEndDate().toString();
    private static final String VALID_START_TIME = MEETING_BOSS.getStartTime().toString();
    private static final String VALID_END_TIME = MEETING_BOSS.getEndTime().toString();

    @Test
    public void toModelType_validCalendarEventDetails_returnsCalendarEvent() throws Exception {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(MEETING_BOSS);
        assertEquals(MEETING_BOSS, calEvent.toModelType());
    }

    @Test
    public void toModelType_invalidEventTitle_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(INVALID_EVENT_TITLE, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullEventTitle_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(null, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EntryTitle.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, INVALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = StartDate.MESSAGE_START_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, null,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                INVALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = EndDate.MESSAGE_END_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                null, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                VALID_END_DATE, INVALID_START_TIME, VALID_END_TIME);
        String expectedMessage = StartTime.MESSAGE_START_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                VALID_END_DATE, null, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, INVALID_END_TIME);
        String expectedMessage = EndTime.MESSAGE_END_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }
}
