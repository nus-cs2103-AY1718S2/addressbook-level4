package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.commons.events.storage.DecryptionRequestEvent;
//@@author tinyjy94
/**
 * Decrypts data found in encryptedmovieplanner file.
 */
public class DecryptCommand extends Command {
    public static final String COMMAND_WORD = "decrypt";
    public static final String COMMAND_ALIAS = "dec";
    public static final String MESSAGE_SUCCESS = "MoviePlanner Decrypted! "
            + "Please restart the application to see the changes.";
    public static final String MESSAGE_WRONGPASSWORD = "Password is wrong!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Decrypts MoviePlanner file to view contents.\n "
            + "Parameters: " + PREFIX_PASSWORD + " PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PASSWORD + "dummypass ";

    private String password;

    public DecryptCommand(String password) {
        this.password = password;
    }

    @Override
    public CommandResult execute() {
        //user request to decrypt
        raise(new DecryptionRequestEvent(password));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
