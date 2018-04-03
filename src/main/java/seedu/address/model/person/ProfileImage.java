package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.util.Objects;

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
    public final String value;

    /**
     * Constructs a {@code ProfileImage}.
     *
     * @param fileName A valid fileName.
     */
    public ProfileImage(String fileName) {
        if (isNull(fileName)) {
            this.value = null;
        } else {
            checkArgument(isValidFile(fileName), MESSAGE_IMAGE_CONSTRAINTS);
            this.value = fileName;
        }
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

        String userDir = System.getProperty("user.dir");
        File imageFile = new File(userDir + File.separator + test);

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
