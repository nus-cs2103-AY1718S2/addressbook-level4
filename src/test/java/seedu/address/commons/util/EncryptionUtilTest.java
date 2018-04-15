package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TestUtil;

//@@author limzk1994
public class EncryptionUtilTest {

    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempFileTest.txt"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //test that decryption works
    @Test
    public void encryptDecryptResult() throws Exception {
        TEMP_FILE.createNewFile();
        String dataToWrite = "This is the string to write";
        FileWriter fileWriter = new FileWriter(TEMP_FILE);
        fileWriter.write(dataToWrite);
        fileWriter.close();

        EncryptionUtil.encrypt(TEMP_FILE);
        EncryptionUtil.decrypt(TEMP_FILE);

        Scanner fromFile = new Scanner(TEMP_FILE);
        String dataToRead = fromFile.nextLine();
        fromFile.close();

        assertEquals(dataToWrite, dataToRead);
    }

    // test that encryption works
    @Test
    public void encryptResultNotEqual() throws Exception {
        TEMP_FILE.createNewFile();
        String dataToWrite = "This is the string to write";
        FileWriter fileWriter = new FileWriter(TEMP_FILE);
        fileWriter.write(dataToWrite);
        fileWriter.close();

        EncryptionUtil.encrypt(TEMP_FILE);

        Scanner fromFile = new Scanner(TEMP_FILE);
        String dataToRead = null;
        if (fromFile.hasNext()) {
            dataToRead = fromFile.nextLine();
            fromFile.close();
        }

        assertNotEquals(dataToWrite, dataToRead);
    }
}
