package seedu.address.testutil;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.todo.ToDo;

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
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getPersonList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getPersonList().size());
    }

    /**
     * Returns the last index of the to-do in the {@code model}'s to-do list.
     */
    public static Index getLastIndexToDo(Model model) {
        return Index.fromOneBased(model.getAddressBook().getToDoList().size());
    }

    /**
     * Returns the last index of the group in the {@code model}'s to-do list.
     */
    public static Index getLastIndexGroup(Model model) {
        return Index.fromOneBased(model.getAddressBook().getGroupList().size());
    }


    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        return model.getAddressBook().getPersonList().get(index.getZeroBased());
    }

    /**
     * Returns the to-do in the {@code model}'s to-do list at {@code index}.
     */
    public static ToDo getToDo(Model model, Index index) {
        return model.getAddressBook().getToDoList().get(index.getZeroBased());
    }
    //@@author jas5469
    /**
     * Returns the group in the {@code model}'s group list at {@code index}.
     */
    public static Group getGroup(Model model, Index index) {
        return model.getAddressBook().getGroupList().get(index.getZeroBased());
    }
    //@@author
}
