//@@author RyanAngJY
package seedu.recipe.model.recipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.CRC32;

import org.junit.Test;

import seedu.recipe.testutil.Assert;

public class ImageTest {
    private static final String INVALID_IMAGE_URL = "http://google.com";
    private static final String NOT_AN_IMAGE_PATH = "build.gradle";
    private static final String VALID_IMAGE_URL = "https://i.imgur.com/FhRsgCK.jpg";
    private static final long VALID_IMAGE_CRC = 2184062566L;

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Image(null));
    }

    @Test
    public void constructor_invalidImage_throwsIllegalArgumentException() {
        String invalidImage = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Image(invalidImage));
    }

    @Test
    public void isValidImage() {
        // blank image
        assertFalse(Image.isValidImage("")); // empty string
        assertFalse(Image.isValidImage("   ")); // spaces only
        assertFalse(Image.isValidImage("\t\n\t\r\n"));

        // invalid image
        assertFalse(Image.isValidImage("estsed")); //random string

        // valid image path
        assertTrue(Image.isValidImage(Image.NULL_IMAGE_REFERENCE));
        assertTrue(Image.isValidImage(Image.VALID_IMAGE_PATH));
        // invalid image path
        assertFalse(Image.isValidImage("ZZZ://ZZZ!!@@#"));
        // not an image
        assertFalse(Image.isValidImage(NOT_AN_IMAGE_PATH));

        // valid image url
        assertTrue(Image.isValidImage(VALID_IMAGE_URL));
        // invalid image url
        assertFalse(Image.isValidImage(INVALID_IMAGE_URL));
    }

    //@@author kokonguyen191
    @Test
    public void downloadImage_invalidUrl_throwsAssertionError() {
        Assert.assertThrows(AssertionError.class, () -> Image.downloadImage(INVALID_IMAGE_URL));
    }

    @Test
    public void downloadImage_validUrl_returnsImageName() throws Exception {
        String fileName = Image.downloadImage(VALID_IMAGE_URL);
        File file = new File(fileName);
        assertTrue(file.exists());

        InputStream in = new FileInputStream(file);
        CRC32 crc32 = new CRC32();
        byte[] buffer = new byte[1000];
        int bytes;
        while ((bytes = in.read(buffer)) != -1) {
            crc32.update(buffer, 0, bytes);
        }
        long crc = crc32.getValue();
        in.close();

        assertEquals(VALID_IMAGE_CRC, crc);

        file.delete();
    }

    @Test
    public void setImageToInternalReference() {
        Image imageStub = new Image(Image.VALID_IMAGE_PATH);
        imageStub.setImageToInternalReference();
        assertTrue(imageStub.toString().equals(Image.IMAGE_STORAGE_FOLDER + imageStub.getImageName()));
    }
}
//@@author
