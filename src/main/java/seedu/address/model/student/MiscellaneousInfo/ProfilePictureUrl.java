package seedu.address.model.student.MiscellaneousInfo;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfilePictureUrl {

    private final URL profilePictureUrl;

    public static final String MESSAGE_PICTURE_CONSTRAINTS =
            "File URL must exist and have extensions of '.jpg' or '.png' only.";

    public ProfilePictureUrl (String url) throws MalformedURLException {
        requireNonNull(url);

        profilePictureUrl = new URL(url);
    }

    /**
     * Checks if file extension is either 'jpg' or 'png'
     * @param filePath
     * @return True if extensions are as above. False if otherwise
     */
    public static boolean checkPictureExtension(String filePath) {
        String extension;

        if(filePath.lastIndexOf(".") != -1 && filePath.lastIndexOf(".") != 0) {
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
        if(!testFile.exists()){
            return false;
        }
        return ProfilePictureUrl.checkPictureExtension(testFile.getPath());
    }
}
