package seedu.address.storage;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;

public class XmlSerializableDeskBoardTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableDeskBoardTest/");
    private static final File TYPICAL_ACTIVITIES_FILE = new File(TEST_DATA_FOLDER + "typicalActivitiesDeskBoard.xml");
    private static final File INVALID_ACTIVITY_FILE = new File(TEST_DATA_FOLDER + "invalidActivityDeskBoard.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagDeskBoard.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_invalidActivityFile_throwsIllegalValueException() throws Exception {
        XmlSerializableDeskBoard dataFromFile = XmlUtil.getDataFromFile(INVALID_ACTIVITY_FILE,
                XmlSerializableDeskBoard.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableDeskBoard dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableDeskBoard.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
