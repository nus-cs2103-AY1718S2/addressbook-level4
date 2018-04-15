package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.smplatform.SocialMediaPlatform;
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
    private final Map<String, SocialMediaPlatform> smpMap;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Map<String, SocialMediaPlatform> socialMediaPlatformMap, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.smpMap = socialMediaPlatformMap;
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

    public Address getAddress() {
        return address;
    }

    //@@author Nethergale
    /**
     * Returns an immutable social media platform map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<String, SocialMediaPlatform> getSocialMediaPlatformMap() {
        return Collections.unmodifiableMap(smpMap);
    }

    //@@author
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    //@@author Nethergale
    /**
     * Returns a person comparator, which compares the names alphabetically.
     * Similar names are compared lexicographically.
     */
    public static Comparator<Person> nameComparator() {
        return Comparator.comparing((Person p) -> p.getName().toString(), (
            s1, s2) -> (s1.compareToIgnoreCase(s2) == 0) ? s1.compareTo(s2) : s1.compareToIgnoreCase(s2));
    }

    //@@author
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
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    //@@author KevinChuangCH
    /**
     * Returns the tag names of tags of a person as String.
     * @return a string of all the tag names of tags of a person.
     */
    public String getTagsAsString() {
        final StringBuilder builder = new StringBuilder();
        for (String tag : tags.arrayOfTags()) {
            builder.append(tag);
            builder.append(" ");
        }
        return builder.toString();
    }

}
