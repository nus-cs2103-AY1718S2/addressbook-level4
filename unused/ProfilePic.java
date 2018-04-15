package seedu.address.model.person;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

//@@author Sebry9-unused
/**
 * Represents a Person's Profile picture in reInsurance.
 * Guarantees: immutability and validity.
 * Unused reason: Group deem that it is not significant addition to the application and decided to cease it due to
 * deadline being too tighted.
 */
public class ProfilePic {
    public static final String DEFAULT_PHOTO ="/images/default.png";

    private static final Logger logger = LogsCenter.getLogger(ProfilePic.class);
    private static final String MESSAGE_PHOTO_CONSTRAINTS = "Profile Picture: "
        + "specified file does not exist.";
    private static final String MESSAGE_PHOTO_COPY_ERROR = "Error copying photo to reInsurance's data directory";
    private static final String DEFAULT_SAVE_DIR = "data" + File.separator;
    private static final String ALLOWED_TYPE_JPG = ".jpg";
    private static final String ALLOWED_TYPE_JPEG = ".jpeg";
    private static final String ALLOWED_TYPE_PNG = ".png";
    private static final int MAX_SIZE = 1000000; // Sets allowable maximum profile picture size to be 1MB

    public final String picture;
    /**
     * Validates given Profile picture.
     *
     * @throws IllegalValueException if given profile picture string is invalid.
     */
    public ProfilePic(String filePath) throws IllegalValueException {
        if (filePath == null || filePath.isEmpty()) {
            this.picture = null;
        } else if (isDefaultPhoto(filePath)) {
            this.picture = filePath;
        } else {
            String trimmedPhotoPath = filePath.trim();
            if (!isValidPhoto(trimmedPhotoPath)) {
                throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
            } else {
                File from = new File(trimmedPhotoPath);
                this.picture = DEFAULT_SAVE_DIR + from.getName();
                Path to = Paths.get(this.picture);
                copyPhotoToDefaultDir(from.toPath(), to);
            }
        }
    }

    /**
     * Return if string give is valid and within size limits
     */
    private static boolean isValidPhoto(String test) {
        File file = new File(test);
        return file.exists()
            && file.length() <= MAX_SIZE
            && (test.endsWith(ALLOWED_TYPE_JPG)
            || test.endsWith(ALLOWED_TYPE_JPEG)
            || test.endsWith(ALLOWED_TYPE_PNG));
    }

    /**
     * Return path to default photo
     */
    private static boolean isDefaultPhoto(String filePath) {
        return filePath.equals(DEFAULT_PHOTO);
    }

    /**
     * Copies the photo from Path {@code from} to Path {@code to}
     * @throws IllegalValueException
     */
    private static void copyPhotoToDefaultDir(Path from, Path to) throws IllegalValueException {
        try {
            Files.createDirectories(Paths.get(DEFAULT_SAVE_DIR));
            Files.copy(from, to, REPLACE_EXISTING);
        } catch (IOException io) {
            logger.info("Display photo error: " + io.toString());
            throw new IllegalValueException(MESSAGE_PHOTO_COPY_ERROR);
        }
    }

    /**
     * Returns file path for user-specified display photos.
     */
    public String getAbsoluteFilePath() {
        if (isDefaultPhoto(picture)) {
            return picture;
        } else {
            return "file://" + Paths.get(picture).toAbsolutePath().toUri().getPath();
        }
    }

    @Override
    public String toString() {
        return picture;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ProfilePic // instanceof handles nulls
            && this.picture.equals(((ProfilePic) other).picture)); // state check
    }

    @Override
    public int hashCode() {
        return picture.hashCode();
    }

}



