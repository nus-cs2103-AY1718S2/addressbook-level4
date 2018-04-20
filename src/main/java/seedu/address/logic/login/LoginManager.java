//@@author cxingkai
package seedu.address.logic.login;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import seedu.address.MainApp;

/**
 * LoginManager class to store login state and handle login attempts
 */
public final class LoginManager {
    public static final int NO_USER_STATE = 0;
    public static final int DOCTOR_LOGIN = 1;
    public static final int MEDICAL_STAFF_LOGIN = 2;
    public static final String NO_USER_STRING = null;

    private static LoginState currLoginState = new LoginState(NO_USER_STATE, NO_USER_STRING);
    private static String passwordPath = "/data/passwords.csv";

    private LoginManager() {
        currLoginState = new LoginState(NO_USER_STATE, NO_USER_STRING);
    }

    public static int getUserState() {
        return currLoginState.getState();
    }

    public static String getUserName() {
        return currLoginState.getUser();
    }

    /**
     * Utility function for tests
     */
    public static void logout() {
        currLoginState.updateState(NO_USER_STATE, NO_USER_STRING);
    }

    /**
     * Check if username and password match and are in the passwords list.
     * Login state will be updated.
     */
    public static boolean authenticate (String username, String password) {
        boolean match = false;
        int loginStateIndex = 0;

        try {
            InputStreamReader isr = new InputStreamReader(MainApp.class.getResourceAsStream(passwordPath));
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] lineArray = line.split(",");
                String fileUsername = lineArray[0];
                String filePassword = lineArray[1];

                if (username.equals(fileUsername) && password.equals(filePassword)) {
                    int role = Integer.parseInt(lineArray[2]);
                    loginStateIndex = role;
                    match = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if (match) {
            currLoginState.updateState(loginStateIndex, username);
            return true;
        }

        return false;
    }
}
