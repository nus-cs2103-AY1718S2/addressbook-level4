package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

//@@author yeggasd
/**
 * Removes password from the address book.
 */
public class RemovePasswordCommand extends Command {

    public static final String COMMAND_WORD = "nopassword";
    public static final String MESSAGE_SUCCESS = "Password removed.";
    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.updatePassword(null);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
