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
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of an Appointment.
 */
public class XmlAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment %s field is missing!";

    @XmlElement(required = true)
    private String owner;
    @XmlElement(required = true)
    private String remark;
    @XmlElement(required = true)
    private String dateTime;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedAppointment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs an {@code XmlAdaptedAppointment} with the given appointment details.
     */
    public XmlAdaptedAppointment(String owner, String remark, String dateTime, List<XmlAdaptedTag> tagged) {
        this.owner = owner;
        this.remark = remark;
        this.dateTime = dateTime;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointment
     */
    public XmlAdaptedAppointment(Appointment source) {
        owner = source.getOwner().fullName;
        remark = source.getRemark().value;
        dateTime = source.getFormattedLocalDateTime();
        tagged = new ArrayList<>();
        for (Tag tag : source.getType()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment
     */
    public Appointment toModelType() throws IllegalValueException {
        final List<Tag> appointmentTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            appointmentTags.add(tag.toModelType());
        }

        if (this.owner == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.owner)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name owner = new Name(this.owner);

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


        final Set<Tag> tags = new HashSet<>(appointmentTags);
        return new Appointment(owner, remark, dateTime, tags);
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
        return Objects.equals(owner, otherAppointment.owner)
                && Objects.equals(remark, otherAppointment.remark)
                && Objects.equals(dateTime, otherAppointment.dateTime)
                && tagged.equals(otherAppointment.tagged);
    }
}
