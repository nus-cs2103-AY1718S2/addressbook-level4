package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Group in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Group {

    private final Information information;

    /**
     * Every field must be present and not null.
     */
    public Group(Information information) {
        requireAllNonNull(information);
        this.information = information;
    }

    public Information getInformation() {
        return information;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getInformation().equals(this.getInformation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(information);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getInformation());
        return builder.toString();
    }

}

