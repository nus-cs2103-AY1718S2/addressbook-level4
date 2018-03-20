package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.BookShelf;
import seedu.address.storage.XmlAdaptedAuthor;
import seedu.address.storage.XmlAdaptedBook;
import seedu.address.storage.XmlAdaptedCategory;
import seedu.address.storage.XmlSerializableBookShelf;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookShelfBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validBookShelf.xml");
    private static final File MISSING_BOOK_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingBookField.xml");
    private static final File VALID_BOOK_FILE = new File(TEST_DATA_FOLDER + "validBook.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempBookShelf.xml"));

    private static final String VALID_TITLE = "Artemis";
    private static final String VALID_DESCRIPTION = "This is Artemis.";
    private static final int VALID_RATING = -1;
    private static final List<XmlAdaptedAuthor> VALID_AUTHORS =
            Collections.singletonList(new XmlAdaptedAuthor("Andy Weir"));
    private static final List<XmlAdaptedCategory> VALID_CATEGORIES =
            Collections.singletonList(new XmlAdaptedCategory("Fiction"));
    private static final String VALID_GID = "ry3GjwEACAAJ";
    private static final String VALID_ISBN = "9780525572664";
    private static final String VALID_PUBLISHER = " ";
    private static final String VALID_PUBLICATION_DATE = "2017-11-14";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, BookShelf.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, BookShelf.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, BookShelf.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        BookShelf dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableBookShelf.class).toModelType();
        assertEquals(4, dataFromFile.getBookList().size());
    }

    @Test
    public void xmlAdaptedBookFromFile_fileWithMissingBookField_validResult() throws Exception {
        XmlAdaptedBook actualBook = XmlUtil.getDataFromFile(
                MISSING_BOOK_FIELD_FILE, XmlAdaptedBookWithRootElement.class);
        XmlAdaptedBook expectedBook = new XmlAdaptedBook(null, null, null,
                VALID_DESCRIPTION, VALID_RATING, VALID_AUTHORS, VALID_CATEGORIES, null, null);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void xmlAdaptedBookFromFile_fileWithValidBook_validResult() throws Exception {
        XmlAdaptedBook actualBook = XmlUtil.getDataFromFile(
                VALID_BOOK_FILE, XmlAdaptedBookWithRootElement.class);
        XmlAdaptedBook expectedBook = new XmlAdaptedBook(VALID_GID, VALID_ISBN, VALID_TITLE,
                VALID_DESCRIPTION, VALID_AUTHORS, VALID_CATEGORIES, VALID_PUBLISHER, VALID_PUBLICATION_DATE);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new BookShelf());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new BookShelf());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableBookShelf dataToWrite = new XmlSerializableBookShelf(new BookShelf());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableBookShelf dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableBookShelf.class);
        assertEquals(dataToWrite, dataFromFile);

        BookShelfBuilder builder = new BookShelfBuilder(new BookShelf());
        dataToWrite = new XmlSerializableBookShelf(builder.withBook(new BookBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableBookShelf.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedBook}
     * objects.
     */
    @XmlRootElement(name = "book")
    private static class XmlAdaptedBookWithRootElement extends XmlAdaptedBook {}
}
