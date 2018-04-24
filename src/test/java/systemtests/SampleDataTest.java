package systemtests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
//import java.util.ArrayList;
import java.util.Arrays;

//import org.junit.Test;

import seedu.address.model.DeskBoard;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends DeskBoardSystemTest {
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

    //TODO:TEST

    /**
     * Test
     */
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Activity[] expectedList = SampleDataUtil.getSampleActivity();
        ReadOnlyDeskBoard expectedDeskBoard = SampleDataUtil.getSampleDeskBoard();
        DeskBoard generatedDeskBoard = new DeskBoard();
        try {
            generatedDeskBoard.setActivities(Arrays.asList(expectedList));
        } catch (DuplicateActivityException e) {
            fail();
        }

        assertTrue(expectedDeskBoard.equals(generatedDeskBoard));
        //TODO: After ui part finished
        //assertListMatching(getPersonListPanel(), expectedList);
    }
}
