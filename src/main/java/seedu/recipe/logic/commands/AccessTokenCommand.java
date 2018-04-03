//@@author nicholasangcx
package seedu.recipe.logic.commands;

import seedu.recipe.ui.util.CloudStorageUtil;

/**
 * Takes in the access token given by dropbox and saves it in the app
 * to allow user to upload files easily.
 */
public class AccessTokenCommand extends Command {

    public static final String COMMAND_WORD = "token";
    public static final String MESSAGE_SUCCESS = "Upload Success!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Takes in the access token given by Dropbox "
            + "to allow user to upload files. *ONLY use this command directly after the upload command.*\n"
            + "Parameters: TOKEN\n"
            + "Example: " + COMMAND_WORD + "VALID_ACCESS_TOKEN";

    private final String accessToken;

    public AccessTokenCommand(String token) {
        this.accessToken = token;
        CloudStorageUtil.setAccessToken(token);
    }

    @Override
    public CommandResult execute() {
        CloudStorageUtil.upload();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccessTokenCommand // instanceof handles nulls
                && accessToken.equals(((AccessTokenCommand) other).accessToken));
    }
}
//@@author
