package seedu.organizer.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.organizer.model.Organizer;
import seedu.organizer.storage.XmlAdaptedSubtask;
import seedu.organizer.storage.XmlAdaptedTag;
import seedu.organizer.storage.XmlAdaptedTask;
import seedu.organizer.storage.XmlSerializableOrganizer;
import seedu.organizer.testutil.OrganizerBuilder;
import seedu.organizer.testutil.TaskBuilder;
import seedu.organizer.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validOrganizer.xml");
    private static final File MISSING_TASK_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingTaskField.xml");
    private static final File INVALID_TASK_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidTaskField.xml");
    private static final File VALID_TASK_FILE = new File(TEST_DATA_FOLDER + "validTask.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempOrganizer.xml"));

    private static final String INVALID_PRIORITY = "9!@#";

    private static final String VALID_NAME = "Work on PrioriTask";
    private static final String VALID_PRIORITY = "9";
    private static final String VALID_DEADLINE = "2018-07-16";
    private static final String VALID_DATEADDED = LocalDate.now().toString();
    private static final String VALID_DATECOMPLETED = "not completed";
    private static final String VALID_DESCRIPTION = "Refactor Address to Description";
    private static final Boolean VALID_STATUS = null;
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));
    private static final List<XmlAdaptedSubtask> VALID_SUBTASKS = Collections.singletonList(new XmlAdaptedSubtask(
            "Find some friends to play dota or csgo", false));
    //temporary fix for xml file bug due to PrioriTask's dependence on the current date
    private static final String current_date = "current_date";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, Organizer.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, Organizer.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, Organizer.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        Organizer dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableOrganizer.class).toModelType();
        assertEquals(9, dataFromFile.getTaskList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void xmlAdaptedTaskFromFile_fileWithMissingTaskField_validResult() throws Exception {
        XmlAdaptedTask actualTask = XmlUtil.getDataFromFile(
                MISSING_TASK_FIELD_FILE, XmlAdaptedTaskWithRootElement.class);
        XmlAdaptedTask expectedTask = new XmlAdaptedTask(
                null, VALID_PRIORITY, VALID_DEADLINE, current_date, VALID_DATECOMPLETED,
                VALID_DESCRIPTION, VALID_STATUS, VALID_TAGS, VALID_SUBTASKS);
        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void xmlAdaptedTaskFromFile_fileWithInvalidTaskField_validResult() throws Exception {
        XmlAdaptedTask actualTask = XmlUtil.getDataFromFile(
                INVALID_TASK_FIELD_FILE, XmlAdaptedTaskWithRootElement.class);
        XmlAdaptedTask expectedTask = new XmlAdaptedTask(
                VALID_NAME, INVALID_PRIORITY, VALID_DEADLINE, current_date, VALID_DATECOMPLETED,
                VALID_DESCRIPTION, VALID_STATUS, VALID_TAGS, VALID_SUBTASKS);
        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void xmlAdaptedTaskFromFile_fileWithValidTask_validResult() throws Exception {
        XmlAdaptedTask actualTask = XmlUtil.getDataFromFile(
                VALID_TASK_FILE, XmlAdaptedTaskWithRootElement.class);
        XmlAdaptedTask expectedTask = new XmlAdaptedTask(
                VALID_NAME, VALID_PRIORITY, VALID_DEADLINE, current_date, VALID_DATECOMPLETED,
                VALID_DESCRIPTION, VALID_STATUS, VALID_TAGS, VALID_SUBTASKS);
        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new Organizer());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new Organizer());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableOrganizer dataToWrite = new XmlSerializableOrganizer(new Organizer());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableOrganizer dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableOrganizer.class);
        assertEquals(dataToWrite, dataFromFile);

        OrganizerBuilder builder = new OrganizerBuilder(new Organizer());
        dataToWrite = new XmlSerializableOrganizer(
                builder.withTask(new TaskBuilder().build()).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableOrganizer.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedTask}
     * objects.
     */
    @XmlRootElement(name = "task")
    private static class XmlAdaptedTaskWithRootElement extends XmlAdaptedTask {
    }
}
