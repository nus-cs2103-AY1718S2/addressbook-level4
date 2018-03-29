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
public class Person {

    private final Name name;
    private final Phone phone;
    private final Order order;
    private final Email email;
    private final Address address;
    private final Halal halal;
    private final Vegetarian vegetarian;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Order order, Email email, Address address,
                  Halal halal, Vegetarian vegetarian, Set<Tag> tags) {
        requireAllNonNull(name, phone, order, email, address, halal, vegetarian, tags);
        this.name = name;
        this.phone = phone;
        this.order = order;
        this.email = email;
        this.address = address;
        this.halal = halal;
        this.vegetarian = vegetarian;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Order getOrder() {
        return order;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Halal getHalal() {
        return halal;
    }

    public Vegetarian getVegetarian() {
        return vegetarian;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
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
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, order, email, address, halal, vegetarian, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Order: ")
                .append(getOrder())
                .append(" Email: ")
                .append(getEmail())
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

}
