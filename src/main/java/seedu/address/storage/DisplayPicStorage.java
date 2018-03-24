package seedu.address.storage;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

import seedu.address.MainApp;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.DisplayPic;

/**
 *  A class to save and open image files from the user's hard disk.
 */
public class DisplayPicStorage {

    public static final String SAVE_LOCATION = "src/main/resources/images/displayPic/";

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

    /**
     * Tries to save a copy of the image provided by the user into a default location.
     * @param name the name of the new image file
     * @param filePath the location of the current image file
     * @param fileType the file extension of the current image file
     * @return whether the image was successfully copied
     */
    public static boolean saveDisplayPic(String name, String filePath, String fileType) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            FileUtil.copyImage(image, fileType, SAVE_LOCATION + name + '.' + fileType);
            return true;
        } catch (IOException | IllegalValueException exc) {
            return false;
        }
    }

    /**
     * Fetches an image from the hard drive to display
     * @param dp is a DisplayPic object
     * @return An image to display
     */
    public static Image fetchDisplay(DisplayPic dp) {
        String filePath = dp.toString();
        checkArgument(DisplayPicStorage.isValidPath(filePath), Messages.MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        checkArgument(DisplayPicStorage.isValidImage(filePath), Messages.MESSAGE_DISPLAY_PIC_NOT_IMAGE);
        File input = new File(dp.toString());
        return new Image(input.toURI().toString());
    }

}
