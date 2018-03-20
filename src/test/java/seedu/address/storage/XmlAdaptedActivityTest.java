package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedActivity.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Name;
import seedu.address.testutil.Assert;

public class XmlAdaptedActivityTest {
    private static final String INVALID_NAME = "Rachel's Bday";
    private static final String INVALID_DATE_TIME = "23 April 2018";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = ASSIGNMENT2.getName().toString();
    private static final String VALID_DATE_TIME = ASSIGNMENT2.getDateTime().toString();
    private static final String VALID_REMARK = ASSIGNMENT2.getRemark().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = ASSIGNMENT2.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validActivityDetails_returnsActivity() throws Exception {
        XmlAdaptedActivity activity = new XmlAdaptedActivity(ASSIGNMENT2);
        assertEquals(ASSIGNMENT2, activity.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedActivity activity =
                new XmlAdaptedActivity(INVALID_NAME, VALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedActivity activity = new XmlAdaptedActivity(null, VALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        XmlAdaptedActivity activity =
                new XmlAdaptedActivity(VALID_NAME, INVALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        XmlAdaptedActivity activity = new XmlAdaptedActivity(VALID_NAME, null, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedActivity activity =
                new XmlAdaptedActivity(VALID_NAME, VALID_DATE_TIME, VALID_REMARK, invalidTags);
        Assert.assertThrows(IllegalValueException.class, activity::toModelType);
    }

}
