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
import seedu.address.model.student.Favourite;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueKey;
import seedu.address.model.student.dashboard.Dashboard;
import seedu.address.model.student.miscellaneousinfo.MiscellaneousInfo;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Student.
 */
public class XmlAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    @XmlElement(required = true)
    private String key;
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
    @XmlElement(required = true)
    private String profilePicturePath;
    @XmlElement(required = true)
    private String favourite;
    @XmlElement(required = true)
    private XmlAdaptedDashboard dashboard = new XmlAdaptedDashboard();
    @XmlElement (required = true)
    private XmlAdaptedMiscInfo miscellaneousInfo = new XmlAdaptedMiscInfo();

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
    public XmlAdaptedStudent(String uniqueKey, String name, String phone, String email, String address,
                             String programmingLanguage, List<XmlAdaptedTag> tagged, String favourite,
                             String profilePicturePath, XmlAdaptedDashboard dashboard,
                             XmlAdaptedMiscInfo miscellaneousInfo) {
        this.key = uniqueKey;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.programmingLanguage = programmingLanguage;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.profilePicturePath = profilePicturePath;
        this.miscellaneousInfo = miscellaneousInfo;
        this.favourite = favourite;
        if (dashboard != null) {
            this.dashboard = dashboard;
        }
    }

    /**
     * Converts a given Student into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedStudent
     */
    public XmlAdaptedStudent(Student source) {
        key = source.getUniqueKey().toString();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        programmingLanguage = source.getProgrammingLanguage().programmingLanguage;
        profilePicturePath = source.getProfilePicturePath().toString();
        tagged = new ArrayList<>();
        miscellaneousInfo = new XmlAdaptedMiscInfo(source.getMiscellaneousInfo());
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        favourite = source.getFavourite().value;
        dashboard = new XmlAdaptedDashboard(source.getDashboard());
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

        if (this.key == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, UniqueKey.class.getSimpleName()));
        }
        if (!UniqueKey.isValidUniqueKey(this.key)) {
            throw new IllegalValueException(UniqueKey.MESSAGE_UNIQUE_KEY_CONSTRAINTS);
        }
        final UniqueKey uniqueKey = new UniqueKey(key);

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

        if (this.profilePicturePath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ProfilePicturePath.class.getSimpleName()));
        }
        if (!ProfilePicturePath.isValidPath(this.profilePicturePath)) {
            throw new IllegalValueException(ProfilePicturePath.MESSAGE_PICTURE_CONSTRAINTS);
        }
        final ProfilePicturePath profilePicturePath = new ProfilePicturePath(this.profilePicturePath);

        final MiscellaneousInfo miscellaneousInfo = this.miscellaneousInfo.toModelType();

        if (this.favourite == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Favourite.class.getSimpleName()));
        }
        final Favourite favourite = new Favourite(this.favourite);

        final Dashboard dashboard = this.dashboard.toModelType();

        final Set<Tag> tags = new HashSet<>(studentTags);

        return new Student(uniqueKey, name, phone, email, address, programmingLanguage, tags, favourite, dashboard,
                profilePicturePath, miscellaneousInfo);
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
                && Objects.equals(favourite, otherStudent.favourite)
                && tagged.equals(otherStudent.tagged)
                && dashboard.equals(otherStudent.dashboard);
    }
}
