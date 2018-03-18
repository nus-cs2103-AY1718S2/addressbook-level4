package seedu.progresschecker.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.commons.util.FileUtil;
import seedu.progresschecker.commons.util.XmlUtil;
import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.testutil.TypicalPersons;

public class XmlSerializableProgressCheckerTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath(
            "src/test/data/XmlSerializableProgressCheckerTest/");
    private static final File TYPICAL_PERSONS_FILE = new File(TEST_DATA_FOLDER + "typicalPersonsProgressChecker.xml");
    private static final File INVALID_PERSON_FILE = new File(TEST_DATA_FOLDER + "invalidPersonProgressChecker.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagProgressChecker.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableProgressChecker dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableProgressChecker.class);
        ProgressChecker progressCheckerFromFile = dataFromFile.toModelType();
        ProgressChecker typicalPersonsProgressChecker = TypicalPersons.getTypicalProgressChecker();
        assertEquals(progressCheckerFromFile, typicalPersonsProgressChecker);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableProgressChecker dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableProgressChecker.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableProgressChecker dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableProgressChecker.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
