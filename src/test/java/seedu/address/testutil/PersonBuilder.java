package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_TEAMNAME = "<UNSPECIFIED>";
    public static final String DEFAULT_RATING = "5";
    public static final String DEFAULT_POSITION = "1";
    public static final String DEFAULT_JERSEY_NUMBER = "17";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private TeamName teamName;
    private Set<Tag> tags;
    private Rating rating;
    private Position position;
    private JerseyNumber jerseyNumber;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
        teamName = new TeamName(DEFAULT_TEAMNAME);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        rating = new Rating(DEFAULT_RATING);
        position = new Position(DEFAULT_POSITION);
        jerseyNumber = new JerseyNumber(DEFAULT_JERSEY_NUMBER);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        remark = personToCopy.getRemark();
        teamName = personToCopy.getTeamName();
        tags = new HashSet<>(personToCopy.getTags());
        rating = personToCopy.getRating();
        position = personToCopy.getPosition();
        jerseyNumber = personToCopy.getJerseyNumber();
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
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code TeamName} of the {@code Person} that we are building.
     */
    public PersonBuilder withTeamName(String teamName) {
        this.teamName = new TeamName(teamName);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Person} that we are building.
     */
    public PersonBuilder withRating(String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code Position} of the {@code Person} that we are building.
     */
    public PersonBuilder withPosition(String position) {
        this.position = new Position(position);
        return this;
    }

    /**
     * Sets the {@code JerseyNumber} of the {@code Person} that we are building.
     */
    public PersonBuilder withJerseyNumber(String jerseyNumber) {
        this.jerseyNumber = new JerseyNumber(jerseyNumber);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, remark, teamName, tags, rating, position, jerseyNumber);
    }

}
