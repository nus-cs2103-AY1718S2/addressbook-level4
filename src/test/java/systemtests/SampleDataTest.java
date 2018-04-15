package systemtests;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.model.CoinBook;
import seedu.address.model.coin.Coin;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends CoinBookSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected CoinBook getInitialData() {
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
    public void coinBook_dataFileDoesNotExist_loadSampleData() {
        Coin[] expectedList = SampleDataUtil.getSampleCoins();
        assertListMatching(getCoinListPanel(), expectedList);
    }
}
