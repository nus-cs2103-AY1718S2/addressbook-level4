package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;


public class Appointment {
    private final Person owner; //owner of the appointment
    private final Remark remark; //remarks, if any
    private final LocalDateTime localDateTime; //date of appointment

    private final UniqueTagList type; //type of appointment

    /**
     * Every field must be present and not null.
     */
    public Appointment(Person owner, Remark remark, LocalDateTime localDateTime, Set<Tag> type) {
        requireAllNonNull(owner, localDateTime, type);
        this.owner = owner;
        this.remark = remark;
        this.localDateTime = localDateTime;
        // protect internal tags from changes in the arg list
        this.type = new UniqueTagList(type);
    }

    public Person getOwner() {
        return owner;
    }

    public Name getOwnerName() {
        return owner.getName();
    }

    public Remark getRemark() {
        return remark;
    }

    public LocalDateTime getDateTime() {
        return localDateTime;
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
        builder.append("Appointment Owner: ")
                .append(getOwnerName())
                .append(" Date of Appointment: ")
                .append(getDateTime())
                .append(" Remarks: ")
                .append(getRemark())
                .append(" Type of Appointment: ");
        getType().forEach(builder::append);
        return builder.toString();
    }

}
