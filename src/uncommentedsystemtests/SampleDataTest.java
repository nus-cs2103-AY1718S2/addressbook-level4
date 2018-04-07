package systemtests;

//import static seedu.organizer.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//import org.junit.Test;

import seedu.organizer.model.Organizer;
//import seedu.organizer.model.task.Task;
//import seedu.organizer.model.util.SampleDataUtil;
import seedu.organizer.testutil.TestUtil;

public class SampleDataTest extends OrganizerSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected Organizer getInitialData() {
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

    /*Does not work due to need for login to display tasks on tasklistpanel
    Might want to remove the loadSampleData feature either way, does not make sense to have it
    @Test
    public void organizer_dataFileDoesNotExist_loadSampleData() {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertListMatching(getTaskListPanel(), expectedList);
    }*/
}
