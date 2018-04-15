package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

//@@author shookshire
/**
 * A utility class to help with building Client objects.
 */
public class ClientBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_LOCATION = "north";
    public static final String DEFAULT_GRADE = "p3";
    public static final String DEFAULT_SUBJECT = "physics";
    public static final String DEFAULT_CATEGORY = "s";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Location location;
    private Grade grade;
    private Subject subject;
    private Category category;
    private Set<Tag> tags;

    public ClientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        location = new Location(DEFAULT_LOCATION);
        grade = new Grade(DEFAULT_GRADE);
        subject = new Subject(DEFAULT_SUBJECT);
        category = new Category(DEFAULT_CATEGORY);
    }

    /**
     * Initializes the ClientBuilder with the data of {@code ClientToCopy}.
     */
    public ClientBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        phone = clientToCopy.getPhone();
        email = clientToCopy.getEmail();
        address = clientToCopy.getAddress();
        tags = new HashSet<>(clientToCopy.getTags());
        location = clientToCopy.getLocation();
        grade = clientToCopy.getGrade();
        subject = clientToCopy.getSubject();
    }

    /**
     * Sets the {@code Name} of the {@code Client} that we are building.
     */
    public ClientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Client} that we are building.
     */
    public ClientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Client} that we are building.
     */
    public ClientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Client} that we are building.
     */
    public ClientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Client} that we are building.
     */
    public ClientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Client} that we are building.
     */
    public ClientBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code Client} that we are building.
     */
    public ClientBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Client} that we are building.
     */
    public ClientBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Client} that we are building.
     */
    public ClientBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    public Client build() {
        return new Client(name, phone, email, address, tags, location, grade, subject, category);
    }

}
