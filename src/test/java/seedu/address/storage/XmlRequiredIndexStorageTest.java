package seedu.address.storage;


import static seedu.address.storage.XmlRequiredIndexStorage.updateData;

import java.io.FileNotFoundException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.util.FileUtil;

//@@author samuelloh
public class XmlRequiredIndexStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void saveRequiredIndexStorage_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        updateData(1, null);
    }

    @Test
    public void saveRequiredIndexStorage_invalidFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        updateData(1, "invalid");
    }

    @Test
    public void SaveRequiredIndexStorage_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempRequiredIndexStorage.xml";

    }



}
//@@author
