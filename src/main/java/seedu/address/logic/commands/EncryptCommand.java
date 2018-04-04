package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.commons.events.storage.EncryptionRequestEvent;
//@@author tinyjy94
/**
 * Encrypts data stored in movieplanner file.
 */
public class EncryptCommand extends Command {

    public static final String COMMAND_WORD = "encrypt";
    public static final String COMMAND_ALIAS = "enc";
    public static final String MESSAGE_SUCCESS = "MoviePlanner Encrypted!";
    public static final String MESSAGE_ERRORENCRYPTING = "Error in encrypting!"
            + " Please make sure file format is correct!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Encrypts MoviePlanner file to prevent data leak.\n "
            + "Parameters: " + PREFIX_PASSWORD + " PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PASSWORD + "dummypass ";

    private String password;

    public EncryptCommand(String password) {
        this.password = password;
    }

    @Override
    public CommandResult execute() {
        //user request to encrypt
        raise(new EncryptionRequestEvent(password));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
