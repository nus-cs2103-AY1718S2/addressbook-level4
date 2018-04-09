//@@author LeonidAgarth
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.storage.XmlAdaptedTag.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class XmlAdaptedTagTest {
    private static final String INVALID_NAME = "Something?!";
    private static final String INVALID_COLOR = "rainbow";

    private static final String VALID_NAME = VALID_TAG_FRIEND;
    private static final String VALID_COLOR = VALID_TAG_COLOR_RED;

    @Test
    public void toModelType_validTagDetails_returnsTag() throws Exception {
        XmlAdaptedTag tag = new XmlAdaptedTag(new Tag(VALID_NAME, VALID_COLOR));
        assertEquals(new Tag(VALID_NAME, VALID_COLOR), tag.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(INVALID_NAME);
        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(null, VALID_COLOR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_invalidColor_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_NAME, INVALID_COLOR);
        String expectedMessage = Tag.MESSAGE_TAG_COLOR_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullColor_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Color");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void equals_test() {
        XmlAdaptedTag tag1 = new XmlAdaptedTag(VALID_TAG_FRIEND);
        XmlAdaptedTag tag2 = new XmlAdaptedTag(VALID_TAG_HUSBAND, VALID_COLOR);
        assertEquals(tag1, tag1);
        assertEquals(tag1, new XmlAdaptedTag(VALID_TAG_FRIEND));
        assertNotEquals(tag1, 1);
        assertNotEquals(tag1, tag2);
    }
}
