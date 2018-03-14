package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String MESSAGE_TAG_COLOR_CONSTRAINTS = "Colors available are: "
            + "teal, red, yellow, blue, orange, brown, green, pink, black, grey";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";
    private static final String[] AVAILABLE_COLORS
            = {"teal", "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey"};

    public final String tagName;
    public String tagColor;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_TAG_CONSTRAINTS);
        this.tagName = tagName;
        this.tagColor = "undefined";
    }

    public Tag(String tagName, String tagColor) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_TAG_CONSTRAINTS);
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a available tag color
     */
    public static boolean isValidTagColor(String color) {
        boolean isValidColor = false;
        String trimmedColor = color.trim().toLowerCase();
        for (String s : AVAILABLE_COLORS) {
            if (s.equals(trimmedColor)) {
                isValidColor = true;
            }
        }
        return isValidColor;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && this.tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
