package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author karenfrilya97
/**
 * Represents a Desk Board's file path.
 * Guarantees: immutable; is valid as declared in {@link #isValidFilePath(String)}
 */
public class FilePath {

    public static final String MESSAGE_FILE_PATH_CONSTRAINTS =
            "Desk Board file path should not be blank.";

    public static final String FILE_PATH_VALIDATION_REGEX = ".+" + ".xml";

    public final String value;

    /**
     * Constructs a {@code FilePath}.
     *
     * @param filePath A valid file path.
     */
    public FilePath (String filePath) {
        requireNonNull(filePath);
        checkArgument(isValidFilePath(filePath), MESSAGE_FILE_PATH_CONSTRAINTS);
        this.value = filePath;
    }

    /**
     * Returns true if a given string is a valid Desk Board file path.
     */
    public static boolean isValidFilePath(String filePath) {
        return filePath.matches(FILE_PATH_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilePath // instanceof handles nulls
                && this.value.equals(((FilePath) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
