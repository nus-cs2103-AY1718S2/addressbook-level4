package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.order.Order;
import seedu.address.model.order.UniqueOrderList;

import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;
import seedu.address.model.tag.UniqueGroupList;
import seedu.address.model.tag.UniquePreferenceList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;

    private final UniqueGroupList groupTags;
    private final UniquePreferenceList prefTags;
    private final UniqueOrderList orders;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Set<Group> groupTags, Set<Preference> prefTags) {
        requireAllNonNull(name, phone, email, address, groupTags, prefTags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal groups from changes in the arg list
        this.groupTags = new UniqueGroupList(groupTags);
        // protect internal preferences from changes in the arg list
        this.prefTags = new UniquePreferenceList(prefTags);
        // protect internal orders from changes in the arg list
        this.orders = new UniqueOrderList();
    }

    public Person(Name name, Phone phone, Email email, Address address, Set<Group> groupTags,
                  Set<Preference> prefTags, List<Order> orders) {
        requireAllNonNull(name, phone, email, address, groupTags, prefTags, orders);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal groups from changes in the arg list
        this.groupTags = new UniqueGroupList(groupTags);
        // protect internal preferences from changes in the arg list
        this.prefTags = new UniquePreferenceList(prefTags);
        // protect internal orders from changes in the arg list
        this.orders = new UniqueOrderList(orders);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable group set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Group> getGroupTags() {
        return Collections.unmodifiableSet(groupTags.toSet());
    }

    /**
     * Returns an immutable preference set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Preference> getPreferenceTags() {
        return Collections.unmodifiableSet(prefTags.toSet());
    }

    /**
     * Returns an immutable order list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders.asObservableList());
    }

    /**
     * Removes given order if found in order set.
     */
    public void removeOrder(Order orderToRemove) {
        orders.remove(orderToRemove);
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
        return Objects.hash(name, phone, email, address, groupTags, prefTags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Groups: ");
        getGroupTags().forEach(builder::append);
        builder.append(" Preferences: ");
        getPreferenceTags().forEach(builder::append);
        return builder.toString();
    }

}
