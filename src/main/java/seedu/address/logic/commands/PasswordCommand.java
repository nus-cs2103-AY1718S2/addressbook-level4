package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.SecurityUtil;

//@@author yeggasd
/**
 * Adds a password to the address book.
 */
public class PasswordCommand extends Command {

    public static final String COMMAND_WORD = "password";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the password used for en. "
            + "Parameters: password PASSWORD"
            + "Example: " + COMMAND_WORD + "test";
    public static final String INVALID_PASSWORD = "Password cannot be blank!";

    public static final String MESSAGE_SUCCESS = "Password updated.";

    private String password;
    /**
     * Creates an PasswordCommand to add the specified password
     */
    public PasswordCommand(String password) {
        requireNonNull(password);
        this.password = password;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        byte[] hashedPassword = SecurityUtil.hashPassword(password);
        model.updatePassword(hashedPassword);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordCommand // instanceof handles nulls
                && password.equals(((PasswordCommand) other).password));
    }
}
