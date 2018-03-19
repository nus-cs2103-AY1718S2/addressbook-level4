package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.LoginCommand.FAKE_PASSWORD;
import static seedu.address.logic.commands.LoginCommand.TEST_PASSWORD;
import static seedu.address.logic.commands.LoginCommand.TEST_USERNAME;

import static seedu.address.logic.LoginManager.DOCTOR_LOGIN;
import static seedu.address.logic.LoginManager.NO_USER;

import org.junit.Test;

public class LoginManagerTest {

    @Test
    public void authenticate_correctPassword_returnTrue() {
        boolean expectSuccess = LoginManager.authenticate(TEST_USERNAME, TEST_PASSWORD);
        int loginState = LoginManager.getUserState();

        assertEquals(expectSuccess, true);
        assertEquals(loginState, DOCTOR_LOGIN);
    }

    @Test
    public void authenticate_wrongPassword_returnFalse() {
        boolean expectFail = LoginManager.authenticate(TEST_USERNAME, FAKE_PASSWORD);
        int loginState = LoginManager.getUserState();

        assertEquals(expectFail, false);
        assertEquals(loginState, NO_USER);
    }
}
