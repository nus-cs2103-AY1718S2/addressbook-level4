package seedu.address.model.student.MiscellaneousInfo;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProfilePicturePath {

    private final Path profilePicturePath;

    public static final String MESSAGE_PICTURE_CONSTRAINTS =
            "File URL must exist and have extensions of '.jpg' or '.png' only.";
    public static final String DEFAULT_PROFILE_PICTURE =
            "out/production/resources/images/profile_photo_placeholder.png";

    public static final String INVALID_PICTURE_URL = "The URL entered for the picture is invalid or corrupted";

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
    public static boolean isValidUrl(String test) {
        File testFile = new File(test);
        if (!testFile.exists()) {
            return false;
        }
        return ProfilePicturePath.checkPictureExtension(testFile.getPath());
    }

    public Path getProfilePicturePath() {
        return profilePicturePath;
    }
}
