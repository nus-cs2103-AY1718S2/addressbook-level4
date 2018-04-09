package seedu.address.model.group;
//@@author jas5469

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class InformationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Information(null));
    }

    @Test
    public void constructor_invalidInformation_throwsIllegalArgumentException() {
        String invalidInformation = "!";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Information(invalidInformation));
    }

    @Test
    public void isValidInformation() {
        // null content
        Assert.assertThrows(NullPointerException.class, () -> Information.isValidInformation(null));

        // invalid content
        assertFalse(Information.isValidInformation("")); // empty string
        assertFalse(Information.isValidInformation(" ")); // spaces only
        assertFalse(Information.isValidInformation("^")); // only non-alphanumeric characters
        assertFalse(Information.isValidInformation("Something to do*")); // contains non-alphanumeric characters

        // valid content
        assertTrue(Information.isValidInformation("hello world")); // alphabets only
        assertTrue(Information.isValidInformation("12345")); // numbers only
        assertTrue(Information.isValidInformation("hello world the 2nd")); // alphanumeric characters
        assertTrue(Information.isValidInformation("Hello World")); // with capital letters
        assertTrue(Information.isValidInformation("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void isSameInformationHashCode() {
        Information firstInformation = new Information(VALID_CONTENT);
        Information secondInformation = new Information(VALID_CONTENT);
        assertTrue(firstInformation.hashCode() == secondInformation.hashCode());
    }
}
