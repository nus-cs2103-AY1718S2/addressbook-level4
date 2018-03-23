package seedu.recipe.logic.commands.exceptions;

import com.dropbox.core.DbxException;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
