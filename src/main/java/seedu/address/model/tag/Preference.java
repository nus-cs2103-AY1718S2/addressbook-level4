package seedu.address.model.tag;
//@@author SuxianAlicia
/**
 * Represents a Preference in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)} in parent class.
 */
public class Preference extends Tag {

    public Preference(String preferenceTagName) {
        super(preferenceTagName);
    }
}
