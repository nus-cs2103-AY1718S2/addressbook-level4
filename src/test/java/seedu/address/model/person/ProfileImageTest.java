package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

//@@author Ang-YC
public class ProfileImageTest {
    private static final String TEST_DATA_FOLDER = "src/test/data/ProfileImageTest/";

    @Test
    public void constructor_null_constructionSuccessValueEmptyString() {
        assertNull(new ProfileImage(null).value);
    }

    @Test
    public void constructor_invalidProfileImage_throwsIllegalArgumentException() {
        String invalidProfileImage = formFilePath("");
        assertThrows(IllegalArgumentException.class, () -> new ProfileImage(invalidProfileImage));
    }

    @Test
    public void constructor_validProfileImage_constructionSuccess() {
        String validProfileImage = formFilePath("gates.jpg");
        assertEquals(validProfileImage, new ProfileImage(validProfileImage).value);
    }

    private String formFilePath(String fileName) {
        return TEST_DATA_FOLDER + fileName;
    }

    @Test
    public void isValidFile() {
        // Null profile image
        assertThrows(NullPointerException.class, () -> ProfileImage.isValidFile(null));

        // Invalid image file name
        assertFalse(ProfileImage.isValidFile(formFilePath(""))); // empty string
        assertFalse(ProfileImage.isValidFile(formFilePath(" "))); // spaces only
        assertFalse(ProfileImage.isValidFile(formFilePath("fileNotFound.jpg"))); // not a existing image
        assertFalse(ProfileImage.isValidFile(formFilePath("largeDora.pdf"))); // greater than 1MB

        // Valid image file name
        assertTrue(ProfileImage.isValidFile(formFilePath("doraemon cute.jpg"))); // spaces within fileName
        assertTrue(ProfileImage.isValidFile(formFilePath("elon.jpg")));
        assertTrue(ProfileImage.isValidFile(formFilePath("gates.jpg")));
        assertTrue(ProfileImage.isValidFile(formFilePath("jobs.jpg")));
        assertTrue(ProfileImage.isValidFile(formFilePath("larry.jpg")));
        assertTrue(ProfileImage.isValidFile(formFilePath("mark.jpg")));

    }

}
