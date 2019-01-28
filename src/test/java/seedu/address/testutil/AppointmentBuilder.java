package seedu.address.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Nric;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

//@@author wynonaK
/**
 * A utility class to help with building Appointment Objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_OWNER_NRIC = "S1012341B";
    public static final String DEFAULT_PET_PATIENT_NAME = "Joker";
    public static final String DEFAULT_REMARK = "Requires home visit";
    public static final String DEFAULT_DATE = "2018-12-31 12:30";
    public static final String DEFAULT_APPOINTMENT_TAG = "surgery";

    private Nric ownerNric;
    private PetPatientName petPatientName;
    private Remark remark;
    private LocalDateTime localDateTime;
    private Set<Tag> appointmentTags;

    public AppointmentBuilder() {
        ownerNric = new Nric(DEFAULT_OWNER_NRIC);
        petPatientName = new PetPatientName(DEFAULT_PET_PATIENT_NAME);
        remark = new Remark(DEFAULT_REMARK);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        localDateTime = LocalDateTime.parse(DEFAULT_DATE, formatter);
        appointmentTags = SampleDataUtil.getTagSet(DEFAULT_APPOINTMENT_TAG);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        ownerNric = appointmentToCopy.getOwnerNric();
        petPatientName = appointmentToCopy.getPetPatientName();
        remark = appointmentToCopy.getRemark();
        localDateTime = appointmentToCopy.getDateTime();
        appointmentTags = new HashSet<>(appointmentToCopy.getAppointmentTags());
    }

    /**
     * Sets the {@code Nric} of the person of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withOwnerNric(String ownerNric) {
        this.ownerNric = new Nric(ownerNric);
        return this;
    }

    /**
     * Sets the {@code PetPatientName} of the pet of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPetPatientName(String petPatientName) {
        this.petPatientName = new PetPatientName(petPatientName);
        return this;
    }

    /**
     * Parses the {@code appointmentTags} into a {@code Set<Tag>}
     * and set it to the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withAppointmentTags(String ... appointmentTags) {
        this.appointmentTags = SampleDataUtil.getTagSet(appointmentTags);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDateTime(String stringDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(stringDateTime, formatter);
        this.localDateTime = dateTime;
        return this;
    }

    public Appointment build() {
        return new Appointment(ownerNric, petPatientName, remark, localDateTime, appointmentTags);
    }
}
