//@@author nicholasangcx
package seedu.recipe.logic.commands.exceptions;

import com.dropbox.core.DbxException;

/**
 * Represents an error which occurs during execution of a specific {@link UploadCommand}
 */
public class UploadCommandException extends DbxException {

    public UploadCommandException(String message) {
        super(message);
    }
}
//@@author
