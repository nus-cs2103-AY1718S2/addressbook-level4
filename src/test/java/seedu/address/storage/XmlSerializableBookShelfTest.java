package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.BookShelf;
import seedu.address.testutil.TypicalBooks;

public class XmlSerializableBookShelfTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableBookShelfTest/");
    private static final File TYPICAL_BOOKS_FILE = new File(TEST_DATA_FOLDER + "typicalBooksBookShelf.xml");
    private static final File INVALID_BOOK_FILE = new File(TEST_DATA_FOLDER + "invalidBookBookShelf.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalBooksFile_success() throws Exception {
        XmlSerializableBookShelf dataFromFile = XmlUtil.getDataFromFile(TYPICAL_BOOKS_FILE,
                XmlSerializableBookShelf.class);
        BookShelf addressBookFromFile = dataFromFile.toModelType();
        BookShelf typicalBookShelf = TypicalBooks.getTypicalBookShelf();
        assertEquals(addressBookFromFile, typicalBookShelf);
    }

    @Test
    public void toModelType_invalidBookFile_throwsIllegalValueException() throws Exception {
        XmlSerializableBookShelf dataFromFile = XmlUtil.getDataFromFile(INVALID_BOOK_FILE,
                XmlSerializableBookShelf.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

}
