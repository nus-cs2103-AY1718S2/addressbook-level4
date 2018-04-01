package seedu.progresschecker.model.photo;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.FileUtil.isUnderFolder;
import static seedu.progresschecker.commons.util.FileUtil.isValidImageFile;

import seedu.progresschecker.commons.exceptions.IllegalValueException;

/**
 * Represents a Path of Photo in ProgressChecker
 */
public class PhotoPath {

    public static final String PHOTO_SAVED_PATH = "src/main/resources/images/contact/";
    public static final String MESSAGE_PHOTOPATH_CONSTRAINTS =
            "The path of the profile photo should start with '" + PHOTO_SAVED_PATH
                    + "'. The extensions of the file to upload should be 'jpg', 'jpeg' or 'png'.";

    public final String value;

    /**
     * Builds the path of profile photo in the ProgressChecker
     * Validates the given String of path
     * @param path is the String of the profile photo path
     * @trhows IllegalValueException if the String violates the constraints of photo path
     */
    public PhotoPath(String path) throws IllegalValueException {
        requireNonNull(path);
        if (isValidPhotoPath(path)){
            this.value = path;
        } else {
            throw new IllegalValueException(MESSAGE_PHOTOPATH_CONSTRAINTS);
        }
    }

    /**
     * Validates the given photo path
     */
    public boolean isValidPhotoPath (String path) {
        if (path.isEmpty()){ //empty path
            return true;
        }
        boolean isValidImage = isValidImageFile(path);
        boolean isUnderFolder = isUnderFolder(path, PHOTO_SAVED_PATH);
        return isValidImage && isUnderFolder;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhotoPath // instanceof handles nulls
                && this.value.equals(((PhotoPath) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
