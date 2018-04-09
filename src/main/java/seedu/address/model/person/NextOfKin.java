package seedu.address.model.person;

import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class NextOfKin {

    private final Name name;
    private final Nric nric;
    private final Remark remark;
    private final InjuriesHistory injuriesHistory;

    /**
     * Every field must be present and not null.
     */

    public NextOfKin(Name name, Nric nric, Remark remark, InjuriesHistory injuriesHistory) {
        requireAllNonNull(name, nric);
        this.name = name;
        this.nric = nric;
        // protect internal tags from changes in the arg list
        this.remark = remark;
        this.injuriesHistory = injuriesHistory;
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Remark getRemark() {
        return remark;
    }

    public InjuriesHistory getInjuriesHistory() {
        return injuriesHistory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NextOfKin)) {
            return false;
        }

        NextOfKin otherPerson = (NextOfKin) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getNric().equals(this.getNric());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, remark, injuriesHistory);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Nric: ")
                .append(getNric());
        builder.append(" Remarks: ")
               .append(getRemark());
        builder.append(" InjuriesHistory: ").append(getInjuriesHistory());
        return builder.toString();
    }

}
