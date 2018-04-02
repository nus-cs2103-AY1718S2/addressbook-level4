package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Nric;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

//@@author wynonaK
/**
 * Represents an Appointment.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Appointment {
    private Nric ownerNric;
    private PetPatientName petPatientName;
    private Remark remark; //remarks
    private LocalDateTime localDateTime; //date of appointment

    private UniqueTagList appointmentTags; //type of appointment

    /**
     * Every field must be present and not null.
     */
    public Appointment(Nric ownerNric, PetPatientName petPatientName, Remark remark,
                       LocalDateTime localDateTime, Set<Tag> appointmentTags) {
        requireAllNonNull(ownerNric, petPatientName, remark, localDateTime, appointmentTags);
        this.ownerNric = ownerNric;
        this.petPatientName = petPatientName;
        this.remark = remark;
        this.localDateTime = localDateTime;
        // protect internal tags from changes in the arg list
        this.appointmentTags = new UniqueTagList(appointmentTags);
    }

    /**
     * ownerNric and petName can be set later using setter methods.
     */
    public Appointment(Remark remark, LocalDateTime localDateTime, Set<Tag> type) {
        requireAllNonNull(remark, localDateTime, type);
        this.remark = remark;
        this.localDateTime = localDateTime;
        // protect internal tags from changes in the arg list
        this.appointmentTags = new UniqueTagList(type);
    }

    public Nric getOwnerNric() {
        return ownerNric;
    }

    public void setOwnerNric(Nric ownerNric) {
        this.ownerNric = ownerNric;
    }

    public PetPatientName getPetPatientName() {
        return petPatientName;
    }

    public void setPetPatientName(PetPatientName petPatientName) {
        this.petPatientName = petPatientName;
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
    public Set<Tag> getAppointmentTags() {
        return Collections.unmodifiableSet(appointmentTags.toSet());
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
        return otherAppointment.getOwnerNric().equals(this.getOwnerNric())
                && otherAppointment.getPetPatientName().equals((this.getPetPatientName()))
                && otherAppointment.getRemark().equals(this.getRemark())
                && otherAppointment.getDateTime().equals(this.getDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ownerNric, petPatientName, remark, localDateTime, appointmentTags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\t")
                .append(getFormattedLocalDateTime())
                .append("\tRemarks: ")
                .append(getRemark())
                .append("\tType(s): ");
        getAppointmentTags().forEach(builder::append);
        return builder.toString();
    }

}
