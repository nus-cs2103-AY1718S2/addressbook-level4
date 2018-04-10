package seedu.address.model.photo;
//@@author crizyli
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's photo.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhotoName(String)}
 */
public class Photo {

    public static final String DEFAULT_PHOTO_FOLDER = "/images/personphoto/";

    public static final String MESSAGE_PHOTO_CONSTRAINTS = "only accepts jpg, jpeg, png and bmp.";

    public final String path;

    public final String name;

    /**
     * Constructs a {@code Photo}.
     *
     * @param name A photo name in images folder.
     */
    public Photo(String name) {
        this.name = name;
        this.path = DEFAULT_PHOTO_FOLDER + name;
    }

    /**
     * Returns true if a given string is a valid photo path.
     */
    public static boolean isValidPhotoName(String test) {
        String extension = test.substring(test.lastIndexOf(".") + 1);
        return extension.compareToIgnoreCase("jpg") == 0
                || extension.compareToIgnoreCase("png") == 0
                || extension.compareToIgnoreCase("jpeg") == 0
                || extension.compareToIgnoreCase("bmp") == 0;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.path.equals(((Photo) other).path)); // state check
    }

}
