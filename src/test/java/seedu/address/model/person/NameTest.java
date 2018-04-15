package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    //@@author aussiroth
    @Test
    public void checkNameEquality() {
        //test name against non-name type
        assertFalse(new Name("Stutor").equals(null));
        assertFalse(new Name("Stutor").equals(new Address("Stutor")));
        //test correctly returns equal if name string is the same
        assertTrue(new Name("Stutor").equals(new Name("Stutor")));
    }

    @Test
    public void checkNameHashCode() {
        Name name = new Name("peter jack");
        assertTrue(name.hashCode() == name.fullName.hashCode());
        name = new Name("12345");
        assertTrue(name.hashCode() == name.fullName.hashCode());
        name = new Name("Capital Tan");
        assertTrue(name.hashCode() == name.fullName.hashCode());
    }
}
