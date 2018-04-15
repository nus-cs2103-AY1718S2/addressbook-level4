package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.pair.PairHash;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_PRICE = "1234";
    public static final String DEFAULT_SUBJECT = "English";
    public static final String DEFAULT_LEVEL = "Lower Sec";
    public static final String DEFAULT_STATUS = "Not Matched";
    public static final String DEFAULT_ROLE = "Student";
    public static final String DEFAULT_REMARK = "Hardworking but slow learner.";
    public static final String DEFAULT_RATE = "3.0";
    public static final int DEFAULT_RATECOUNT = 1;
    public static final Set<PairHash> DEFAULT_PAIRHASH = PairHash.getDefaultPairHashSet();

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Price price;
    private Subject subject;
    private Level level;
    private Status status;
    private Role role;
    private Set<Tag> tags;
    private Remark remark;
    private Rate rate;
    private Set<PairHash> pairHashes;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        price = new Price(DEFAULT_PRICE);
        subject = new Subject(DEFAULT_SUBJECT);
        level = new Level(DEFAULT_LEVEL);
        status = new Status(DEFAULT_STATUS);
        role = new Role(DEFAULT_ROLE);
        tags = SampleDataUtil.getTagSet();
        remark = new Remark(DEFAULT_REMARK);
        rate = new Rate(Double.parseDouble(DEFAULT_RATE), true);
        rate.setCount(DEFAULT_RATECOUNT);
        pairHashes = DEFAULT_PAIRHASH;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        price = personToCopy.getPrice();
        subject = personToCopy.getSubject();
        level = personToCopy.getLevel();
        status = personToCopy.getStatus();
        role = personToCopy.getRole();
        tags = new HashSet<>(personToCopy.getTags());
        remark = personToCopy.getRemark();
        rate = personToCopy.getRate();
        pairHashes = personToCopy.getPairHashes();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        HashSet<Tag> setTags = new HashSet<>();
        for (String tagString : tags) {
            setTags.add(new Tag(tagString));
        }
        this.tags = setTags;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Level} of the {@code Person} that we are building.
     */
    public PersonBuilder withLevel(String level) {
        this.level = new Level(level);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Person} that we are building.
     */
    public PersonBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Person} that we are building.
     */
    public PersonBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Person} that we are building.
     */
    public PersonBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the required attribute tags for the person
     */
    private void setTags() {
        if (!price.toString().equals("")) {
            tags.add(new Tag(price.toString(), Tag.AllTagTypes.PRICE));
        }
        if (!subject.toString().equals("")) {
            tags.add(new Tag(subject.toString(), Tag.AllTagTypes.SUBJECT));
        }
        if (!level.toString().equals("")) {
            tags.add(new Tag(level.toString(), Tag.AllTagTypes.LEVEL));
        }
        if (!status.toString().equals("")) {
            tags.add(new Tag(status.toString(), Tag.AllTagTypes.STATUS));
        }
        if (!role.toString().equals("")) {
            tags.add(new Tag(role.toString(), Tag.AllTagTypes.ROLE));
        }
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code Person} that we are building.
     */
    public PersonBuilder withRate(String rate, String rateCount) {
        this.rate = new Rate(Double.parseDouble(rate), true);
        this.rate.setCount(Integer.parseInt(rateCount));

        return this;
    }

    //@@author alexawangzi
    /**
     * Sets the {@code pairHash} of the {@code Person} that we are building.
     */
    public PersonBuilder withPairhash(PairHash pairHash) {
        Set<PairHash> pairHashesSet = new HashSet<PairHash>();
        pairHashesSet.add(pairHash);
        this.pairHashes = pairHashesSet;
        return this;
    }

    public PersonBuilder withPairhash(int value) {
        return this.withPairhash(new PairHash(value));
    }

    //@@author
    /**
     * Builds a person based off the attributes in this class
     * @return Person with set attributes
     */
    public Person build() {
        setTags();
        return new Person(name, phone, email, address, price, subject, level, status, role, tags,
                          remark, rate, pairHashes);
    }

}
