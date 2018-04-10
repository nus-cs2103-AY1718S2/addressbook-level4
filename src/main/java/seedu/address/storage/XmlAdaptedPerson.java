package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.CurrentPosition;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.skill.Skill;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String currentPosition;
    @XmlElement(required = true)
    private String company;
    @XmlElement(required = true)
    private String profilePicture;

    @XmlElement
    private List<XmlAdaptedSkill> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedJob.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedJob} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, String currentPosition,
                            String company, String profilePicture, List<XmlAdaptedSkill> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.currentPosition = currentPosition;
        this.company = company;
        this.profilePicture = profilePicture;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedJob
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        currentPosition = source.getCurrentPosition().value;
        company = source.getCompany().value;
        profilePicture = source.getProfilePicture().filePath;

        tagged = new ArrayList<>();
        for (Skill skill : source.getSkills()) {
            tagged.add(new XmlAdaptedSkill(skill));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Skill> personSkills = new ArrayList<>();
        for (XmlAdaptedSkill tag : tagged) {
            personSkills.add(tag.toModelType());
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

        //@@author kush1509
        if (this.currentPosition == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CurrentPosition.class.getSimpleName()));
        }
        if (!CurrentPosition.isValidCurrentPosition(this.currentPosition)) {
            throw new IllegalValueException(CurrentPosition.MESSAGE_CURRENT_POSITION_CONSTRAINTS);
        }
        final CurrentPosition currentPosition = new CurrentPosition(this.currentPosition);

        if (this.company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(this.company)) {
            throw new IllegalValueException(Company.MESSAGE_COMPANY_CONSTRAINTS);
        }
        final Company company = new Company(this.company);

        //@@author
        if (this.profilePicture != null) {
            if (!ProfilePicture.isValidProfilePicture(this.profilePicture)) {
                throw new IllegalValueException(ProfilePicture.MESSAGE_PROFILEPICTURE_CONSTRAINTS);
            } else if (!ProfilePicture.hasValidProfilePicture(this.profilePicture)) {
                throw new IllegalValueException(ProfilePicture.MESSAGE_PROFILEPICTURE_NOT_EXISTS);
            }
        }
        final ProfilePicture profilePicture = new ProfilePicture(this.profilePicture);
        final Set<Skill> skills = new HashSet<>(personSkills);
        return new Person(name, phone, email, address, currentPosition, company, profilePicture, skills);
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
                && Objects.equals(currentPosition, otherPerson.currentPosition)
                && Objects.equals(company, otherPerson.company)
                && Objects.equals(profilePicture, otherPerson.profilePicture)
                && tagged.equals(otherPerson.tagged);
    }
}
