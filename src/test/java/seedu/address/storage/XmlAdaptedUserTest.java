package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedUser.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalUsers.KARA;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.testutil.Assert;

//@@author kaisertanqr
public class XmlAdaptedUserTest {

    private static final String INVALID_USERNAME = "Kaiser@@";
    private static final String INVALID_PASSWORD = "+651234";
    private static final String INVALID_ADDRESS_BOOK_FILE_PATH = "sadsasa.xml";

    private static final String VALID_USERNAME = KARA.getUsername().toString();
    private static final String VALID_PASSWORD = KARA.getPassword().toString();
    private static final String VALID_ADDRESSBOOK_FILE_PATH = KARA.getAddressBookFilePath().toString();


    @Test
    public void toModelType_validUserDetails_returnsUser() throws Exception {
        XmlAdaptedUser user = new XmlAdaptedUser(KARA);
        assertEquals(KARA, user.toModelType());
    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        XmlAdaptedUser user =
                new XmlAdaptedUser(INVALID_USERNAME, VALID_PASSWORD, VALID_ADDRESSBOOK_FILE_PATH);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        XmlAdaptedUser user =
                new XmlAdaptedUser(null, VALID_PASSWORD, VALID_ADDRESSBOOK_FILE_PATH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        XmlAdaptedUser user =
                new XmlAdaptedUser(VALID_USERNAME, INVALID_PASSWORD, VALID_ADDRESSBOOK_FILE_PATH);
        String expectedMessage = Password.MESSAGE_PASSWORD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        XmlAdaptedUser user =
                new XmlAdaptedUser(VALID_USERNAME, null, INVALID_ADDRESS_BOOK_FILE_PATH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

    @Test
    public void toModelType_invalidAddressBookFilePath_throwsIllegalValueException() {
        XmlAdaptedUser user =
                new XmlAdaptedUser(VALID_USERNAME, VALID_PASSWORD, INVALID_ADDRESS_BOOK_FILE_PATH);
        String expectedMessage = User.MESSAGE_AB_FILEPATH_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

    @Test
    public void toModelType_nullAddressBookFilePath_throwsIllegalValueException() {
        XmlAdaptedUser user =
                new XmlAdaptedUser(VALID_USERNAME, VALID_PASSWORD, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "AddressBook file path");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

}
