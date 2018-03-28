package seedu.recipe.ui.util;

import java.io.File;

import seedu.recipe.commons.util.FileUtil;

public class CloudStorageUtil {

    public static final String APP_KEY = "0kj3cb9w27d66n8";
    public static final String APP_SECRET = "7stnncfsyvgim60";
    public static final String ACCESS_TOKEN = "";

    public static final String RECIPE_DATA_FOLDER = FileUtil.getPath("data/");
    public static final File RECIPE_BOOK_FILE = new File(RECIPE_DATA_FOLDER + "recipebook.xml");
    public static final String CLIENT_IDENTIFIER = "dropbox/recirecipe";

    private static String authorizationUrl;

}
