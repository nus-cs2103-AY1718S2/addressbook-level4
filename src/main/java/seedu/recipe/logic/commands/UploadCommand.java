//@@author nicholasangcx
package seedu.recipe.logic.commands;

import static seedu.recipe.storage.model.Filename.MESSAGE_FILENAME_CONSTRAINTS;

import seedu.recipe.commons.core.EventsCenter;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.events.ui.JumpToListRequestEvent;
import seedu.recipe.commons.events.ui.UploadRecipesEvent;
import seedu.recipe.ui.util.CloudStorageUtil;

/**
 * Uploads all recipes online, specifically to Dropbox.
 */
public class UploadCommand extends Command {

    public static final String COMMAND_WORD = "upload";
    public static final String MESSAGE_SUCCESS = "Upload success!";
    public static final String MESSAGE_FAILURE = "Failed to upload!";
    public static final String MESSAGE_UPLOAD = "Connecting to Dropbox......";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Uploads all recipes to your Dropbox with the "
            + "specified filename, with no spaces. It will only take in the first parameter. "
            + MESSAGE_FILENAME_CONSTRAINTS + "\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " RecipeBook";

    private static final Index FIRST_INDEX = Index.fromOneBased(1);

    public final String xmlExtensionFilename;

    /**
     * Creates an UploadCommand to upload recipebook.xml to Dropbox with the
     * specified {@code String XmlExtensionFilename}
     */
    public UploadCommand(String xmlExtensionFilename) {
        this.xmlExtensionFilename = xmlExtensionFilename;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new UploadRecipesEvent(xmlExtensionFilename));
        if (CloudStorageUtil.hasAccessToken()) {
            EventsCenter.getInstance().post(new JumpToListRequestEvent(FIRST_INDEX));
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_UPLOAD);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UploadCommand // instanceof handles nulls
                && xmlExtensionFilename.equals(((UploadCommand) other).xmlExtensionFilename));
    }
}
//@@author
