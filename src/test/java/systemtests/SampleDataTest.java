package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.ptman.model.Model.PREDICATE_SHOW_ALL_SHIFTS;
import static seedu.ptman.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.ptman.model.Model;
import seedu.ptman.model.PartTimeManager;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.shift.Shift;
import seedu.ptman.model.util.SampleDataUtil;
import seedu.ptman.testutil.TestUtil;

public class SampleDataTest extends PartTimeManagerSystemTest {

    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected PartTimeManager getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected String getDataFileLocation() {
        String filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void partTimeManager_dataFileDoesNotExist_loadSampleData() {
        Employee[] expectedEmployeeList = SampleDataUtil.getSampleEmployees();
        assertListMatching(getEmployeeListPanel(), expectedEmployeeList);

        Shift[] expectedShiftList = SampleDataUtil.getSampleShifts();
        Model model = getModel();
        model.updateFilteredShiftList(PREDICATE_SHOW_ALL_SHIFTS);
        ObservableList<Shift> actualShiftList = model.getFilteredShiftList();
        assertEquals(expectedShiftList, actualShiftList.toArray());
    }
}
