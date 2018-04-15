package seedu.address.testutil;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Model;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.order.Order;
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
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getPersonList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getPersonList().size());
    }

    //@@author amad-person
    /**
     * Returns the middle index of the order in the {@code model}'s order list.
     */
    public static Index getMidOrderIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getOrderList().size() / 2);
    }

    /**
     * Returns the last index of the order in the {@code model}'s order list.
     */
    public static Index getLastOrderIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getOrderList().size());
    }
    //@@author amad-person

    /**
     * Returns the middle index of the calendar entry in the {@code model}'s calendar entry list.
     */
    public static Index getMidEntryIndex(Model model) {
        return Index.fromOneBased(model.getCalendarManager().getCalendarEntryList().size() / 2);
    }

    /**
     * Returns the last index of the calendar entry in the {@code model}'s calendar entry list.
     */
    public static Index getLastEntryIndex(Model model) {
        return Index.fromOneBased(model.getCalendarManager().getCalendarEntryList().size());
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        return model.getAddressBook().getPersonList().get(index.getZeroBased());
    }

    //@@author amad-person
    /**
     * Returns the order in the {@code model}'s order list at {@code index}.
     */
    public static Order getOrder(Model model, Index index) {
        return model.getAddressBook().getOrderList().get(index.getZeroBased());
    }
    //@@author

    /**
     * Returns the calendar entry in the {@code model}'s calendar entry list at {@code index}.
     */
    public static CalendarEntry getCalendarEntry(Model model, Index index) {
        return model.getCalendarManager().getCalendarEntryList().get(index.getZeroBased());
    }
}
