package seedu.recipe.storage;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import seedu.recipe.model.recipe.Image;

public class ImageStorageTest {
    private static final String DESTINATION_FOLDER = "data/images/";
    private static final String INVALID_IMAGE_PATH = "notvalid----";

    @Test
    public void saveImageFile() {
        ImageStorage.saveImageFile(Image.VALID_IMAGE_PATH, DESTINATION_FOLDER);
        File image = new File(DESTINATION_FOLDER + "clock.png");
        assertTrue(image.exists());
    }
}
