package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag implements Comparable<Tag> {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should contain only "
            + "alphanumeric characters and spaces";

    public static final String TAG_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    /**
     * This represents all the tag types.
     */
    public enum AllTagTypes { ROLE, SUBJECT, LEVEL, PRICE, STATUS, DEFAULT }

    public final String tagName;
    public final AllTagTypes tagType;

    /**
     * Constructs a {@code Tag}.
     * tagType will be initialised with the AllTagTypes.DEFAULT value.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_TAG_CONSTRAINTS);
        this.tagName = tagName;
        this.tagType = AllTagTypes.DEFAULT;
    }

    //@@author aussiroth
    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     * @param tagType A valid tag type.
     */
    public Tag(String tagName, AllTagTypes tagType) {
        this.tagType = tagType;
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_TAG_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    /**
     * returns true if given string is a valid tag type.
     * @param test A string to test.
     */
    public static boolean isValidTagType(String test) {
        for (AllTagTypes tType : AllTagTypes.values()) {
            if (tType.toString().equals(test)) {
                return true;
            }
        }
        return false;
    }

    public int compareTo(Tag other) {
        return this.tagType.compareTo(other.tagType);
    }

    //@@author
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
