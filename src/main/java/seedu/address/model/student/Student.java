package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.dashboard.Dashboard;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final ProgrammingLanguage programmingLanguage;
    private final Favourite favourite;
    private final Dashboard dashboard;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Address address, ProgrammingLanguage programmingLanguage,
                   Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = new Favourite(false); // Default value
        this.dashboard = new Dashboard();
    }

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Address address, ProgrammingLanguage programmingLanguage,
                   Set<Tag> tags, Favourite fav) {
        requireAllNonNull(name, phone, email, address, tags, fav);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = fav;
        this.dashboard = new Dashboard();
    }

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Address address, ProgrammingLanguage programmingLanguage,
                   Set<Tag> tags, Favourite fav, Dashboard dashboard) {
        requireAllNonNull(name, phone, email, address, tags, fav);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = fav;
        this.dashboard = dashboard;
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

    public Favourite getFavourite() {
        return favourite;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    /**
     * Returns true if Student is in favourites, else returns false.
     */
    public boolean isFavourite() {
        if (favourite.value.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
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

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(this.getName())
                && otherStudent.getPhone().equals(this.getPhone())
                && otherStudent.getEmail().equals(this.getEmail())
                && otherStudent.getAddress().equals(this.getAddress())
                && otherStudent.getProgrammingLanguage().equals(this.getProgrammingLanguage())
                && otherStudent.getDashboard().equals(this.getDashboard());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, programmingLanguage, dashboard);
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
                .append(" Programming Language: ")
                .append(getProgrammingLanguage())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Favourite: ")
                .append(getFavourite())
                .append(" Dashboard: ")
                .append(getDashboard());
        return builder.toString();
    }

}
