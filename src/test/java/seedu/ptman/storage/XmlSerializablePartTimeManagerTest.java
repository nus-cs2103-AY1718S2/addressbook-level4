package seedu.ptman.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ptman.commons.exceptions.IllegalValueException;
import seedu.ptman.commons.util.FileUtil;
import seedu.ptman.commons.util.XmlUtil;
import seedu.ptman.model.PartTimeManager;
import seedu.ptman.testutil.TypicalEmployees;

public class XmlSerializablePartTimeManagerTest {

    private static final String TEST_DATA_FOLDER =
            FileUtil.getPath("src/test/data/XmlSerializablePartTimeManagerTest/");
    private static final File TYPICAL_EMPLOYEES_FILE =
            new File(TEST_DATA_FOLDER + "typicalEmployeesPartTimeManager.xml");
    private static final File INVALID_EMPLOYEE_FILE =
            new File(TEST_DATA_FOLDER + "invalidEmployeePartTimeManager.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagPartTimeManager.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalEmployeesFile_success() throws Exception {
        XmlSerializablePartTimeManager dataFromFile = XmlUtil.getDataFromFile(TYPICAL_EMPLOYEES_FILE,
                XmlSerializablePartTimeManager.class);
        PartTimeManager partTimeManagerFromFile = dataFromFile.toModelType();
        assertEquals(partTimeManagerFromFile.getEmployeeList(), TypicalEmployees.getTypicalPartTimeManager()
                .getEmployeeList());
    }

    @Test
    public void toModelType_invalidEmployeeFile_throwsIllegalValueException() throws Exception {
        XmlSerializablePartTimeManager dataFromFile = XmlUtil.getDataFromFile(INVALID_EMPLOYEE_FILE,
                XmlSerializablePartTimeManager.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializablePartTimeManager dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializablePartTimeManager.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void equals() throws Exception {
        XmlSerializablePartTimeManager object = XmlUtil.getDataFromFile(TYPICAL_EMPLOYEES_FILE,
                XmlSerializablePartTimeManager.class);
        XmlSerializablePartTimeManager other = XmlUtil.getDataFromFile(TYPICAL_EMPLOYEES_FILE,
                XmlSerializablePartTimeManager.class);
        assertEquals(object, other);
        assertTrue(object.equals(object));
        assertFalse(object.equals(null));
        assertFalse(object.equals(1));
    }
}
