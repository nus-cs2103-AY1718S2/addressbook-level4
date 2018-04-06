//@@author RyanAngJY
package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.net.URL;

import seedu.recipe.MainApp;

/**
 * Represents a Recipe's image in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidImage(String)}
 */
public class Image {

    public static final String NULL_IMAGE_REFERENCE = "-";
    public static final String FILE_PREFIX = "file:";
    public static final String IMAGE_STORAGE_FOLDER = "data/images/";
    public static final String MESSAGE_IMAGE_CONSTRAINTS = "Image path should be valid";
    public static final URL VALID_IMAGE = MainApp.class.getResource("/images/clock.png");
    public static final String VALID_IMAGE_PATH = VALID_IMAGE.toExternalForm().substring(5);
    private String value;
    private String imageName;

    /**
     * Constructs a {@code Image}.
     *
     * @param imagePath A valid file path.
     */
    public Image(String imagePath) {
        requireNonNull(imagePath);
        checkArgument(isValidImage(imagePath), MESSAGE_IMAGE_CONSTRAINTS);
        this.value = imagePath;
        setImageName();
    }

    /**
     * Sets the name of the image file
     */
    public void setImageName() {
        if (this.value.equals(NULL_IMAGE_REFERENCE)) {
            imageName = NULL_IMAGE_REFERENCE;
        } else {
            this.imageName = new File(this.value).getName();
        }
    }

    public String getImageName() {
        return imageName;
    }

    /**
     *  Returns true if a given string is a valid file path, or no file path has been assigned
     */
    public static boolean isValidImage(String testImagePath) {
        if (testImagePath.equals(NULL_IMAGE_REFERENCE)) {
            return true;
        }
        File image = new File(testImagePath);
        if (image.exists() && !image.isDirectory()) {
            return true;
        }
        return false;
    }

    /**
     * Sets image path to follow internal image storage folder
     */
    public void setImageToInternalReference() {
        if (!imageName.equals(NULL_IMAGE_REFERENCE)) {
            this.value = IMAGE_STORAGE_FOLDER + imageName;
        }
    }

    public String getUsablePath() {
        return FILE_PREFIX + value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Image // instanceof handles nulls
                && this.value.equals(((Image) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
//@@author
