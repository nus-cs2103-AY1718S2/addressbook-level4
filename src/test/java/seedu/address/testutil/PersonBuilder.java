package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_GROUPS = "friends";
    public static final String DEFAULT_PREFERENCES = "computers";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Group> groupTags;
    private Set<Preference> prefTags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        groupTags = SampleDataUtil.getGroupSet(DEFAULT_GROUPS);
        prefTags = SampleDataUtil.getPreferenceSet(DEFAULT_PREFERENCES);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        groupTags = new HashSet<>(personToCopy.getGroupTags());
        prefTags = new HashSet<>(personToCopy.getPreferenceTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code groups} into a {@code Set<Group>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withGroups(String ... groups) {
        this.groupTags = SampleDataUtil.getGroupSet(groups);
        return this;
    }

    /**
     * Parses the {@code preferences} into a {@code Set<Preference>}
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withPreferences(String ... preferences) {
        this.prefTags = SampleDataUtil.getPreferenceSet(preferences);
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

    public Person build() {
        return new Person(name, phone, email, address, groupTags, prefTags);
    }

}
