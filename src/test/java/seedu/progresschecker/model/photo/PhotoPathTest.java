package seedu.progresschecker.model.photo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.progresschecker.testutil.Assert;

//@@author Livian1107
public class PhotoPathTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PhotoPath(null));
    }

    @Test
    public void isValidPhotoPath() {
        // null photo path
        Assert.assertThrows(NullPointerException.class, () -> PhotoPath.isValidPhotoPath(null));

        // blank photo path
        assertFalse(PhotoPath.isValidPhotoPath(" ")); // spaces only

        // invalid starting
        assertFalse(PhotoPath.isValidPhotoPath("src/image.jpg")); // missing parent path
        assertFalse(PhotoPath.isValidPhotoPath("src/main/image.jpg")); // missing parent path
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/image.jpg")); // missing parent path
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/images/image.jpg")); // missing parent path

        // invalid file extension
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/images/contact/image.psd"));
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/images/contact/image.gif"));

        // valid photo path
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contact/image.jpg"));
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contact/image.jpeg"));
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contact/image.png"));
        assertTrue(PhotoPath.isValidPhotoPath("")); // empty path
    }
}
