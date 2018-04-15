package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.CurrentPosition;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.skill.Skill;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_CURRENT_POSITION = "Software Engineer";
    public static final String DEFAULT_COMPANY = "Google";
    public static final String DEFAULT_PROFILE_PICTURE =
            "./src/test/data/images/alice.jpeg";
    public static final String DEFAULT_SKILLS = "friends";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private ProfilePicture profilePicture;
    private CurrentPosition currentPosition;
    private Company company;
    private Set<Skill> skills;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        currentPosition = new CurrentPosition(DEFAULT_CURRENT_POSITION);
        company = new Company(DEFAULT_COMPANY);
        profilePicture = new ProfilePicture(DEFAULT_PROFILE_PICTURE);
        skills = SampleDataUtil.getSkillSet(DEFAULT_SKILLS);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        currentPosition = personToCopy.getCurrentPosition();
        company = personToCopy.getCompany();
        profilePicture = personToCopy.getProfilePicture();
        skills = new HashSet<>(personToCopy.getSkills());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<Skill>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSkills(String ... skills) {
        this.skills = SampleDataUtil.getSkillSet(skills);
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
     * Sets the {@code CurrentPosition} of the {@code Person} that we are building.
     */
    public PersonBuilder withCurrentPosition(String currentPosition) {
        this.currentPosition = new CurrentPosition(currentPosition);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Person} that we are building.
     */
    public PersonBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Sets the {@code ProfilePicture} of the {@code Person} that we are building.
     */
    public PersonBuilder withProfilePicture(String... profilePicture) {
        if (profilePicture.length == 0) {
            this.profilePicture = new ProfilePicture();
        } else {
            this.profilePicture = new ProfilePicture(profilePicture[0]);
        }
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, currentPosition, company, profilePicture, skills);
    }

}
