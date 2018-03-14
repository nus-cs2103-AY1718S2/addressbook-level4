package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * JAXB-friendly adapted version of the Appointment.
 */
public class XmlAdaptedAppointment {

    @XmlElement
    private String patientName;

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
    public XmlAdaptedAppointment(String patientName, String dateTime) {
        this.patientName = patientName;
        this.dateTime = dateTime;
    }

    /**
     * converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAppointment(Appointment source) {
        this.patientName = source.getPatientName();
        this.dateTime = source.getAppointmentDateTime();
    }

    public Appointment toModelType() throws IllegalValueException {
        return new Appointment(patientName, dateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof  XmlAdaptedAppointment)) {
            return false;
        }
        return (patientName.equals(((XmlAdaptedAppointment) other).patientName)
                && dateTime.equals(((XmlAdaptedAppointment) other).dateTime));
    }
}
