package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;

import javafx.scene.image.Image;

/**
 * Represents a ProfilePicture in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ProfilePicture {
    public static final String MESSAGE_PROFILEPICTURE_CONSTRAINTS =
            "Profile picture name should be a valid image name,"
                    + " and it should end with either jpeg, jpg, png, gif or bmp";
    public static final String MESSAGE_PROFILEPICTURE_NOT_EXISTS =
            "Profile picture does not exists. Please give another profile picture";

    // alphanumeric and special characters
    public static final String PROFILE_PICTURE_VALIDATION_REGEX = "^$|([^\\s]+(\\.(?i)(jpeg|jpg|png|gif|bmp))$)";

    private static final String defaultImgPath = "./src/test/data/images/default.png";
    private static final String defaultImgURL = "file:src/test/data/images/default.png";
    private static final String profilePictureFolder =
            "./src/main/resources/ProfilePictures/";

    private static int id = 0;

    public final String value;
    public final String path;

    /**
     * Constructs an {@code Email}.
     *
     * @param profilePicture A valid image path.
     */
    public ProfilePicture(String... profilePicture) {
        if (profilePicture.length != 0 && profilePicture[0] != null) {
            checkArgument(isValidProfilePicture(profilePicture[0]), MESSAGE_PROFILEPICTURE_CONSTRAINTS);
            checkArgument(hasValidProfilePicture(profilePicture[0]), MESSAGE_PROFILEPICTURE_NOT_EXISTS);

            this.value = profilePicture[0];
            String temp = new String();
            try {
                temp = new File(copyImageToProfilePictureFolder(profilePicture[0])).toURI().toURL().toExternalForm();
            } catch (MalformedURLException e) {
                temp = defaultImgURL;
            } finally {
                this.path = temp;
            }
        } else {
            this.value = defaultImgPath;
            this.path = defaultImgURL;
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidProfilePicture(String test) {
        return test.matches(PROFILE_PICTURE_VALIDATION_REGEX);
    }

    /**
     * Returns if there exists profile picture.
     * @param profilePicture
     * @return
     */
    public static boolean hasValidProfilePicture(String profilePicture) {
        File file = new File(profilePicture);
        return file.exists() && !file.isDirectory();
    }

    public Image getImage() {
        return new Image(path);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePicture // instanceof handles nulls
                && this.value.equals(((ProfilePicture) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * copy the image from the absolute path to the Profile Picture Folder
     * @param profilePicture
     * @return destination path
     */
    private String copyImageToProfilePictureFolder(String profilePicture) {
        String destPath = "";
        try {
            File source = new File(profilePicture);
            String fileExtension = extractFileExtension(profilePicture);
            destPath = profilePictureFolder.concat(Integer.toString(id).concat(".").concat(fileExtension));
            File dest = new File(destPath);
            Files.copy(source.toPath(), dest.toPath());
            id++;
        } catch (IOException e) {
            // Exception will not happen as the profile picture path has been check through hasValidProfilePicture
        }
        return destPath;
    }

    /**
     * extract FileExtension from fileName
     * @param fileName
     * @return fileExtension
     */
    private String extractFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }
}
