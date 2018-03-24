package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.storage.DisplayPicStorage;

/**
 * Represents the filepath of a Person's displayPic in the address book.
 */
public class DisplayPic {

    public static final String DEFAULT_DISPLAY_PIC = "src/main/resources/images/displayPic/default.png";
    public static final String DEFAULT_IMAGE_LOCATION = "src/main/resources/images/displayPic/";

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
        checkArgument(DisplayPicStorage.isValidPath(trimmedFilePath),
                Messages.MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        checkArgument(DisplayPicStorage.isValidImage(trimmedFilePath), Messages.MESSAGE_DISPLAY_PIC_NOT_IMAGE);


        String fileType = FileUtil.getFileType(trimmedFilePath);
        if (DisplayPicStorage.saveDisplayPic(name.trim(), trimmedFilePath, fileType)) {
            this.value = DEFAULT_IMAGE_LOCATION + name.trim() + '.' + fileType;
        } else {
            this.value = DEFAULT_DISPLAY_PIC;
        }
    }

    public DisplayPic(String filePath) {
        requireNonNull(filePath);
        checkArgument(DisplayPicStorage.isValidPath(filePath), Messages.MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        checkArgument(DisplayPicStorage.isValidImage(filePath), Messages.MESSAGE_DISPLAY_PIC_NOT_IMAGE);
        this.value = filePath;
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
