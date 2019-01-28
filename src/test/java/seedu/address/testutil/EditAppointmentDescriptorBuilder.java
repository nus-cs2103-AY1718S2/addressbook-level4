package seedu.address.testutil;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.descriptors.EditAppointmentDescriptor;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Nric;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.tag.Tag;

//@@author chialejing
/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {

    private EditAppointmentDescriptor descriptor;

    public EditAppointmentDescriptorBuilder() {
        descriptor = new EditAppointmentDescriptor();
    }

    public EditAppointmentDescriptorBuilder(EditAppointmentDescriptor descriptor) {
        this.descriptor = new EditAppointmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAppointmentDescriptor} with fields containing {@code appointment}'s details
     */
    public EditAppointmentDescriptorBuilder(Appointment appointment) {
        descriptor = new EditAppointmentDescriptor();
        descriptor.setOwnerNric(appointment.getOwnerNric());
        descriptor.setPetPatientName(appointment.getPetPatientName());
        descriptor.setRemark(appointment.getRemark());
        descriptor.setLocalDateTime(appointment.getDateTime());
        descriptor.setTags(appointment.getAppointmentTags());
    }

    /**
     * Sets the {@code NRIC } of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withOwnerNric(String ownerNric) {
        descriptor.setOwnerNric(new Nric(ownerNric));
        return this;
    }

    /**
     * Sets the {@code PetPatientName} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withPetPatientName(String petPatientName) {
        descriptor.setPetPatientName(new PetPatientName(petPatientName));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withLocalDateTime(String localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        descriptor.setLocalDateTime(LocalDateTime.parse(localDateTime, formatter));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditAppointmentDescriptor}
     * that we are building.
     */
    public EditAppointmentDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditAppointmentDescriptor build() {
        return descriptor;
    }
}
