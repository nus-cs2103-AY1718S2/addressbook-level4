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
import seedu.address.model.person.JerseyNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.TeamName;

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
    private Boolean phonePrivacy;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private Boolean emailPrivacy;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private Boolean addressPrivacy;
    @XmlElement(required = true)
    private String remark;
    @XmlElement(required = true)
    private Boolean remarkPrivacy;
    @XmlElement(required = true)
    private String teamName;
    @XmlElement(required = true)
    private String rating;
    @XmlElement(required = true)
    private Boolean ratingPrivacy;
    @XmlElement(required = true)
    private String position;
    @XmlElement(required = true)
    private String jerseyNumber;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        this.remarkPrivacy = false;
        this.phonePrivacy = false;
        this.addressPrivacy = false;
        this.emailPrivacy = false;

        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
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
        remark = source.getRemark().value;
        teamName = source.getTeamName().fullName;

        phonePrivacy = source.getPhone().isPrivate();
        emailPrivacy = source.getEmail().isPrivate();
        addressPrivacy = source.getAddress().isPrivate();
        remarkPrivacy = source.getRemark().isPrivate();
        ratingPrivacy = source.getRating().isPrivate();

        tagged = new ArrayList<>();
        rating = source.getRating().value;
        position = source.getPosition().value;
        jerseyNumber = source.getJerseyNumber().value;

        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (phonePrivacy == null) {
            phonePrivacy = false;
        }

        if (emailPrivacy == null) {
            emailPrivacy = false;
        }

        if (addressPrivacy == null) {
            addressPrivacy = false;
        }

        if (remarkPrivacy == null) {
            remarkPrivacy = false;
        }

        if (ratingPrivacy == null) {
            ratingPrivacy = false;
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
        final Phone phone = new Phone(this.phone, this.phonePrivacy);

        if (this.email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(this.email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email email = new Email(this.email, this.emailPrivacy);

        if (this.address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(this.address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }

        final Address address = new Address(this.address, this.addressPrivacy);
        final Remark remark = new Remark(this.remark, this.remarkPrivacy);
        final TeamName teamName = new TeamName(this.teamName);

        final Set<Tag> tags = new HashSet<>(personTags);

        if (this.rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName()));
        }
        if (!Rating.isValidRating(this.rating)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        final Rating rating = new Rating(this.rating, this.ratingPrivacy);

        if (this.position == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Position.class.getSimpleName()));
        }
        if (!Position.isValidPosition(this.position)) {
            throw new IllegalValueException(Position.MESSAGE_POSITION_CONSTRAINTS);
        }
        final Position position = new Position(this.position);

        if (this.jerseyNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JerseyNumber.class.getSimpleName()));
        }
        if (!JerseyNumber.isValidJerseyNumber(this.jerseyNumber)) {
            throw new IllegalValueException(JerseyNumber.MESSAGE_JERSEY_NUMBER_CONSTRAINTS);
        }
        final JerseyNumber jerseyNumber = new JerseyNumber(this.jerseyNumber);

        return new Person(name, phone, email, address, remark, teamName, tags, rating, position, jerseyNumber);
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
                && tagged.equals(otherPerson.tagged);
    }
}
