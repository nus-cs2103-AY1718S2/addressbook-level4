package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import seedu.address.MainApp;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;

/**
 * Represents the filepath of a Person's displayPic in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class DisplayPic {

    public static final String MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS =
            "The filepath should lead to a file that exists.";
    public static final String MESSAGE_DISPLAY_PIC_NOT_IMAGE =
            "The filepath should point to a valid image file.";
    public static final String DEFAULT_DISPLAY_PIC = "/images/displayPic/default.png";
    public static final String DEFAULT_IMAGE_LOCATION = "/images/displayPic/";
    public static final String SAVE_LOCATION = "/src/main/resources/images/displayPic/";

    public final String value;

    public DisplayPic() {
        this.value = DEFAULT_DISPLAY_PIC;
    }

    /**
     * Constructs an {@code DisplayPic}.
     *
     * @param filePath A valid string containing the path to the file.
     */
    public DisplayPic(String name, String filePath) throws IllegalValueException {
        requireNonNull(filePath);
        String trimmedFilePath = filePath.trim();
        checkArgument(isValidPath(trimmedFilePath), MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        checkArgument(isValidImage(trimmedFilePath), MESSAGE_DISPLAY_PIC_NOT_IMAGE);


        String fileType = FileUtil.getFileType(trimmedFilePath);
        if (saveDisplayPic(name.trim(), trimmedFilePath, fileType)) {
            //this.value = name.trim() + '.' + fileType;
            this.value = DEFAULT_IMAGE_LOCATION + name.trim() + '.' + fileType;
        } else {
            this.value = DEFAULT_DISPLAY_PIC;
        }
    }

    public DisplayPic(String filePath) throws IllegalValueException {
        requireNonNull(filePath);
        this.value = filePath;
    }

    /**
     * Tries to save a copy of the image provided by the user into a default location.
     * @param name the name of the new image file
     * @param filePath the location of the current image file
     * @param fileType the file extension of the current image file
     * @return whether the image was successfully copied
     */
    public boolean saveDisplayPic(String name, String filePath, String fileType) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            FileUtil.copyImage(image, fileType, Paths.get(".").toAbsolutePath().normalize().toString()
                    + SAVE_LOCATION + name.trim() + '.' + fileType);
            return true;
        } catch (IOException | IllegalValueException exp) {
            return false;
        }
    }

    /**
     * Returns true if a given string points to a valid file.
     */
    public static boolean isValidPath(String test) {
        if (MainApp.class.getResourceAsStream(test) == null) {
            File file = new File(test);
            return FileUtil.isFileExists(file);
        } else {
            return true;
        }
    }

    /**
     * Checks if the image file provided can be opened properly as an image
     * @param test is a filepath to an image file
     * @return if the filePath it is pointing to is am image file that can be opened
     */
    public static boolean isValidImage(String test) {
        try {
            InputStream imageStream = ImageIO.class.getResourceAsStream(test);
            if (imageStream == null) {
                try {
                    BufferedImage image = ImageIO.read(new File(test));
                    return image != null;
                } catch (IOException e3) {
                    return false;
                }
            }
            BufferedImage image = ImageIO.read(imageStream);
            return image != null;
        } catch (IOException e) {
            try {
                BufferedImage image = ImageIO.read(new File(test));
                return image != null;
            } catch (IOException e2) {
                return false;
            }
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPic // instanceof handles nulls
                && this.value.equals(((DisplayPic) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
