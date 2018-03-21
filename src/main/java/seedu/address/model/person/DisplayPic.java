package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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

    public final String value;

    public DisplayPic() {
        this.value = DEFAULT_DISPLAY_PIC;
    }

    /**
     * Constructs an {@code DisplayPic}.
     *
     * @param filePath A valid string containing the path to the file.
     */
    public DisplayPic(String filePath) {
        requireNonNull(filePath);
        checkArgument(isValidPath(filePath), MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        checkArgument(isValidImage(filePath), MESSAGE_DISPLAY_PIC_NOT_IMAGE);


        this.value = filePath;
    }

    /**
     * Returns true if a given string points to a valid file.
     */
    public static boolean isValidPath(String test) {
        File file = new File(test);
        return FileUtil.isFileExists(file);
    }

    /**
     * Checks if the image file provided can be opened properly as an image
     * @param test is a filepath to an image file
     * @return if the filePath it is pointing to is am image file that can be opened
     */
    public static boolean isValidImage(String test) {
        try {
            BufferedImage image = ImageIO.read(new File(test));
            return image != null;
        } catch (IOException e) {
            return false;
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
