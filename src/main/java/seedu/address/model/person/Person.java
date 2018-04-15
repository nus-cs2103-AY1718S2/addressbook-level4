package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Comparable<Person> {

    private final Name name;
    private final Phone phone;
    private final Order order;
    private final Address address;
    private final Halal halal;
    private final Vegetarian vegetarian;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Order order, Address address,
                  Halal halal, Vegetarian vegetarian, Set<Tag> tags) {
        requireAllNonNull(name, phone, order, address, halal, vegetarian, tags);
        this.name = name;
        this.phone = phone;
        this.order = order;
        this.address = address;
        this.halal = halal;
        this.vegetarian = vegetarian;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    /**
     * Every field must be present and not null.
     * This method is only used in AddOrder command
     */
    public Person(Name name, Phone phone, Order order, Address address,
                  Halal halal, Vegetarian vegetarian, UniqueTagList tags) {
        requireAllNonNull(name, phone, order, address, halal, vegetarian, tags);
        this.name = name;
        this.phone = phone;
        this.order = order;
        this.address = address;
        this.halal = halal;
        this.vegetarian = vegetarian;
        // here input tags is a UniqueTagList
        this.tags = tags;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    //@@author ZacZequn
    public Order getOrder() {
        return order;
    }
    //@@author

    public Address getAddress() {
        return address;
    }

    //@@author ZacZequn
    public Halal getHalal() {
        return halal;
    }

    public Vegetarian getVegetarian() {
        return vegetarian;
    }
    //@@author

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    /**
     * Returns a UniqueTagList of the person
     */
    public UniqueTagList getTagList() {
        return tags;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getOrder().equals(this.getOrder())
                && otherPerson.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, order, address, halal, vegetarian, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Order: ")
                .append(getOrder())
                .append(" Address: ")
                .append(getAddress())
                .append(" Halal: ")
                .append(getHalal())
                .append(" Vegetarian: ")
                .append(getVegetarian())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }



    @Override
    public int compareTo(Person other) {
        int valueOwn = getValue(this);
        int valueOther = getValue(other);

        if (valueOwn > valueOther) {
            return 1;
        } else if (valueOwn < valueOther) {
            return -1;
        } else {
            return 0;
        }

    }

    private int getValue(Person person) {
        UniqueTagList tagsOwn = person.getTagList();

        Tag processed = new Tag("Processed");
        Tag cooked = new Tag("Cooked");
        Tag delivering = new Tag("Delivering");
        Tag delivered = new Tag("Delivered");

        int valueOwn = 0;

        if (tagsOwn.contains(processed)) {
            valueOwn += 1;
        }
        if (tagsOwn.contains(cooked)) {
            valueOwn -= 2;
        }
        if (tagsOwn.contains(delivering)) {
            valueOwn -= 4;
        }
        if (tagsOwn.contains(delivered)) {
            valueOwn += 10;
        }
        return valueOwn;
    }
}
