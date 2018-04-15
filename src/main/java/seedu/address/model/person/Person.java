package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
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
    private final Email email;
    private final Address address;
    private final Role role;

    private final UniqueTagList tags;

    private boolean isInCell = false;

    /**
     * New Constructor for working
     * Every field must be present and not null.
     * This is for adding people into the prisonbook database and by default, they are not imprisoned
     */
    public Person(Name name, Phone phone, Email email, Address address, Role role, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    //for storage purposes and tests
    public Person(Name name, Phone phone, Email email, Address address, Role role, Set<Tag> tags, boolean isInCell) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.isInCell = isInCell;
    }

    /**
     * Constructor for updatedPrisoner after being added to cell
     * @param person is prisoner being added to cell
     * @param isInCell is true if adding to cell
     * @param cellAddress is cell prisoner is entering
     */
    public Person (Person person, boolean isInCell, String cellAddress) {
        requireAllNonNull(person, isInCell);
        this.name = person.getName();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        this.address = new Address(cellAddress + " [Old address: " + person.getAddress().toString() + "]");
        this.role = person.getRole();
        this.tags = new UniqueTagList(person.getTags());
        this.isInCell = isInCell;
    }

    /**
     * Constructor for freedPrisoner after being deleted from cell
     * @param person deleted from cell
     * @param isInCell is false because deleting from cell
     */
    public Person (Person person, boolean isInCell) {
        requireNonNull(person);
        this.name = person.getName();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        String addressString = person.getAddress().toString();
        this.address = new Address(addressString.substring(
                addressString.indexOf(": ") + 2, addressString.indexOf("]")));
        this.role = person.getRole();
        this.tags = new UniqueTagList(person.getTags());
        this.isInCell = isInCell;
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

    //@@author sarahgoh97
    //only called if prisoner isInCell
    public Address getCellAddress() {
        assert(isInCell);
        String cellAddress = address.value.substring(0, address.value.indexOf("[") - 1);
        return new Address(cellAddress);
    }
    //@@author

    public Role getRole() {
        return role;
    }

    public boolean getIsInCell() {
        return isInCell;
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
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getRole().equals(this.getRole())
                && otherPerson.getIsInCell() == this.getIsInCell();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
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
                .append(" Role: ")
                .append(getRole())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
