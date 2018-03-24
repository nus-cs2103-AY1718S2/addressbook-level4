package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author jlks96
public class PersonNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PersonName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PersonName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> PersonName.isValidName(null));

        // invalid name
        assertFalse(PersonName.isValidName("")); // empty string
        assertFalse(PersonName.isValidName(" ")); // spaces only
        assertFalse(PersonName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(PersonName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(PersonName.isValidName("peter jack")); // alphabets only
        assertTrue(PersonName.isValidName("12345")); // numbers only
        assertTrue(PersonName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(PersonName.isValidName("Capital Tan")); // with capital letters
        assertTrue(PersonName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
