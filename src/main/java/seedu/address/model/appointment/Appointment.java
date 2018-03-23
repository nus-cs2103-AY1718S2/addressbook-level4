package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents an Appointment.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Appointment {
    private Person owner = null; //owner of the appointment
    private Name ownerName = null;
    private PetPatient pet = null;
    private Remark remark; //remarks
    private LocalDateTime localDateTime; //date of appointment

    private final UniqueTagList type; //type of appointment

    /**
     * Every field must be present and not null.
     */
    public Appointment(Person owner, PetPatient pet, Remark remark, LocalDateTime localDateTime, Set<Tag> type) {
        requireAllNonNull(owner, remark, localDateTime, type);
        this.owner = owner;
        this.pet = pet;
        this.remark = remark;
        this.localDateTime = localDateTime;
        // protect internal tags from changes in the arg list
        this.type = new UniqueTagList(type);
    }

    public Appointment(Name owner, Remark remark, LocalDateTime localDateTime, Set<Tag> type) {
        requireAllNonNull(owner, remark, localDateTime, type);
        this.ownerName = owner;
        this.remark = remark;
        this.localDateTime = localDateTime;
        // protect internal tags from changes in the arg list
        this.type = new UniqueTagList(type);
    }

    public Person getOwner() {
        return owner;
    }

    public Name getOwnerName() {
        return ownerName;
    }

    public PetPatient getPetPatient() {
        return pet;
    }

    public Remark getRemark() {
        return remark;
    }

    public LocalDateTime getDateTime() {

        return localDateTime;
    }

    public String getFormattedLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getType() {
        return Collections.unmodifiableSet(type.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getOwner().equals(this.getOwner())
                && otherAppointment.getRemark().equals(this.getRemark())
                && otherAppointment.getDateTime().equals(this.getDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(owner, remark, localDateTime, type);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\t")
                .append(getFormattedLocalDateTime())
                .append("\tOwner: ")
                .append(getOwner().getName().toString())
                .append("\tPet Patient: ")
                .append(getPetPatient().getName().toString())
                .append("\tRemarks: ")
                .append(getRemark())
                .append("\tType(s): ");
        getType().forEach(builder::append);
        return builder.toString();
    }

}
