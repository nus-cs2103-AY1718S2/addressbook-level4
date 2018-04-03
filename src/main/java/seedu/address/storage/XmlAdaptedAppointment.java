package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Nric;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.tag.Tag;

//@@author wynonaK
/**
 * JAXB-friendly version of an Appointment.
 */
public class XmlAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment %s field is missing!";

    @XmlElement(required = true)
    private String ownerNric;
    @XmlElement(required = true)
    private String petPatientName;
    @XmlElement(required = true)
    private String remark;
    @XmlElement(required = true)
    private String dateTime;

    @XmlElement
    private List<XmlAdaptedTag> appointmentTagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedAppointment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs an {@code XmlAdaptedAppointment} with the given appointment details.
     */
    public XmlAdaptedAppointment(String ownerNric, String petPatientName, String remark,
                                 String dateTime, List<XmlAdaptedTag> appointmentTagged) {
        this.ownerNric = ownerNric;
        this.petPatientName = petPatientName;
        this.remark = remark;
        this.dateTime = dateTime;
        if (appointmentTagged != null) {
            this.appointmentTagged = new ArrayList<>(appointmentTagged);
        }
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointment
     */
    public XmlAdaptedAppointment(Appointment source) {
        ownerNric = source.getOwnerNric().toString();
        petPatientName = source.getPetPatientName().toString();
        remark = source.getRemark().value;
        dateTime = source.getFormattedLocalDateTime();
        appointmentTagged = new ArrayList<>();
        for (Tag tag : source.getAppointmentTags()) {
            appointmentTagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment
     */
    public Appointment toModelType() throws IllegalValueException {
        final List<Tag> appointmentTags = new ArrayList<>();
        for (XmlAdaptedTag tag : appointmentTagged) {
            appointmentTags.add(tag.toModelType());
        }

        if (this.ownerNric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(this.ownerNric)) {
            throw new IllegalValueException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
        final Nric ownerNric = new Nric(this.ownerNric);

        if (this.petPatientName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, PetPatientName.class.getSimpleName()));
        }
        if (!PetPatientName.isValidName(this.petPatientName)) {
            throw new IllegalValueException(PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS);
        }
        final PetPatientName petPatientName = new PetPatientName(this.petPatientName);

        if (this.remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(this.remark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        }

        final Remark remark = new Remark(this.remark);

        if (this.dateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName()));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = null;

        try {
            localDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Please follow the format of yyyy-MM-dd HH:mm");
        }

        final LocalDateTime dateTime = localDateTime;


        final Set<Tag> thisAppointmentTags = new HashSet<>(appointmentTags);
        return new Appointment(ownerNric, petPatientName, remark, dateTime, thisAppointmentTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAppointment)) {
            return false;
        }

        XmlAdaptedAppointment otherAppointment = (XmlAdaptedAppointment) other;
        return Objects.equals(ownerNric, otherAppointment.ownerNric)
                && Objects.equals(petPatientName, otherAppointment.petPatientName)
                && Objects.equals(remark, otherAppointment.remark)
                && Objects.equals(dateTime, otherAppointment.dateTime)
                && appointmentTagged.equals(otherAppointment.appointmentTagged);
    }
}
