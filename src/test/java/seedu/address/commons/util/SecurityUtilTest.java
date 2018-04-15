package seedu.address.commons.util;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
//@@author tinyjy94
public class SecurityUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/SecurityUtilTest/");
    private static final String password = "pass";
    private static final File plainFile = new File(TEST_DATA_FOLDER + "movieplanner.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void encrypt_nullOutputFile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        SecurityUtil.encrypt(plainFile, null, password);
    }

    @Test
    public void generateKey_nullPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        SecurityUtil.generateKey(null);
    }

}
