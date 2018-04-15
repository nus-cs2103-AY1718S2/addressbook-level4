package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileUtilTest extends FileUtil {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getPath() {

        // valid case
        assertEquals("folder" + File.separator + "sub-folder", FileUtil.getPath("folder/sub-folder"));

        // null parameter -> throws NullPointerException
        thrown.expect(NullPointerException.class);
        FileUtil.getPath(null);

        // no forwards slash -> assertion failure
        thrown.expect(AssertionError.class);
        FileUtil.getPath("folder");
    }

    @Test
    public void createFile() throws Exception {
        FileUtil.createFile(new File("test.txt"));
        assertFalse(FileUtil.createFile(new File("test.txt")));
        //cleanup the file created for test
        File file = new File("test.txt");
        file.delete();
    }
}
