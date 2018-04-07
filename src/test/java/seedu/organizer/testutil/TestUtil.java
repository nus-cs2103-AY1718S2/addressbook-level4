package seedu.organizer.testutil;

import java.io.File;
import java.io.IOException;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.util.FileUtil;
import seedu.organizer.model.Model;
import seedu.organizer.model.task.Task;

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
     * Returns the middle index of the task in the {@code model}'s task list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getOrganizer().getTaskList().size() / 2);
    }

    /**
     * Returns the last index of the task in the {@code model}'s task list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getOrganizer().getTaskList().size());
    }

    /**
     * Returns the task in the {@code model}'s task list at {@code index}.
     */
    public static Task getTask(Model model, Index index) {
        return model.getOrganizer().getTaskList().get(index.getZeroBased());
    }
}
