//@@author Jason1im
package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Account;
import seedu.address.testutil.Assert;

public class XmlAdapatedAccountTest {
    private static final String INVALID_USERNAME = "::";
    private static final String INVALID_PASSWORD = "-a-";

    private static final String VALID_USERNAME = "John";
    private static final String VALID_PASSWORD = "doe123";

    @Test
    public void toModelType_validAccount_returnsAccount() throws Exception {
        Account account = new Account(VALID_USERNAME, VALID_PASSWORD);
        XmlAdaptedAccount xmlAccount = new XmlAdaptedAccount(account);
        assertEquals(account, xmlAccount.toModelType());
    }

    @Test
    public void toModelType_invalidUserName_throwsIllegalValueException() {
        XmlAdaptedAccount account = new XmlAdaptedAccount(INVALID_USERNAME, VALID_PASSWORD);
        String expectedMessage = Account.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        XmlAdaptedAccount account = new XmlAdaptedAccount(VALID_USERNAME, INVALID_PASSWORD);
        String expectedMessage = Account.MESSAGE_PASSWORD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }
}
