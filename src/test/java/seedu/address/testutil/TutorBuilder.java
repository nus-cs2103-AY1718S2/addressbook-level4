package seedu.address.testutil;

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
import seedu.address.model.person.Status;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class TutorBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@example.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_PRICE = "100";
    public static final String DEFAULT_SUBJECT = "english";
    public static final String DEFAULT_LEVEL = "lower Sec";
    public static final String DEFAULT_STATUS = "not Matched";
    public static final String DEFAULT_TAGS = "tutor";
    public static final String DEFAULT_REMARK = "Patient and approachable.";
    public static final String DEFAULT_RATE = "3.0";
    public static final int DEFAULT_RATECOUNT = 1;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Price price;
    private Subject subject;
    private Level level;
    private Status status;
    private Set<Tag> tags;
    private Remark remark;
    private Rate rate;

    public TutorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        price = new Price(DEFAULT_PRICE);
        subject = new Subject(DEFAULT_SUBJECT);
        level = new Level(DEFAULT_LEVEL);
        status = new Status(DEFAULT_STATUS);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        remark = new Remark(DEFAULT_REMARK);
        rate = new Rate(Double.parseDouble(DEFAULT_RATE), true);
        rate.setCount(DEFAULT_RATECOUNT);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TutorBuilder(Person personToCopy) {
        super();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public TutorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public TutorBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public TutorBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public TutorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public TutorBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Level} of the {@code Person} that we are building.
     */
    public TutorBuilder withLevel(String level) {
        this.level = new Level(level);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Person} that we are building.
     */
    public TutorBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Person} that we are building.
     */
    public TutorBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Person} that we are building.
     */
    public TutorBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    public void setTags() {
        tags.add(new Tag(price.toString()));
        tags.add(new Tag(subject.toString()));
        tags.add(new Tag(level.toString()));
        tags.add(new Tag(status.toString()));
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public TutorBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code Person} that we are building.
     */
    public TutorBuilder withRate(String rate, String rateCount) {
        this.rate = new Rate(Double.parseDouble(rate), true);
        this.rate.setCount(Integer.parseInt(rateCount));
        return this;
    }

    /**
     * Builds a stutor based off the attributes in this class
     * @return Tutor with set attributes
     */
    public Tutor build() {
        setTags();
        return new Tutor(name, phone, email, address, price, subject, level, status, tags,
                         remark, rate, PairHash.getDefaultPairHashSet());
    }

}
