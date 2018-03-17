package seedu.address.model.tag;

/**
 * Represents a Group in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)} in parent class.
 */
public class Group extends Tag {

    public Group(String groupTagName) {
        super(groupTagName);
    }
}
