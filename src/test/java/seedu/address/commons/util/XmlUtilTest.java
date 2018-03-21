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

import seedu.address.model.DeskBoard;
import seedu.address.storage.XmlAdaptedActivity;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableDeskBoard;
import seedu.address.testutil.DeskBoardBuilder;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validAddressBook.xml");
    private static final File MISSING_ACTIVITY_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingActivityField.xml");
    private static final File INVALID_ACTIVITY_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidActivityField.xml");
    private static final File VALID_ACTIVITY_FILE = new File(TEST_DATA_FOLDER + "validActivity.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml"));

    private static final String INVALID_DATE_TIME = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_DATE_TIME = "9482424";
    private static final String VALID_REMARK = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, DeskBoard.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, DeskBoard.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, DeskBoard.class);
    }

    //TODO: TEST
    /**
     * Test
     */
    public void getDataFromFile_validFile_validResult() throws Exception {
        DeskBoard dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableDeskBoard.class).toModelType();
        assertEquals(9, dataFromFile.getActivityList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void xmlAdaptedActivityFromFile_fileWithMissingActivityField_validResult() throws Exception {
        XmlAdaptedActivity actualActivity = XmlUtil.getDataFromFile(
                MISSING_ACTIVITY_FIELD_FILE, XmlAdaptedActivityWithRootElement.class);
        XmlAdaptedActivity expectedActivity = new XmlAdaptedActivity(
                null, VALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        assertEquals(expectedActivity, actualActivity);
    }

    @Test
    public void xmlAdaptedActivityFromFile_fileWithInvalidActivityField_validResult() throws Exception {
        XmlAdaptedActivity actualActivity = XmlUtil.getDataFromFile(
                INVALID_ACTIVITY_FIELD_FILE, XmlAdaptedActivityWithRootElement.class);
        XmlAdaptedActivity expectedActivity = new XmlAdaptedActivity(
                VALID_NAME, INVALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        assertEquals(expectedActivity, actualActivity);
    }

    @Test
    public void xmlAdaptedActivityFromFile_fileWithValidActivity_validResult() throws Exception {
        XmlAdaptedActivity actualActivity = XmlUtil.getDataFromFile(
                VALID_ACTIVITY_FILE, XmlAdaptedActivityWithRootElement.class);
        XmlAdaptedActivity expectedActivity = new XmlAdaptedActivity(
                VALID_NAME, VALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        assertEquals(expectedActivity, actualActivity);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new DeskBoard());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new DeskBoard());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableDeskBoard dataToWrite = new XmlSerializableDeskBoard(new DeskBoard());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableDeskBoard dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableDeskBoard.class);
        assertEquals(dataToWrite, dataFromFile);

        DeskBoardBuilder builder = new DeskBoardBuilder(new DeskBoard());
        dataToWrite = new XmlSerializableDeskBoard(
                builder.withActivity(new TaskBuilder().build()).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableDeskBoard.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data
     * to {@code XmlAdaptedActivity}
     * objects.
     */
    @XmlRootElement(name = "activity")
    private static class XmlAdaptedActivityWithRootElement extends XmlAdaptedActivity {}
}
