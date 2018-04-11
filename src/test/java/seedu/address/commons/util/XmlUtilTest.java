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
import seedu.address.storage.XmlAdaptedEvent;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlAdaptedTask;
import seedu.address.storage.XmlSerializableDeskBoard;
import seedu.address.testutil.DeskBoardBuilder;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestUtil;

//@@author karenfrilya97
/**
 * Tests {@code XmlUtil} read and save methods
 */
public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validDeskBoard.xml");
    private static final File MISSING_TASK_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingTaskField.xml");
    private static final File INVALID_TASK_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidTaskField.xml");
    private static final File VALID_TASK_FILE = new File(TEST_DATA_FOLDER + "validTask.xml");
    private static final File MISSING_EVENT_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingEventField.xml");
    private static final File INVALID_EVENT_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidEventField.xml");
    private static final File VALID_EVENT_FILE = new File(TEST_DATA_FOLDER + "validEvent.xml");

    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempDeskBoard.xml"));

    private static final String INVALID_DATE_TIME = "9482asf424";
    private static final String INVALID_LOCATION = " michegan ave";

    private static final String VALID_TASK_NAME = "Hans Muster";
    private static final String VALID_TASK_DUE_DATE_TIME = "9482424";
    private static final String VALID_TASK_REMARK = "4th street";
    private static final List<XmlAdaptedTag> VALID_TASK_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    private static final String VALID_EVENT_NAME = "CIP";
    private static final String VALID_EVENT_START_DATE_TIME = "02/04/2018 08:00";
    private static final String VALID_EVENT_END_DATE_TIME = "02/04/2018 12:00";
    private static final String VALID_EVENT_LOCATION = "michegan ave";
    private static final List<XmlAdaptedTag> VALID_EVENT_TAGS = Collections.singletonList(new XmlAdaptedTag("CIP"));

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

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        DeskBoard dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableDeskBoard.class).toModelType();
        assertEquals(7, dataFromFile.getActivityList().size());
        assertEquals(3, dataFromFile.getTagList().size());
    }

    @Test
    public void xmlAdaptedTaskFromFile_fileWithMissingTaskField_validResult() throws Exception {
        XmlAdaptedTask actualTask = XmlUtil.getDataFromFile(
                MISSING_TASK_FIELD_FILE, XmlAdaptedTaskWithRootElement.class);
        XmlAdaptedTask expectedTask = new XmlAdaptedTask(
                null, VALID_TASK_DUE_DATE_TIME, VALID_TASK_REMARK, VALID_TASK_TAGS);
        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void xmlAdaptedTaskFromFile_fileWithInvalidTaskField_validResult() throws Exception {
        XmlAdaptedTask actualTask = XmlUtil.getDataFromFile(
                INVALID_TASK_FIELD_FILE, XmlAdaptedTaskWithRootElement.class);
        XmlAdaptedTask expectedTask = new XmlAdaptedTask(
                VALID_TASK_NAME, INVALID_DATE_TIME, VALID_TASK_REMARK, VALID_TASK_TAGS);
        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void xmlAdaptedTaskFromFile_fileWithValidTask_validResult() throws Exception {
        XmlAdaptedTask actualTask = XmlUtil.getDataFromFile(
                VALID_TASK_FILE, XmlAdaptedTaskWithRootElement.class);
        XmlAdaptedTask expectedTask = new XmlAdaptedTask(
                VALID_TASK_NAME, VALID_TASK_DUE_DATE_TIME, VALID_TASK_REMARK, VALID_TASK_TAGS);
        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithMissingEventField_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                MISSING_EVENT_FIELD_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_START_DATE_TIME,
                null, VALID_EVENT_LOCATION, null, VALID_EVENT_TAGS);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithInvalidEventField_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                INVALID_EVENT_FIELD_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_START_DATE_TIME,
                VALID_EVENT_END_DATE_TIME, INVALID_LOCATION, null, VALID_EVENT_TAGS);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithValidEvent_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                VALID_EVENT_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_START_DATE_TIME,
                VALID_EVENT_END_DATE_TIME, VALID_EVENT_LOCATION, null, VALID_EVENT_TAGS);
        assertEquals(expectedEvent, actualEvent);
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
     * to {@code XmlAdaptedTask} objects.
     */
    @XmlRootElement(name = "task")
    private static class XmlAdaptedTaskWithRootElement extends XmlAdaptedTask {}

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data
     * to {@code XmlAdaptedEvent} objects.
     */
    @XmlRootElement(name = "event")
    private static class XmlAdaptedEventWithRootElement extends XmlAdaptedEvent {}
}
