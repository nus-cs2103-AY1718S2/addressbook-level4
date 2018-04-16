package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.TaskBook;
import seedu.address.testutil.TypicalTasks;

//@@author CYX28
public class XmlSerializableTaskBookTest {

    private static final String TEST_DATA_FOLDER =
            FileUtil.getPath("src/test/data/XmlSerializableTaskBookTest/");
    private static final File TYPICAL_TASK_BOOK_FILE = new File(TEST_DATA_FOLDER + "typicalTaskBook.xml");
    private static final File INVALID_TASK_FILE = new File(TEST_DATA_FOLDER + "invalidTaskBook.xml");
    private static final File INVALID_TASK_CATEGORY_FILE =
            new File(TEST_DATA_FOLDER + "invalidTaskCategory.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalTaskBookFile_success() throws Exception {
        XmlSerializableTaskBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_TASK_BOOK_FILE,
                XmlSerializableTaskBook.class);
        TaskBook taskBookFromFile = dataFromFile.toModelType();
        TaskBook typicalTaskBook = TypicalTasks.getTypicalTaskBook();
        assertEquals(taskBookFromFile, typicalTaskBook);
    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        XmlSerializableTaskBook dataFromFile = XmlUtil.getDataFromFile(INVALID_TASK_FILE,
                XmlSerializableTaskBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTaskCategoryFile_throwsIllegalValueException() throws Exception {
        XmlSerializableTaskBook dataFromFile = XmlUtil.getDataFromFile(INVALID_TASK_CATEGORY_FILE,
                XmlSerializableTaskBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void equals() {
        XmlSerializableTaskBook taskBook = new XmlSerializableTaskBook();
        XmlSerializableTaskBook taskBookCopy = new XmlSerializableTaskBook();
        XmlSerializableAddressBook addressbook = new XmlSerializableAddressBook();

        // same object -> returns true
        assertTrue(taskBook.equals(taskBook));

        // same values -> returns true
        assertTrue(taskBook.equals(taskBookCopy));

        // different types -> returns false
        assertFalse(taskBook.equals(addressbook));
        assertFalse(taskBook.equals(5));

        // null -> returns false
        assertFalse(taskBook.equals(null));
    }

}
