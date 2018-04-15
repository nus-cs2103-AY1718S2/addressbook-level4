package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.util.SecurityUtil;
import seedu.address.model.Password;

//@@author yeggasd
public class XmlAdaptedPasswordTest {

    @Test
    public void toModelType_validPassword_returnsPassword() throws Exception {
        Password pass = new Password(hash("test"), hash("test"));
        XmlAdaptedPassword password = new XmlAdaptedPassword(pass);
        assertEquals(pass, password.toModelType());
    }

    private byte[] hash(String password) {
        return SecurityUtil.hashPassword(password);
    }
}
