package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.UniquePersonList;

//@@author jas5469
/**
 * Represents a Group in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Group {

    private final Information information;
    private UniquePersonList personList;

    /**
     * Every field must be present and not null.
     */
    public Group(Information information) {
        requireAllNonNull(information);
        this.information = information;
        this.personList = new UniquePersonList();
    }
    /**
     * Every field must be present and not null.
     */
    public Group(Information information, UniquePersonList personList) {
        requireAllNonNull(information);
        this.information = information;
        this.personList = personList;
    }

    public Information getInformation() {
        return information;
    }

    public UniquePersonList getPersonList() {
        return personList;
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

