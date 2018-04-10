package seedu.address.testutil;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;

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
     * Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getMidPersonIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getPersonList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastPersonIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getPersonList().size());
    }

    /**
     * Returns the middle index of the job in the {@code model}'s job list.
     */
    public static Index getMidJobIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getJobList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastJobIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getJobList().size());
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        return model.getAddressBook().getPersonList().get(index.getZeroBased());
    }

    /**
     * Returns the job in the {@code model}'s job list at {@code index}.
     */
    public static Job getJob(Model model, Index index) {
        return model.getAddressBook().getJobList().get(index.getZeroBased());
    }
}
