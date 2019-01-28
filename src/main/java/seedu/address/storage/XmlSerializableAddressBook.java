package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.exceptions.ConcurrentAppointmentException;
import seedu.address.model.appointment.exceptions.PastAppointmentException;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedAppointment> appointments;
    @XmlElement
    private List<XmlAdaptedPetPatient> petPatients;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
        appointments = new ArrayList<>();
        petPatients = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new)
                .collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new)
                .collect(Collectors.toList()));
        appointments.addAll(src.getAppointmentList().stream().map(XmlAdaptedAppointment::new)
                .collect(Collectors.toList()));
        petPatients.addAll(src.getPetPatientList().stream().map(XmlAdaptedPetPatient::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public AddressBook toModelType() throws IllegalValueException,
        ConcurrentAppointmentException, PastAppointmentException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedTag t : tags) {
            addressBook.addTag(t.toModelType());
        }
        for (XmlAdaptedPerson p : persons) {
            addressBook.addPerson(p.toModelType());
        }
        for (XmlAdaptedAppointment a : appointments) {
            addressBook.addAppointment(a.toModelType());
        }
        for (XmlAdaptedPetPatient pp : petPatients) {
            addressBook.addPetPatient(pp.toModelType());
        }
        return addressBook;
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
        return persons.equals(otherAb.persons)
                && tags.equals(otherAb.tags)
                && appointments.equals(otherAb.appointments)
                && petPatients.equals(otherAb.petPatients);
    }
}
