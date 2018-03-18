package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Student.
 */
public class XmlAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String programmingLanguage;


    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedStudent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedStudent() {}

    /**
     * Constructs an {@code XmlAdaptedStudent} with the given student details.
     */
    public XmlAdaptedStudent(String name, String phone, String email, String address,
                             String programmingLanguage, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.programmingLanguage = programmingLanguage;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Student into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedStudent
     */
    public XmlAdaptedStudent(Student source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        programmingLanguage = source.getProgrammingLanguage().programmingLanguage;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted student object into the model's Student object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student
     */
    public Student toModelType() throws IllegalValueException {
        final List<Tag> studentTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            studentTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(this.phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone phone = new Phone(this.phone);

        if (this.email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(this.email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email email = new Email(this.email);

        if (this.address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(this.address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address address = new Address(this.address);

        if (this.programmingLanguage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ProgrammingLanguage.class.getSimpleName()));
        }
        if (!ProgrammingLanguage.isValidProgrammingLanguage(this.programmingLanguage)) {
            throw new IllegalValueException(ProgrammingLanguage.MESSAGE_PROGRAMMING_LANGUAGE_CONSTRAINTS);
        }
        final ProgrammingLanguage programmingLanguage = new ProgrammingLanguage(this.programmingLanguage);

        final Set<Tag> tags = new HashSet<>(studentTags);
        return new Student(name, phone, email, address, programmingLanguage, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedStudent)) {
            return false;
        }

        XmlAdaptedStudent otherStudent = (XmlAdaptedStudent) other;
        return Objects.equals(name, otherStudent.name)
                && Objects.equals(phone, otherStudent.phone)
                && Objects.equals(email, otherStudent.email)
                && Objects.equals(address, otherStudent.address)
                && tagged.equals(otherStudent.tagged);
    }
}
