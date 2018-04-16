package seedu.address.model.person;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.nio.file.Files;
import javax.imageio.ImageIO;

//@@author dezhanglee
/**
 * Represents a Picture in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Picture {

    public static final String DEFAULT_PATH = "images/default.png";
    public static final String MESSAGE_PICTURE_CONSTRAINTS =
            "Filepath must be valid, point to an image file, and is less than 10MB in size";
    public static final String PICTURE_VALIDATION_REGEX_EXT = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9._-]+)+\\\\?";
    public static final String PICTURE_VALIDATION_REGEX_INT = "[^\\s].*";
    public static final String APPDATA_DIR = getDefaultDirectory();
    public static final String FOLDER = APPDATA_DIR + "/AddressBook";
    private static final String URL_PREFIX = "file:/";

    private static final int FIVE_MB_IN_BYTES = 5242880;

    private String path;

    /**
     * Default initializer, uses default picture
     */
    public Picture() {

        this.path = DEFAULT_PATH;
    }

    /**
     * initializer if path pointing to pic is specified. For now, only called by XmlAdaptedPerson class
     * Since its only called by XmlAdaptedPerson (which saves the picture filepaths based on last successful state),
     * arguments are correct and hence no need to call checkArgument on {@code path}
     * @param path
     */
    public Picture(String path) {

        requireNonNull(path);
        String p = truncateFilePrefix(path);
        //if invalid/outdated copy of picture in XMLAdaptedPerson, reset to default pic.
        if (isValidPath(p)) {
            this.path = path;
        } else {
            this.path = DEFAULT_PATH;
        }
    }

    /**
     * initializer if path, and image name of new picture is specified
     *
     * @param path
     * @param newPictureName
     */
    public Picture(String path, String newPictureName) {

        requireNonNull(path);
        checkArgument(isValidPath(path), MESSAGE_PICTURE_CONSTRAINTS);
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }
        this.path = FOLDER + "//"  + newPictureName + "." + extension;

        createNewPicture(path, newPictureName + "." + extension);

    }

    /**
     * Check if path is valid
     *
     * @param path
     * @return
     */
    public static boolean isValidPath(String path) {

        if (path.equals(DEFAULT_PATH)) {
            return true;
        }

        boolean isValidExternalPath = path.matches(PICTURE_VALIDATION_REGEX_EXT);
        boolean isValidInternalPath = path.matches(PICTURE_VALIDATION_REGEX_INT);

        if (isValidExternalPath || isValidInternalPath) {
            return isValidPicture(path);
        }
        return false;
    }

    /**
     * Checks if the image pointed to by the path is indeed a valid image
     *
     * @param path
     * @return
     */
    public static boolean isValidPicture(String path) {

        File f = new File(path);

        try {
            if (ImageIO.read(f) == null) {
                return false;
            }
        } catch (Exception e) { //cannot read image properly
            return false;
        }

        return isValidSize(path);
    }
    /**
     * Check if image is greater than allowed size
     *
     * @param path
     * @return
     */
    public static boolean isValidSize(String path) {

        File f = new File(path);

        return (f.length() <= FIVE_MB_IN_BYTES);
    }

    /**
     * copies the file from (@code source) to the profile pic folder
     * If unable to copy, then just directly use file at source.
     * This will work since we have already passed {@code source} into isValidPath to ensure it can be read
     * TODO: display some kind of commandresult to notify user of this ^
     * @param source
     * @param dstFilename
     */
    public void createNewPicture(String source, String dstFilename) {

        File folder = new File(FOLDER);

        if (!folder.exists()) {

            if (!folder.mkdir()) {
                this.path = URL_PREFIX + source;
                return;
            }
        }

        File src = null;

        try {
            src = new File(source);
        } catch (Exception e) {
            this.path = URL_PREFIX + source;
            return;
        }


        File dest = new File(FOLDER + "//" + dstFilename);

        if (!dest.exists()) {
            try {
                dest.createNewFile();
            } catch (Exception e) {
                this.path = URL_PREFIX + source;
                return;
            }
        }

        try {
            Files.copy(src.toPath(), dest.toPath() , REPLACE_EXISTING);
            this.path = URL_PREFIX + dest.toPath().toString();
        } catch (Exception e) {
            this.path = URL_PREFIX + source;
        }
    }

    public String getPath() {
        return this.path;
    }

    /**
     * Determines the User OS and output the appropriate folder to store profile pic
     *
     * @return
     */
    private static String getDefaultDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("WIN")) {
            return System.getenv("APPDATA");
        } else if (os.contains("MAC")) {
            return System.getProperty("user.home") + "/Library/Application "
                    + "Support";
        } else if (os.contains("NUX")) {
            return System.getProperty("user.home");
        }
        return System.getProperty("user.dir");
    }

    /**
     * Removes the "file:/" prefix from a filepath, so that can use isValidPath on p
     * @param p
     * @return
     */
    private String truncateFilePrefix(String p) {

        if (p.substring(0, 6).equals("file:/")) {
            return p.substring(6);
        }
        return p;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Picture // instanceof handles nulls
                && this.path.equals(((Picture) other).path)); // state check
    }


}
