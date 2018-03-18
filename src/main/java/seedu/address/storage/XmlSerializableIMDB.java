package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Imdb;
import seedu.address.model.ReadOnlyIMDB;

/**
 * An Immutable Imdb that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableIMDB {

    @XmlElement
    private List<XmlAdaptedPatient> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedAppointment> appointments;

    /**
     * Creates an empty XmlSerializableIMDB.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableIMDB() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableIMDB(ReadOnlyIMDB src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPatient::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        appointments.addAll(src.getAppointmentList().stream().map(XmlAdaptedAppointment::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code Imdb} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPatient} or {@code XmlAdaptedTag}.
     */
    public Imdb toModelType() throws IllegalValueException {
        Imdb Imdb = new Imdb();
        for (XmlAdaptedTag t : tags) {
            Imdb.addTag(t.toModelType());
        }
        for (XmlAdaptedPatient p : persons) {
            Imdb.addPerson(p.toModelType());
        }

        for (XmlAdaptedAppointment appt : appointments) {
            Imdb.addAppointment(appt.toModelType());
        }
        return Imdb;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableIMDB)) {
            return false;
        }

        XmlSerializableIMDB otherAb = (XmlSerializableIMDB) other;
        return persons.equals(otherAb.persons) && tags.equals(otherAb.tags)
                && appointments.equals(otherAb.appointments);
    }
}
