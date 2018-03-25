package seedu.address.logic.commands;


import seedu.address.logic.LogicManager;

/**
 * Set the application password
 * */
public class SetPasswordCommand extends Command{

    public static final String COMMAND_WORD = "setPassword";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set new application password, and old "
            + "password requeired."
            + "Parameters: "
            + "oldPassword" + " newPassword ";

    public static final String MESSAGE_SUCCESS = "New password has been set!";

    private String oldPassword;

    private String newPassword;

    public SetPasswordCommand(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    public CommandResult execute() {
        if (this.oldPassword.compareTo(LogicManager.getPassword()) == 0) {
            LogicManager.setPassword(this.newPassword);
            model.setPassword(this.newPassword);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult("Incorrect old password!");
        }
    }

}
