package seedu.progresschecker.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.progresschecker.model.person.Email;
import seedu.progresschecker.model.person.Major;
import seedu.progresschecker.model.person.Name;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.Phone;
import seedu.progresschecker.model.person.Year;
import seedu.progresschecker.model.tag.Tag;
import seedu.progresschecker.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_MAJOR = "Computer Science";
    public static final String DEFAULT_YEAR = "2";
    public static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Phone phone;
    private Email email;
    private Major major;
    private Year year;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        major = new Major(DEFAULT_MAJOR);
        year = new Year(DEFAULT_YEAR);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        major = personToCopy.getMajor();
        year = personToCopy.getYear();
        tags = new HashSet<>(personToCopy.getTags());
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
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Major} of the {@code Person} that we are building.
     */
    public PersonBuilder withMajor(String major) {
        this.major = new Major(major);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Person} that we are building.
     */
    public PersonBuilder withYear(String year) {
        this.year = new Year(year);
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

    public Person build() {
        return new Person(name, phone, email, major, year, tags);
    }

}
