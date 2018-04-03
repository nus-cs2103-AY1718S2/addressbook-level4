package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * JAXB-friendly adapted version of the Appointment.
 */
public class XmlAdaptedAppointment {

    @XmlElement
    private String dateTime;

    /**
     * Constructs an XmlAdaptedAppointment
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs a {@code XmlAdaptedAppointment} with the given {@code patientName} and {@code dateTime}.
     */
    public XmlAdaptedAppointment(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAppointment(Appointment source) {
        this.dateTime = source.getAppointmentDateTimeString();
    }

    public Appointment toModelType() throws IllegalValueException {
        return new Appointment(dateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof  XmlAdaptedAppointment)) {
            return false;
        }
        return (dateTime.equals(((XmlAdaptedAppointment) other).dateTime));
    }
}
