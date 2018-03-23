package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.coin.Address;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.Email;
import seedu.address.model.coin.Name;
import seedu.address.model.coin.Phone;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Coin.
 */
public class XmlAdaptedCoin {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Coin's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedCoin.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCoin() {}

    /**
     * Constructs an {@code XmlAdaptedCoin} with the given coin details.
     */
    public XmlAdaptedCoin(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Coin into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCoin
     */
    public XmlAdaptedCoin(Coin source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted coin object into the model's Coin object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted coin
     */
    public Coin toModelType() throws IllegalValueException {
        final List<Tag> coinTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            coinTags.add(tag.toModelType());
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

        final Set<Tag> tags = new HashSet<>(coinTags);
        return new Coin(name, phone, email, address, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCoin)) {
            return false;
        }

        XmlAdaptedCoin otherCoin = (XmlAdaptedCoin) other;
        return Objects.equals(name, otherCoin.name)
                && Objects.equals(phone, otherCoin.phone)
                && Objects.equals(email, otherCoin.email)
                && Objects.equals(address, otherCoin.address)
                && tagged.equals(otherCoin.tagged);
    }
}
