//@@author RyanAngJY
package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import seedu.recipe.MainApp;
import seedu.recipe.commons.core.LogsCenter;

/**
 * Represents a Recipe's image in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidImage(String)}
 */
public class Image {

    public static final String NULL_IMAGE_REFERENCE = "-";
    public static final String FILE_PREFIX = "file:";
    public static final String MESSAGE_IMAGE_CONSTRAINTS = "Image path should be valid";
    public static final URL VALID_IMAGE = MainApp.class.getResource("/images/clock.png");
    public static final String VALID_IMAGE_PATH = VALID_IMAGE.toExternalForm().substring(5);
    public static final String IMAGE_DIRECTORY = "data/images/";
    public static final String DOWNLOADED_IMAGE_FORMAT = "jpg";
    public final String value;

    /**
     * Constructs a {@code Image}.
     *
     * @param imagePath A valid file path.
     */
    public Image(String imagePath) {
        requireNonNull(imagePath);
        checkArgument(isValidImage(imagePath), MESSAGE_IMAGE_CONSTRAINTS);
        if (isValidImageUrl(imagePath)) {
            imagePath = downloadImage(imagePath);
        }
        this.value = imagePath;
    }

    //@@author kokonguyen191

    /**
     * Returns true if a given string is a valid file path, or no file path has been assigned
     */
    public static boolean isValidImage(String testImageInput) {
        if (testImageInput.equals(NULL_IMAGE_REFERENCE)) {
            return true;
        } else {
            boolean isValidImageStringInput = isValidImageStringInput(testImageInput);
            boolean isValidImagePath = isValidImagePath(testImageInput);
            boolean isValidImageUrl = isValidImageUrl(testImageInput);

            boolean isValidImage = isValidImageStringInput && (isValidImagePath || isValidImageUrl);

            return isValidImage;
        }
    }

    /**
     * Returns true if the input is a valid input syntax-wise
     */
    private static boolean isValidImageStringInput(String testString) {
        String trimmedTestImagePath = testString.trim();
        if (trimmedTestImagePath.equals("")) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if {@code testPath} is valid and points to an image
     */
    private static boolean isValidImagePath(String testPath) {
        try {
            File file = new File(testPath);
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                return false;
            } else {
                return true;
            }
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Returns true if {@code testUrl} is valid and connects to an image
     */
    private static boolean isValidImageUrl(String testUrl) {
        URL imageUrl = null;
        try {
            imageUrl = new URL(testUrl);
        } catch (MalformedURLException e) {
            return false;
        }

        BufferedImage image = null;
        try {
            image = ImageIO.read(imageUrl);
        } catch (IOException ioe) {
            LogsCenter.getLogger(Image.class).warning("Cannot get image from "
                    + testUrl + ". It is likely the app is not connected to the Internet.");
            return false;
        }

        if (image != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Downloads an iamge from {@code imageUrlString} to the images folder
     */
    public static String downloadImage(String imageUrlString) {
        assert isValidImageUrl(imageUrlString);

        try {
            byte[] imageData = getImageData(imageUrlString);
            String md5Checksum = calculateMd5Checksum(imageData);
            String filePath = getImageFilePathFromImageName(md5Checksum);
            File file = prepareImageFile(filePath);
            writeDataToFile(imageData, file);
            return filePath;
        } catch (IOException ioe) {
            throw new AssertionError(
                    "Something wrong happened when the app was trying to "
                            + "download image data from " + imageUrlString
                            + ". This should not happen.", ioe);
        } catch (NoSuchAlgorithmException nsaee) {
            throw new AssertionError(
                    "Something wrong happened when the app was trying to "
                            + "calculate the MD5 checksum for the iamge from " + imageUrlString
                            + ". This should not happen.", nsaee);
        }
    }

    /**
     * Gets a byte array from the {@code imageUrlSring}
     */
    private static byte[] getImageData(String imageUrlString) throws IOException {
        URL imageUrl = new URL(imageUrlString);
        BufferedImage image = ImageIO.read(imageUrl);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, DOWNLOADED_IMAGE_FORMAT, byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return data;
    }

    /**
     * Returns the MD5 checksum String value of given {@code data}
     */
    private static String calculateMd5Checksum(byte[] data) throws NoSuchAlgorithmException {
        requireNonNull(data);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        // Adapted from https://stackoverflow.com/questions/5470219/get-md5-string-from-message-digest
        HexBinaryAdapter hexBinaryAdapter = new HexBinaryAdapter();
        return hexBinaryAdapter.marshal(md5.digest());
    }

    private static String getImageFilePathFromImageName(String imageName) {
        return IMAGE_DIRECTORY + imageName + "." + DOWNLOADED_IMAGE_FORMAT;
    }

    /**
     * Checks if {@code filePath} exists or not. If not, create a file at {@code filePath} as well as any parent
     * directory if necessary, then returns the File object.
     */
    private static File prepareImageFile(String filePath) throws IOException {
        File directory = new File(IMAGE_DIRECTORY);
        File file = new File(filePath);
        directory.mkdirs();
        file.createNewFile();
        return file;
    }

    /**
     * Writes given {@code data} to {@code file}
     */
    private static void writeDataToFile(byte[] data, File file) throws IOException {
        requireNonNull(data);
        requireNonNull(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(data);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
    //@@author RyanAngJY

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
