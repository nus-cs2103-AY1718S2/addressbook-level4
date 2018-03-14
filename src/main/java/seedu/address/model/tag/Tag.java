package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public static final String MESSAGE_TAG_COLOR_CONSTRAINTS = "Colors available are: "
            + "teal, red, yellow, blue, orange, brown, green, pink, black, grey";
    public static final String TAG_COLOR_FILE_PATH = "data/tagColors.txt";
    private static final String[] AVAILABLE_COLORS = new String[] {"teal", "red", "yellow", "blue", "orange", "brown",
        "green", "pink", "black", "grey", "undefined"};

    public final String name;
    public final String color;

    /**
     * Constructs a {@code Tag}.
     *
     * @param name A valid tag name.
     */
    public Tag(String name) {
        requireNonNull(name);
        checkArgument(isValidTagName(name), MESSAGE_TAG_CONSTRAINTS);
        this.name = name;
        this.color = "undefined";
    }

    public Tag(String name, String color) {
        requireNonNull(name);
        checkArgument(isValidTagName(name), MESSAGE_TAG_CONSTRAINTS);
        checkArgument(isValidTagColor(color), MESSAGE_TAG_COLOR_CONSTRAINTS);
        this.name = name;
        this.color = color;
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
        String trimmedColor = color.trim().toLowerCase();
        for (String s : AVAILABLE_COLORS) {
            if (s.equals(trimmedColor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && this.name.equals(((Tag) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + name + ']';
    }

}
