package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    @XmlElement
    private List<XmlAdaptedClient> students;
    @XmlElement
    private List<XmlAdaptedClient> tutors;
    @XmlElement
    private List<XmlAdaptedClient> closedStudents;
    @XmlElement
    private List<XmlAdaptedClient> closedTutors;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        tutors = new ArrayList<>();
        students = new ArrayList<>();
        closedStudents = new ArrayList<>();
        closedTutors = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        students.addAll(src.getStudentList().stream().map(XmlAdaptedClient::new)
                .collect(Collectors.toList()));
        tutors.addAll(src.getTutorList().stream().map(XmlAdaptedClient::new)
                .collect(Collectors.toList()));
        closedStudents.addAll(src.getClosedStudentList().stream().map(XmlAdaptedClient::new)
                .collect(Collectors.toList()));
        closedTutors.addAll(src.getClosedTutorList().stream().map(XmlAdaptedClient::new)
                .collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedTag t : tags) {
            addressBook.addTag(t.toModelType());
        }
        for (XmlAdaptedClient s : students) {
            addressBook.addStudent(s.toModelType());
        }
        for (XmlAdaptedClient t : tutors) {
            addressBook.addTutor(t.toModelType());
        }
        for (XmlAdaptedClient cs : closedStudents) {
            addressBook.addClosedStudent(cs.toModelType());
        }
        for (XmlAdaptedClient ct : closedTutors) {
            addressBook.addClosedTutor(ct.toModelType());
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
        return students.equals(otherAb.students) && tutors.equals(otherAb.tutors) && tags.equals(otherAb.tags);
    }
}
