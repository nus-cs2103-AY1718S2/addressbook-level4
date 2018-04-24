package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.DeskBoard;
import seedu.address.testutil.TypicalActivities;

public class XmlSerializableDeskBoardTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableDeskBoardTest/");
    private static final File TYPICAL_ACTIVITIES_FILE = new File(TEST_DATA_FOLDER + "typicalActivitiesDeskBoard.xml");
    private static final File INVALID_ACTIVITY_FILE = new File(TEST_DATA_FOLDER + "invalidActivityDeskBoard.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagDeskBoard.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalActivitiesFile_success() throws Exception {
        XmlSerializableDeskBoard dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ACTIVITIES_FILE,
                XmlSerializableDeskBoard.class);
        DeskBoard deskBoardFromFile = dataFromFile.toModelType();
        DeskBoard typicalActivitiesDeskBoard = TypicalActivities.getTypicalDeskBoard();
        boolean equals = deskBoardFromFile.getTagList().get(1).equals(
                    typicalActivitiesDeskBoard.getTagList().get(1));
        boolean equals2 = deskBoardFromFile.getTagList().get(2).equals(
                typicalActivitiesDeskBoard.getTagList().get(2));
        boolean equals3 = deskBoardFromFile.getTagList().get(3).equals(
                typicalActivitiesDeskBoard.getTagList().get(3));
        boolean equals4 = deskBoardFromFile.getTagList().get(4).equals(
                typicalActivitiesDeskBoard.getTagList().get(4));

        assertEquals(deskBoardFromFile, typicalActivitiesDeskBoard);
    }

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
