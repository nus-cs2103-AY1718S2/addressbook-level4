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
    private final Email email;
    private final Address address;
    private final Price price;
    private final Subject subject;
    private final Level level;
    private final Role role;
    private Status status;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Price price, Subject subject,
                  Level level, Status status, Role role, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, price, subject, level, status, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.price = price;
        this.subject = subject;
        this.level = level;
        this.role = role;
        this.status = status;

        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
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

    public Price getPrice() {
        return price;
    }

    public Subject getSubject() {
        return subject;
    }

    public Level getLevel() {
        return level;
    }

    public Role getRole() {
        return role;
    }


    public Status getStatus() {
        return status;
    }

    public Address getAddress() {
        return address;
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
        return Objects.hash(name, phone, email, address, price, subject, level, status, role, tags);
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
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
