package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

//@@author kengsengg
/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    @XmlElement(required = true)
    private String info;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given appointment details.
     */
    public XmlAdaptedAppointment(String info, String date, String startTime, String endTime) {
        this.info = info;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedAppointment(Appointment source) {
        info = source.getInfo();
        date = source.getDate();
        startTime = source.getStartTime();
        endTime = source.getEndTime();
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment
     */
    public Appointment toModelType() throws IllegalValueException {
        if (!Appointment.isValidAppointmentDate(this.date)) {
            throw new IllegalValueException(Appointment.MESSAGE_APPOINTMENT_DATE_CONSTRAINTS);
        }

        if (!Appointment.isValidAppointmentStartTime(this.startTime)) {
            throw new IllegalValueException(Appointment.MESSAGE_APPOINTMENT_START_TIME_CONSTRAINTS);
        }

        if (!Appointment.isValidAppointmentEndTime(this.endTime)) {
            throw new IllegalValueException(Appointment.MESSAGE_APPOINTMENT_END_TIME_CONSTRAINTS);
        }

        return new Appointment(info, date, startTime, endTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedAppointment otherAppointment = (XmlAdaptedAppointment) other;
        return Objects.equals(info, otherAppointment.info)
                && Objects.equals(date, otherAppointment.date)
                && Objects.equals(startTime, otherAppointment.startTime)
                && Objects.equals(endTime, otherAppointment.endTime);
    }
}
//@@author
