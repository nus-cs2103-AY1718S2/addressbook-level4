package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient {

    private final Name name;
    private final Nric nric;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final DateOfBirth dob;
    private final BloodType bloodType;
    private final Remark remark;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Nric nric, Phone phone, Email email, Address address,
                   DateOfBirth dob, BloodType bloodType, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dob = dob;
        this.bloodType = bloodType;
        this.remark = remark;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public DateOfBirth getDob() {
        return dob;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return otherPatient.getName().equals(this.getName())
                && otherPatient.getNric().equals(this.getNric())
                && otherPatient.getPhone().equals(this.getPhone())
                && otherPatient.getEmail().equals(this.getEmail())
                && otherPatient.getAddress().equals(this.getAddress())
                && otherPatient.getDob().equals(this.getDob())
                && otherPatient.getBloodType().equals(this.getBloodType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, phone, email, address, dob, bloodType, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Nric: ")
                .append(getNric())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" DOB: ")
                .append(getDob())
                .append(" Blood Type: ")
                .append(getBloodType())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Conditions: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
