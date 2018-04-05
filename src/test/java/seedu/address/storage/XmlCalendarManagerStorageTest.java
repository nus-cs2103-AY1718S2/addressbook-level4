package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCalendarEntries.WORKSHOP;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.CalendarManager;
import seedu.address.model.ReadOnlyCalendarManager;

//@@author SuxianAlicia
public class XmlCalendarManagerStorageTest {
    private static final String TEST_DATA_FOLDER =
            FileUtil.getPath("./src/test/data/XmlCalendarManagerStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readCalendarManager_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readCalendarManager(null);
    }

    private java.util.Optional<ReadOnlyCalendarManager> readCalendarManager(String filePath) throws Exception {
        return new XmlCalendarManagerStorage(filePath).readCalendarManager(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCalendarManager("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readCalendarManager("NotXmlFormatCalendarManager.xml");
    }

    @Test
    public void readCalendarManager_invalidCalendarManager_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCalendarManager("invalidCalendarManager.xml");
    }

    @Test
    public void readCalendarManager_invalidAndValidCalendarManager_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCalendarManager("invalidAndValidCalendarManager.xml");
    }

    @Test
    public void readAndSaveCalendarManager_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        CalendarManager original = getTypicalCalendarManagerWithEntries();
        XmlCalendarManagerStorage xmlCalendarManagerStorage = new XmlCalendarManagerStorage(filePath);

        //Save in new file and read back
        xmlCalendarManagerStorage.saveCalendarManager(original, filePath);
        ReadOnlyCalendarManager readBack = xmlCalendarManagerStorage.readCalendarManager(filePath).get();
        assertEquals(original, new CalendarManager(readBack));

        //Modify data, overwrite exiting file, and read back
        original.deleteCalendarEntry(WORKSHOP);
        xmlCalendarManagerStorage.saveCalendarManager(original, filePath);
        readBack = xmlCalendarManagerStorage.readCalendarManager(filePath).get();
        assertEquals(original, new CalendarManager(readBack));

        //Save and read without specifying file path
        original.addCalendarEntry(WORKSHOP);
        xmlCalendarManagerStorage.saveCalendarManager(original); //file path not specified
        readBack = xmlCalendarManagerStorage.readCalendarManager().get(); //file path not specified
        assertEquals(original, new CalendarManager(readBack));

    }

    @Test
    public void saveCalendarManager_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveCalendarManager(null, "SomeFile.xml");
    }

    /**
     * Saves {@code calendarManager} at the specified {@code filePath}.
     */
    private void saveCalendarManager(ReadOnlyCalendarManager calendarManager, String filePath) {
        try {
            new XmlCalendarManagerStorage(filePath)
                    .saveCalendarManager(calendarManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveCalendarManager(new CalendarManager(), null);
    }


}
