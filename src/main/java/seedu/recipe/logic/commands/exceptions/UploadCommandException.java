package seedu.recipe.logic.commands.exceptions;

import com.dropbox.core.DbxException;

public class UploadCommandException extends DbxException{

    public UploadCommandException(String message) {
        super(message);
    }

}
