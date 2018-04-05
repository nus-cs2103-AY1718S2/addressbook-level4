package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedActivity.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalActivities.CIP;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Location;
import seedu.address.model.activity.Name;
import seedu.address.testutil.Assert;

//@@author karenfrilya97
public class XmlAdaptedEventTest {

    private static final Event CIP_EVENT = CIP;

    private static final String INVALID_NAME = "Rachel's Bday";
    private static final String INVALID_DATE_TIME = "23 April 2018";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = CIP_EVENT.getName().toString();
    private static final String VALID_START_DATE_TIME = CIP_EVENT.getStartDateTime().toString();
    private static final String VALID_END_DATE_TIME = CIP_EVENT.getEndDateTime().toString();
    private static final String VALID_LOCATION = CIP_EVENT.getLocation().toString();
    private static final String VALID_REMARK = CIP_EVENT.getRemark().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = CIP_EVENT.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(CIP_EVENT);
        assertEquals(CIP_EVENT, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(INVALID_NAME, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                        VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, event.getActivityType(), "name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, INVALID_DATE_TIME, VALID_END_DATE_TIME,
                        VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, null, VALID_END_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, event.getActivityType(), "start date/time");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, INVALID_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, null,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, event.getActivityType(), "end date/time");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                INVALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Location.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, invalidTags);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

}
