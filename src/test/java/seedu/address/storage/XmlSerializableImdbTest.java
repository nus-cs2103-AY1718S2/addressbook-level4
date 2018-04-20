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
import seedu.address.model.Imdb;
import seedu.address.testutil.TypicalPatients;

public class XmlSerializableImdbTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableImdbTest/");
    private static final File TYPICAL_PERSONS_FILE = new File(TEST_DATA_FOLDER + "typicalPatientImdb.xml");
    private static final File INVALID_PERSON_FILE = new File(TEST_DATA_FOLDER + "invalidPatientImdb.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagImdb.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableImdb dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableImdb.class);
        Imdb imdbFromFile = dataFromFile.toModelType();
        Imdb typicalPersonsImdb = TypicalPatients.getTypicalAddressBook();
        assertEquals(imdbFromFile, typicalPersonsImdb);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableImdb dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableImdb.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableImdb dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableImdb.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void equals() throws Exception {
        XmlSerializableImdb dataFromFileFirstTest = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableImdb.class);
        XmlSerializableImdb dataFromFileSecondTest = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableImdb.class);

        //same object --> return true
        assertTrue(dataFromFileFirstTest.equals(dataFromFileFirstTest));

        //same value --> return true
        assertTrue(dataFromFileFirstTest.equals(dataFromFileSecondTest));

        // different types -> returns false
        assertFalse(dataFromFileFirstTest.equals(1));

        //null -> returns false
        assertFalse(dataFromFileFirstTest.equals(null));

    }
}
