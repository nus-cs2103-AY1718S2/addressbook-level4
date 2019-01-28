package seedu.address.logic.descriptors;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Nric;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.tag.Tag;

//@@author chialejing
/**
 * Stores the details to edit the appointment with. Each non-empty field value will replace the
 * corresponding field value of the appointment.
 */
public class EditAppointmentDescriptor {
    private Nric ownerNric;
    private PetPatientName petPatientName;
    private Remark remark;
    private LocalDateTime localDateTime;
    private Set<Tag> tags;

    public EditAppointmentDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
        setOwnerNric(toCopy.ownerNric);
        setPetPatientName(toCopy.petPatientName);
        setRemark(toCopy.remark);
        setLocalDateTime(toCopy.localDateTime);
        setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(this.ownerNric, this.petPatientName, this.remark,
                this.localDateTime, this.tags);
    }

    public void setOwnerNric(Nric ownerNric) {
        this.ownerNric = ownerNric;
    }

    public Optional<Nric> getOwnerNric() {
        return Optional.ofNullable(ownerNric);
    }

    public void setPetPatientName(PetPatientName petPatientName) {
        this.petPatientName = petPatientName;
    }

    public Optional<PetPatientName> getPetPatientName() {
        return Optional.ofNullable(petPatientName);
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    public Optional<Remark> getRemark() {
        return Optional.ofNullable(remark);
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Optional<LocalDateTime> getLocalDateTime() {
        return Optional.ofNullable(localDateTime);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentDescriptor)) {
            return false;
        }

        // state check
        EditAppointmentDescriptor e = (EditAppointmentDescriptor) other;

        return getOwnerNric().equals(e.getOwnerNric())
                && getPetPatientName().equals(e.getPetPatientName())
                && getRemark().equals(e.getRemark())
                && getLocalDateTime().equals(e.getLocalDateTime())
                && getTags().equals(e.getTags());
    }
}
