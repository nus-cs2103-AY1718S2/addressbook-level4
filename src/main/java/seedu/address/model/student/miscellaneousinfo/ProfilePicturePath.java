package seedu.address.model.student.miscellaneousinfo;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a Student's profile picture's pathname in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class ProfilePicturePath {

    public static final String MESSAGE_PICTURE_CONSTRAINTS =
            "File URL must exist and have extensions of '.jpg' or '.png' only.";
    public static final String DEFAULT_PROFILE_PICTURE =
            "profile_photo_placeholder.png";

    public final Path profilePicturePath;

    public ProfilePicturePath(String filePath) {
        requireNonNull(filePath);
        profilePicturePath = Paths.get(filePath);
    }

    /**
     * Checks if file extension is either 'jpg' or 'png'
     *
     * @param filePath
     * @return True if extensions are as above. False if otherwise
     */
    public static boolean checkPictureExtension(String filePath) {
        String extension;

        if (filePath.lastIndexOf(".") != -1 && filePath.lastIndexOf(".") != 0) {
            extension = filePath.substring(filePath.lastIndexOf(".") + 1);
            return extension.equals("jpg") || extension.equals("png");
        }

        return false;

    }

    /**
     * Returns true if a given string is a valid file path with extensions either '.jpg' or '.png'.
     */
    public static boolean isValidPath(String test) {
        File testFile = new File(test);
        if (!testFile.exists()) {
            return false;
        }
        return ProfilePicturePath.checkPictureExtension(testFile.getPath());
    }

    public Path getProfilePicturePath() {
        return profilePicturePath;
    }

    /**
     * Returns the extension of the profile picture path.
     */
    public String getExtension() {
        int extensionSeparator = profilePicturePath.toString().lastIndexOf(".");
        return profilePicturePath.toString().substring(extensionSeparator);
    }

    @Override
    public String toString() {
        return profilePicturePath.toString();
    }
}
