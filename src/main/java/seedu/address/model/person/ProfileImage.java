package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;

import javafx.scene.image.Image;

//@@author Ang-YC
/**
 * Represents a Person's profile image in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFile(String)}
 */
public class ProfileImage {
    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "Profile image file should be at least 1 character long, exist in the same directory "
                    + "as the jar executable, smaller than 1MB and readable";
    private static final int ONEMEGABYTE = 1 * 1024 * 1024;
    private static final String IMAGE_VALIDATION_REGEX = ".*\\S.*";
    public final String value;
    public final String userInput;
    private boolean isHashed;

    /**
     * Constructs a {@code ProfileImage}.
     *
     * @param fileName A valid fileName.
     */
    public ProfileImage(String fileName) {
        isHashed = false;
        if (isNull(fileName)) {
            this.value = null;
            this.userInput = null;
        } else {
            checkArgument(isValidFile(fileName), MESSAGE_IMAGE_CONSTRAINTS);
            this.value = fileName;
            userInput = fileName;
        }
    }

    public ProfileImage(String storageFileName, String userFileName) {
        isHashed = true;
        if (isNull(storageFileName)) {
            this.value = null;
            this.userInput = null;
        } else {
            checkArgument(isValidFile(storageFileName), MESSAGE_IMAGE_CONSTRAINTS);
            this.value = storageFileName;
            userInput = userFileName;
        }
    }
    /**
     * Return the loaded {@code Image} of the person's Profile Image,
     * resized to 100px for performance issue
     * @return the image in {@code Image}
     */
    public Image getImage() {
        try {
            return new Image(getFile().toURI().toString(),
                    100d, 0d, true, true, false);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return the {@code File} of the image
     * @return the image in {@code File}
     */
    private File getFile() {
        if (this.value == null) {
            return null;
        }
        return getFileFromPath(this.value);
    }

    /**
     * Return the {@code File} representation of the path
     * @param path of the image
     * @return the {@code File} representation
     */
    private static File getFileFromPath(String path) {
        String userDir = System.getProperty("user.dir");
        return new File(userDir + File.separator + path);
    }

    /**
     * Returns true if a given string is a valid file path,
     * however it doesn't validate if it is a valid image file
     * due to there are too many different image types
     */
    public static boolean isValidFile(String test) {
        requireNonNull(test);

        if (!test.matches(IMAGE_VALIDATION_REGEX)) {
            return false;
        }

        File imageFile = getFileFromPath(test);

        if (imageFile.isDirectory() || !imageFile.exists() || imageFile.length() > ONEMEGABYTE) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isHashed() {
        return isHashed;
    }

    @Override
    public String toString() {
        return userInput;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof ProfileImage // instanceof handles nulls
                && ((this.value == null && ((ProfileImage) other).value == null) //both value are null
                || (isHashed && ((ProfileImage) other).isHashed) ? isHashEqual(this.value, ((ProfileImage) other).value)
                : this.userInput.equals(((ProfileImage) other).userInput))); // state check
    }
    /**
     * Checks whether the hash of two resume are the same
     * @param first resume
     * @param second resume
     * @return same as true or false otherwise
     */
    private boolean isHashEqual(String first, String second) {
        assert(first.split("_").length == 2);
        String firstHash = first.split("_")[1];
        String secondHash = second.split("_")[1];
        return firstHash.equals(secondHash);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
