package seedu.address.logic.commands;
//@@author crizyli
import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.logic.AddressBookUnlockedEvent;
import seedu.address.commons.events.logic.PasswordEnteredEvent;
import seedu.address.commons.events.ui.ShowPasswordFieldEvent;
import seedu.address.logic.LogicManager;

/**
 * Unlocks ET
 */
public class UnlockCommand extends Command {

    public static final String COMMAND_WORD = "unlock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been unlocked!";

    public static final String MESSAGE_INCORRECT_PASSWORD = "Incorrect unlock password!";

    public static final String MESSAGE_MISSING_PASSWORD = "Password is missing!";

    private String password;

    private boolean isTestMode;

    public UnlockCommand() {
        isTestMode = false;
        registerAsAnEventHandler(this);
    }

    @Override
    public CommandResult execute() {
        if (!LogicManager.isLocked()) {
            return new CommandResult("Employees Tracker is already unlocked!");
        }

        if (!isTestMode) {
            EventsCenter.getInstance().post(new ShowPasswordFieldEvent());
        } else {
            this.password = "admin";
        }

        if (this.password.equals("nopassword")) {
            return new CommandResult(MESSAGE_MISSING_PASSWORD);
        }

        if (this.password.compareTo(LogicManager.getPassword()) == 0) {
            LogicManager.unLock();
            EventsCenter.getInstance().post(new AddressBookUnlockedEvent());
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_INCORRECT_PASSWORD);
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnlockCommand);
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    private void handlePasswordEnteredEvent(PasswordEnteredEvent event) {
        this.password = event.getPassword();
    }

    public void setTestMode() {
        isTestMode = true;
    }
}
