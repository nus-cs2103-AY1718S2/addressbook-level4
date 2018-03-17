package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.IMDB;
import seedu.address.model.ReadOnlyIMDB;

/**
 * An Immutable IMDB that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    @XmlElement
    private List<XmlAdaptedPatient> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedAppointment> appointments;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyIMDB src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPatient::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        appointments.addAll(src.getAppointmentList().stream().map(XmlAdaptedAppointment::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code IMDB} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPatient} or {@code XmlAdaptedTag}.
     */
    public IMDB toModelType() throws IllegalValueException {
        IMDB IMDB = new IMDB();
        for (XmlAdaptedTag t : tags) {
            IMDB.addTag(t.toModelType());
        }
        for (XmlAdaptedPatient p : persons) {
            IMDB.addPerson(p.toModelType());
        }

        for (XmlAdaptedAppointment appt : appointments) {
            IMDB.addAppointment(appt.toModelType());
        }
        return IMDB;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }

        XmlSerializableAddressBook otherAb = (XmlSerializableAddressBook) other;
        return persons.equals(otherAb.persons) && tags.equals(otherAb.tags)
                && appointments.equals(otherAb.appointments);
    }
}
