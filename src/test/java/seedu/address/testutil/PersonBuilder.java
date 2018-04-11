package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Expenditure;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Policy;
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
    public static final Double DEFAULT_INCOME = 2000.0;
    public static final Double DEFAULT_ACTUALSPENDING = 2000.0;
    public static final Integer DEFAULT_AGE = 20;
    public static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Income income;
    private Expenditure actualSpending;
    private Expenditure expectedSpending;
    private Age age;
    private Set<Tag> tags;
    private Optional<Policy> policy;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        income = new Income(DEFAULT_INCOME);
        actualSpending = new Expenditure(DEFAULT_ACTUALSPENDING);
        age = new Age(DEFAULT_AGE);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        policy = Optional.empty();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        income = personToCopy.getIncome();
        address = personToCopy.getAddress();
        income = personToCopy.getIncome();
        actualSpending = personToCopy.getActualSpending();
        age = personToCopy.getAge();
        tags = new HashSet<>(personToCopy.getTags());
        policy = personToCopy.getPolicy();
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
    public PersonBuilder withTags(String... tags) {
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
     * Sets the {@code income} of the {@code Person} that we are building.
     */
    public PersonBuilder withIncome(Double income) {
        this.income = new Income(income);
        return this;
    }

    /**
     * Sets the {@code income} of the {@code Person} that we are building.
     */
    public PersonBuilder withActualSpending(Double actualSpending) {
        this.actualSpending = new Expenditure(actualSpending);
        return this;
    }


    /**
     * Sets the {@code age} of the {@code Person} that we are building.
     */
    public PersonBuilder withAge(Integer age) {
        this.age = new Age(age);
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
     * Sets the {@code Policy} of the {@code Person} that we are building.
     */
    public PersonBuilder withPolicy(Optional<Policy> policy) {
        this.policy = policy;
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, income, actualSpending, expectedSpending, age, policy);
    }

}
