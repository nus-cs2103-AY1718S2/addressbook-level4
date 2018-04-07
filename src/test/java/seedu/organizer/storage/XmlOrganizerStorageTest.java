package seedu.organizer.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.organizer.testutil.TypicalTasks.GROCERY;
import static seedu.organizer.testutil.TypicalTasks.INTERVIEWPREP;
import static seedu.organizer.testutil.TypicalTasks.MAKEPRESENT;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.organizer.commons.exceptions.DataConversionException;
import seedu.organizer.commons.util.FileUtil;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.ReadOnlyOrganizer;

public class XmlOrganizerStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlOrganizerStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readOrganizer_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readOrganizer(null);
    }

    private java.util.Optional<ReadOnlyOrganizer> readOrganizer(String filePath) throws Exception {
        return new XmlOrganizerStorage(filePath).readOrganizer(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readOrganizer("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readOrganizer("NotXmlFormatOrganizer.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readOrganizer_invalidTaskOrganizer_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readOrganizer("invalidTaskOrganizer.xml");
    }

    @Test
    public void readOrganizer_invalidAndValidTaskOrganizer_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readOrganizer("invalidAndValidTaskOrganizer.xml");
    }

    @Test
    public void readAndSaveOrganizer_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempOrganizer.xml";
        Organizer original = getTypicalOrganizer();
        XmlOrganizerStorage xmlOrganizerStorage = new XmlOrganizerStorage(filePath);

        //Save in new file and read back
        xmlOrganizerStorage.saveOrganizer(original, filePath);
        ReadOnlyOrganizer readBack = xmlOrganizerStorage.readOrganizer(filePath).get();
        assertEquals(original, new Organizer(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(MAKEPRESENT);
        original.removeTask(GROCERY);
        xmlOrganizerStorage.saveOrganizer(original, filePath);
        readBack = xmlOrganizerStorage.readOrganizer(filePath).get();
        assertEquals(original, new Organizer(readBack));

        //Save and read without specifying file path
        original.addTask(INTERVIEWPREP);
        xmlOrganizerStorage.saveOrganizer(original); //file path not specified
        readBack = xmlOrganizerStorage.readOrganizer().get(); //file path not specified
        assertEquals(original, new Organizer(readBack));

    }

    @Test
    public void saveOrganizer_nullOrganizer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveOrganizer(null, "SomeFile.xml");
    }

    /**
     * Saves {@code organizer} at the specified {@code filePath}.
     */
    private void saveOrganizer(ReadOnlyOrganizer organizer, String filePath) {
        try {
            new XmlOrganizerStorage(filePath).saveOrganizer(organizer, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveOrganizer_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveOrganizer(new Organizer(), null);
    }


}
