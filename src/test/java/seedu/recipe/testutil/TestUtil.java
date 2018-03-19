package seedu.recipe.testutil;

import java.io.File;
import java.io.IOException;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.util.FileUtil;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting string.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    /**
     * Returns the middle index of the recipe in the {@code model}'s recipe list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getRecipeBook().getRecipeList().size() / 2);
    }

    /**
     * Returns the last index of the recipe in the {@code model}'s recipe list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getRecipeBook().getRecipeList().size());
    }

    /**
     * Returns the recipe in the {@code model}'s recipe list at {@code index}.
     */
    public static Recipe getRecipe(Model model, Index index) {
        return model.getRecipeBook().getRecipeList().get(index.getZeroBased());
    }
}
