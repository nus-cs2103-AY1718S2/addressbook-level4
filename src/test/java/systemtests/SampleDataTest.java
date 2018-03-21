package systemtests;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//import org.junit.Test;

import seedu.address.model.DeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends RemarkBookSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected DeskBoard getInitialData() {
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

    //TODO: TEST
    /**
     * Test
     */
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Activity[] expectedList = SampleDataUtil.getSampleActivity();
        assertListMatching(getPersonListPanel(), expectedList);
    }
}
