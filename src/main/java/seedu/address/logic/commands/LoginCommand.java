//@@author cxingkai
package seedu.address.logic.commands;

import javafx.stage.Stage;
import seedu.address.logic.login.LoginManager;
import seedu.address.logic.login.LoginWindow;

/**
 * Allows user to login to the system
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "lg";

    public static final String MESSAGE_ALREADY_LOGGED_IN = "Already logged in";
    public static final String MESSAGE_LOGIN_CANCEL = "Login cancelled.";
    public static final String MESSAGE_LOGIN_SUCCESS = "Successfully logged in as ";

    public static final String MESSAGE_NOT_LOGGED_IN = "Not logged in!\n%1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows user to login to the system "
            + "given the correct username and password.\n"
            + "Example: " + COMMAND_WORD;

    public static final String TEST_USERNAME = "alice";
    public static final String TEST_PASSWORD = "password123";
    public static final String FAKE_PASSWORD = "fake_password";

    @Override
    public CommandResult execute() {
        LoginWindow loginWindow = new LoginWindow();
        Stage stage = new Stage();
        loginWindow.start(stage);

        return new CommandResult(processResultString());
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand); // instanceof handles nulls
    }

    /**
     * Creates output String for user feedback
     */
    public String processResultString() {
        String userRole = null;
        if (LoginManager.getUserState() == LoginManager.DOCTOR_LOGIN) {
            userRole = "Doctor";
        } else if (LoginManager.getUserState() == LoginManager.MEDICAL_STAFF_LOGIN) {
            userRole = "Staff";
        } else {
            return MESSAGE_LOGIN_CANCEL;
        }

        return MESSAGE_LOGIN_SUCCESS + LoginManager.getUserName() + " (" + userRole + ")";
    }
}
