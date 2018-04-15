package seedu.progresschecker.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileUtilTest {

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
    public void isValidImageFile() {

        final String validImage1 = "image.jpg";
        final String validImage2 = "image.jpeg";
        final String validImage3 = "image.png";
        final String invalidImage = "image.gif";

        // valid case
        assertTrue(FileUtil.isValidImageFile(validImage1));
        assertTrue(FileUtil.isValidImageFile(validImage2));
        assertTrue(FileUtil.isValidImageFile(validImage3));

        // invalid case
        assertFalse(FileUtil.isValidImageFile(invalidImage));
    }

    @Test
    public void isUnderFolder() {

        final String path = "ParentPath/Path/file.jpg";
        final String validParentPath = "ParentPath";
        final String invalidParentPath = "INVALID_PATH";

        // valid case
        assertTrue(FileUtil.isUnderFolder(path, validParentPath));

        // invalid case
        assertFalse(FileUtil.isUnderFolder(path, invalidParentPath));
    }

    @Test
    public void getFileExtension() {

        // valid case
        assertEquals(".jpg", FileUtil.getFileExtension("file.jpg"));

        // null parameter -> throws NullPointerException
        thrown.expect(NullPointerException.class);
        FileUtil.getFileExtension(null);
    }
}
