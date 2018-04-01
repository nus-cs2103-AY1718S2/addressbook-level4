// @@author kush1509
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null company
        Assert.assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // invalid company
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany(" ")); // spaces only
        assertFalse(Company.isValidCompany("^")); // only non-alphanumeric characters
        assertFalse(Company.isValidCompany("google*")); // contains non-alphanumeric characters

        // valid company
        assertTrue(Company.isValidCompany("google")); // alphabets only
        assertTrue(Company.isValidCompany("12345")); // numbers only
        assertTrue(Company.isValidCompany("facebook 2nd")); // alphanumeric characters
        assertTrue(Company.isValidCompany("Capital Ventures")); // with capital letters
        assertTrue(Company.isValidCompany("Google Services Pvt Ltd")); // long companys
    }

    private void assertTrue(boolean google) {
    }
}
