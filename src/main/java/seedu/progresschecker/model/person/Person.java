package seedu.progresschecker.model.person;

import static seedu.progresschecker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.progresschecker.model.tag.Tag;
import seedu.progresschecker.model.tag.UniqueTagList;

/**
 * Represents a Person in ProgressChecker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private static String defaultPath = "/images/profile_photo.jpg";

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final GithubUsername username;
    private final Major major;
    private final Year year;
    private String photoPath;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, GithubUsername username, Major major, Year year,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, username, major, year, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.major = major;
        this.year = year;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.photoPath = defaultPath;
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

    //@@author EdwardKSG
    public GithubUsername getUsername() {
        return username;
    }

    public Major getMajor() {
        return major;
    }

    public Year getYear() {
        return year;
    }
    //@@author

    public String getPhotoPath() {
        return photoPath;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public void updatePhoto(String path) {
        this.photoPath = path;
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
                && otherPerson.getUsername().equals(this.getUsername())
                && otherPerson.getMajor().equals(this.getMajor())
                && otherPerson.getYear().equals(this.getYear());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, username, major, year, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Github Username: ")
                .append(getUsername())
                .append(" Major: ")
                .append(getMajor())
                .append(" Year of Study: ")
                .append(getYear())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
