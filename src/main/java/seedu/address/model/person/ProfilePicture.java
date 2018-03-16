package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a ProfilePicture in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ProfilePicture {
    public static final String MESSAGE_PROFILEPICTURE_CONSTRAINTS =
            "Profile picture path should be a valid path, and it should end with either jpg, png, gif or bmp";

    // alphanumeric and special characters
    public static final String PROFILE_PICTURE_VALIDATION_REGEX = "^$|([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param profilePicture A valid image path.
     */
    public ProfilePicture(String profilePicture) {
        requireNonNull(profilePicture);
        checkArgument(isValidProfilePicture(profilePicture), MESSAGE_PROFILEPICTURE_CONSTRAINTS);
        this.value = profilePicture;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidProfilePicture(String test) {
        return test.matches(PROFILE_PICTURE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePicture // instanceof handles nulls
                && this.value.equals(((ProfilePicture) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
