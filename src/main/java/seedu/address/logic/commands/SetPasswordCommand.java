package seedu.address.logic.commands;
//@@author crizyli
import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.logic.SetPasswordEnteredEvent;
import seedu.address.commons.events.ui.ShowSetPasswordDialogEvent;
import seedu.address.logic.LogicManager;

/**
 * Set the application password
 * */
public class SetPasswordCommand extends Command {

    public static final String COMMAND_WORD = "setPassword";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set new application password, and old "
            + "password required. \n";

    public static final String MESSAGE_SUCCESS = "New password has been set!";

    public static final String MESSAGE_INCORRECT_OLDPASSWORD = "Incorrect old password!";

    public static final String MESSAGE_INCOMPLETE_FIELD = "Input field(s) not complete!";

    private String oldPassword;

    private String newPassword;

    private boolean isComplete;

    private boolean isTestMode;

    public SetPasswordCommand() {
        isComplete = true;
        registerAsAnEventHandler(this);
    }

    @Override
    public CommandResult execute() {

        if (!isTestMode) {
            EventsCenter.getInstance().post(new ShowSetPasswordDialogEvent());
        } else {
            this.oldPassword = "admin";
            this.newPassword = "newpsw";
        }

        if (!isComplete) {
            return new CommandResult(MESSAGE_INCOMPLETE_FIELD);
        }

        if (this.oldPassword.compareTo(LogicManager.getPassword()) == 0) {
            LogicManager.setPassword(this.newPassword);
            model.setPassword(this.newPassword);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_INCORRECT_OLDPASSWORD);
        }
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPasswordCommand);
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    private void handleSetPasswordEnteredEvent(SetPasswordEnteredEvent event) {

        if (event.getPassword().equals("incomplete")) {
            isComplete = false;
        }

        this.oldPassword = event.getPassword().substring(0, event.getPassword().lastIndexOf(","));
        this.newPassword = event.getPassword().substring(event.getPassword().lastIndexOf(",") + 1);
    }

    public void setTestMode() {
        this.isTestMode = true;
    }
}
