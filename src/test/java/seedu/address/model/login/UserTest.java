package seedu.address.model.login;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author kaisertanqr
public class UserTest {

    private static final Username VALID_USERNAME = new Username("kaiser");
    private static final Password VALID_PASSWORD = new Password("pass");
    private static final String VALID_FILE_PATH = "addressbook-kaiser.xml";

    @Test
    public void constructor_null_throwsNullPointerException() {
        // ================== normal constructor ======================

        // null username
        Assert.assertThrows(NullPointerException.class, () -> new User(null, VALID_PASSWORD));

        // null password
        Assert.assertThrows(NullPointerException.class, () -> new User(VALID_USERNAME, null));

        // ================== overloaded constructor ======================

        // null username
        Assert.assertThrows(NullPointerException.class, () -> new User(null, VALID_PASSWORD, VALID_FILE_PATH));

        // null password
        Assert.assertThrows(NullPointerException.class, () -> new User(VALID_USERNAME, null, VALID_FILE_PATH));

        // null address book file path
        Assert.assertThrows(NullPointerException.class, () -> new User(VALID_USERNAME, VALID_PASSWORD, null));

    }
}
