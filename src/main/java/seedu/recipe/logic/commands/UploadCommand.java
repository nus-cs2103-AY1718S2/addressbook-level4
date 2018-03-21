package seedu.recipe.logic.commands;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import seedu.recipe.commons.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Uploads all recipes online, specifically to Dropbox.
 */
public class UploadCommand extends Command {

    public static final String COMMAND_WORD = "upload";
    public static final String MESSAGE_SUCCESS = "Upload success!";
    public static final String MESSAGE_FAILURE = "Failed to upload!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Uploads all recipes to your Dropbox with the "
            + "specified filename, with no spaces. It will only take in the first parameter and filenames that "
            + "do not already exist in your Dropbox.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " RecipeBook";

    private static final String ACCESS_TOKEN = "nF-Ym1zvMnAAAAAAAAAAPYd-4nRthqNAuk343dpYSiQXXHLBJFNraaaUUgPwokxl";
    private static final String RECIPE_DATA_FOLDER = FileUtil.getPath("data/");
    private static final File RECIPE_BOOK_FILE = new File(RECIPE_DATA_FOLDER + "addressbook.xml");

    private final String XmlExtensionFilename;

    /**
     * Creates an UploadCommand to upload addressbook.xml to Dropbox with the
     * specified {@code String XmlExtensionFilename}
     */
    public UploadCommand(String XmlExtensionFilename) {
        this.XmlExtensionFilename = XmlExtensionFilename;
    }

    @Override
    public CommandResult execute() throws DbxException {
        CommandResult result = upload();
        return result;
    }

    private CommandResult upload() throws DbxException {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/recirecipe").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        // Upload "addressbook.xml" to Dropbox
        try (InputStream in = new FileInputStream(RECIPE_BOOK_FILE)) {
            client.files().uploadBuilder("/" + XmlExtensionFilename).uploadAndFinish(in);
        } catch (IOException IOE) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
