package seedu.address.model.photo;
//@@author crizyli
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhotoTest {

    @Test
    public void construct_success() {
        Photo photo = new Photo("test.jpg");
        assertEquals(photo.getName(), "test.jpg");
        assertEquals(photo.toString(), Photo.DEFAULT_PHOTO_FOLDER + "test.jpg");
    }

    @Test
    public void invalidPhotoName_throwsIllegalArgumentException() {
        assertFalse(Photo.isValidPhotoName(""));
        assertFalse(Photo.isValidPhotoName("test"));
        assertFalse(Photo.isValidPhotoName("test.txt"));
        assertFalse(Photo.isValidPhotoName("test.pdf"));
    }

    @Test
    public void equals() {
        Photo firstPhoto = new Photo("test.jpg");
        Photo secondPhoto = new Photo("test2.jpg");

        // same object -> returns true
        assertTrue(firstPhoto.equals(firstPhoto));

        // different types -> returns false
        assertFalse(firstPhoto.equals(1));

        // null -> returns false
        assertFalse(firstPhoto.equals(null));

        // same value -> returns true
        Photo thirdPhoto = new Photo("test.jpg");
        assertTrue(firstPhoto.equals(thirdPhoto));

        // different value -> returns false
        assertFalse(firstPhoto.equals(secondPhoto));

    }

}
