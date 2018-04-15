package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Halal;
import seedu.address.model.person.Name;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vegetarian;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ORDER = "Chicken Rice";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_HALAL = "Non-halal";
    public static final String DEFAULT_VEGETARIAN = "Non-vegetarian";
    public static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Phone phone;
    private Order order;
    private Address address;
    private Halal halal;
    private Vegetarian vegetarian;

    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        order = new Order(DEFAULT_ORDER);
        address = new Address(DEFAULT_ADDRESS);
        halal = new Halal(DEFAULT_HALAL);
        vegetarian = new Vegetarian(DEFAULT_VEGETARIAN);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        order = personToCopy.getOrder();
        address = personToCopy.getAddress();
        halal = personToCopy.getHalal();
        vegetarian = personToCopy.getVegetarian();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    //@@author ZacZequn
    /**
     * Sets the {@code Order} of the {@code Person} that we are building.
     */
    public PersonBuilder withOrder(String order) {
        this.order = new Order(order);
        return this;
    }
    //@@author

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
    //@@author ZacZequn
    /**
     * Sets the {@code Halal} of the {@code Person} that we are building.
     */
    public PersonBuilder withHalal(String halal) {
        this.halal = new Halal(halal);
        return this;
    }

    /**
     * Sets the {@code Vegetarian} of the {@code Person} that we are building.
     */
    public PersonBuilder withVegetarian(String vegetarian) {
        this.vegetarian = new Vegetarian(vegetarian);
        return this;
    }
    //@@author


    public Person build() {
        return new Person(name, phone, order, address, halal, vegetarian, tags);
    }

}
