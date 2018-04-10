package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

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
    private static final int CROP_DIMENSION = 100;

    public final String value;
    private final Image image;

    /**
     * Constructs a {@code ProfileImage}.
     *
     * @param fileName A valid fileName.
     */
    public ProfileImage(String fileName) {
        if (isNull(fileName)) {
            this.value = null;
            this.image = null;
        } else {
            checkArgument(isValidFile(fileName), MESSAGE_IMAGE_CONSTRAINTS);
            this.value = fileName;
            this.image = loadImage();
        }
    }

    public Image getImage() {
        return image;
    }

    /**
     * Return the loaded {@code Image} of the person's Profile Image,
     * resized to 100px for performance issue
     * @return the image in {@code Image}
     */
    private Image loadImage() {
        try {
            File file = getFile();
            if (file != null) {
                //Image image = new Image(file.toURI().toString(), 0, 0, true, true, true);

                // Load image
                BufferedImage image = ImageIO.read(file);

                // Scaling amd resizing calculation
                int width = image.getWidth();
                int height = image.getHeight();
                int shorter = Math.min(width, height);
                double scale = (double) shorter / (double) CROP_DIMENSION;
                int x = 0;
                int y = 0;

                if (width < height) {
                    width = CROP_DIMENSION;
                    height = (int) Math.round((double) height / scale);
                    y = (CROP_DIMENSION - height) / 2;
                } else {
                    height = CROP_DIMENSION;
                    width = (int) Math.round((double) width / scale);
                    x = (CROP_DIMENSION - width) / 2;
                }

                // Resize start
                BufferedImage resized = new BufferedImage(CROP_DIMENSION, CROP_DIMENSION,
                        BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g2d = resized.createGraphics();
                g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_SPEED));
                g2d.drawImage(image, x, y, width, height, null);

                // Output
                WritableImage output = new WritableImage(CROP_DIMENSION, CROP_DIMENSION);
                SwingFXUtils.toFXImage(resized, output);

                // Clean up
                image.flush();
                resized.flush();
                g2d.dispose();

                return output;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
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

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof ProfileImage // instanceof handles nulls
                && Objects.equals(this.value, ((ProfileImage) other).value)); // State check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
