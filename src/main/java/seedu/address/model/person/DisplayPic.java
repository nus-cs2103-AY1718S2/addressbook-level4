package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.storage.DisplayPicStorage;

/**
 * Represents the filepath of a Person's displayPic in the address book.
 */
//@@author Alaru
public class DisplayPic {

    public static final String DEFAULT_DISPLAY_PIC = "/images/displayPic/default.png";
    public static final String DEFAULT_IMAGE_LOCATION = "data/displayPic/";
    public static final String MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS =
            "The filepath should lead to a file that exists.";
    public static final String MESSAGE_DISPLAY_PIC_NOT_IMAGE =
            "The filepath should point to a valid image file.";
    public static final String MESSAGE_DISPLAY_PIC_NO_EXTENSION =
            "The filepath should point to a file with an extension.";

    public final String originalFilePath;
    private String value;

    public DisplayPic() {
        value = originalFilePath = DEFAULT_DISPLAY_PIC;
    }

    /**
     * Constructs an {@code DisplayPic}.
     *
     * @param filePath A valid string containing the path to the file.
     */
    public DisplayPic(String filePath) {
        requireNonNull(filePath);
        checkArgument(DisplayPicStorage.isValidPath(filePath), MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        checkArgument(DisplayPicStorage.hasValidExtension(filePath), MESSAGE_DISPLAY_PIC_NO_EXTENSION);
        checkArgument(DisplayPicStorage.isValidImage(filePath), MESSAGE_DISPLAY_PIC_NOT_IMAGE);
        value = originalFilePath = filePath;
    }

    /**
     * Creates the duplicated image filename.
     */
    public String getSaveDisplay(String personDetails) throws IllegalValueException {
        if (value.equals(DEFAULT_DISPLAY_PIC)) {
            return value;
        }
        String fileType = FileUtil.getFileType(value);
        String uniqueFileName = DisplayPicStorage.generateDisplayPicName(personDetails, value, fileType);
        value = DEFAULT_IMAGE_LOCATION + uniqueFileName + '.' + fileType;
        return uniqueFileName;
    }

    /**
     * Saves the display picture to the specified storage location.
     */
    public void saveDisplay(String uniqueName) throws IllegalValueException {
        if (value.equals(DEFAULT_DISPLAY_PIC)) {
            return;
        }
        String fileType = FileUtil.getFileType(value);
        DisplayPicStorage.saveDisplayPic(uniqueName, originalFilePath, fileType);
        value = DEFAULT_IMAGE_LOCATION + uniqueName + '.' + fileType;
    }

    public void updateToDefault() {
        this.value = DEFAULT_DISPLAY_PIC;
    }

    public boolean isDefault() {
        return value.equals(DEFAULT_DISPLAY_PIC);
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
