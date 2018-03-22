package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    public static final String DUPLICATE_GROUPS_MESSAGE_FORMAT = "Person has duplicate groups!";
    public static final String DUPLICATE_PREFERENCES_MESSAGE_FORMAT = "Person has duplicate preferences!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedGroup> groups = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedPreference> preferences = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, List<XmlAdaptedGroup> groups,
                            List<XmlAdaptedPreference> preferences) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (groups != null) {
            this.groups = new ArrayList<>(groups);
        }
        if (preferences != null) {
            this.preferences = new ArrayList<>(preferences);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        groups = new ArrayList<>();
        for (Group group : source.getGroupTags()) {
            groups.add(new XmlAdaptedGroup(group));
        }

        preferences = new ArrayList<>();
        for (Preference pref : source.getPreferenceTags()) {
            preferences.add(new XmlAdaptedPreference(pref));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Group> personGroups = new ArrayList<>();
        for (XmlAdaptedGroup group : groups) {
            Group groupToAdd = group.toModelType();
            if (personGroups.contains(groupToAdd)) {
                throw new IllegalValueException(DUPLICATE_GROUPS_MESSAGE_FORMAT);
            }
            personGroups.add(groupToAdd);
        }

        final List<Preference> personPreferences = new ArrayList<>();
        for (XmlAdaptedPreference pref: preferences) {
            Preference prefToAdd = pref.toModelType();
            if (personPreferences.contains(prefToAdd)) {
                throw new IllegalValueException(DUPLICATE_PREFERENCES_MESSAGE_FORMAT);
            }
            personPreferences.add(prefToAdd);
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

        final Set<Group> groups = new HashSet<>(personGroups);
        final Set<Preference> preferences = new HashSet<>(personPreferences);
        return new Person(name, phone, email, address, groups, preferences);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && groups.equals(otherPerson.groups);
    }
}
