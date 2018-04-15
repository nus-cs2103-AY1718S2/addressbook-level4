package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
//@@author chuakunhong

/**
 * Represents a remarks of the person in the address book.
 */
public class Cca {

    public final String value;
    public final String pos;

    /**
     * Constructs a {@code Cca}.
     *
     * @param cca A valid cca.
     * @param pos A valid position.
     */
    public Cca(String cca, String pos) {
        requireNonNull(cca);
        this.value = cca;
        requireNonNull(pos);
        this.pos = pos;

    }

    @Override
    public String toString() {
        if (value != "" && pos != "") {
            return value + ", " + pos;
        } else {
            return "";
        }
    }

    public String getValue() {
        return value;
    }

    public String getPos() {
        return pos;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cca // instanceof handles nulls
                && this.value.equals(((Cca) other).value)); // state check
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 37 * hash + value.hashCode();
        hash = 37 * hash + pos.hashCode();
        return hash;
    }

}
//@@author
