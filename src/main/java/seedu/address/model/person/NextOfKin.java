package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class NextOfKin {

    private final Name name;
    private final Nric nric;
    private final Phone phone;
    private final Email email;
    private final Remark remark;

    /**
     * Every field must be present and not null.
     */

    public NextOfKin(Name name, Nric nric, Phone phone, Email email, Remark remark) {
        requireAllNonNull(name, nric);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        // protect internal tags from changes in the arg list
        this.remark = remark;
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

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
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
        return Objects.hash(name, nric, phone, email, remark);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Nric: ")
                .append(getNric());
        builder.append(" Phone: ")
                .append(getPhone());
        builder.append(" Email: ")
                .append(getEmail());
        builder.append(" Remarks: ")
               .append(getRemark());
        return builder.toString();
    }

}
