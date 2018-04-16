package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;


/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;

    private final UniqueTagList tags;

    private Picture picture;
    //@@author dezhanglee
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Picture pic, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.picture = pic;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }
    //@@author
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.picture = new Picture();
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Person(Person source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getPicture(),
                source.getTags());
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
    //@@author dezhanglee
    public Picture getPicture() {
        return picture;
    }
    //@@author
    public UniqueTagList getUniqueTagList() {
        return tags;
    }
    //@@author dezhanglee
    /**
     * Update contact picture to that located in path
     * @param path
     */
    public void updatePicture(String path) {
        int hash = this.hashCode();
        String filename = String.valueOf(hash);
        this.picture = new Picture(path, filename);
    }

    /**
     * Resets the current picture to a default picture.
     */
    public void resetPicture() {
        this.picture = new Picture();
    }

    /**
     * Set profile picture to that in path
     */
    public void setPicture(String path) throws IllegalValueException {

        Picture oldPic = this.picture;
        try {
            int fileName = this.hashCode();
            this.picture = new Picture(path, String.valueOf(fileName));
        } catch (Exception e) {
            this.picture = oldPic; //reset picture back to default
            throw new IllegalValueException(Picture.MESSAGE_PICTURE_CONSTRAINTS);
        }
    }

    //@@author dezhanglee
    /**
     * Adds tags from {@code toAdd} to existing Person tag list, throws exception if there are duplicate tags
     * @param toAdd
     * @throws DuplicateTagException
     */
    public void addTags(Set<Tag> toAdd) throws DuplicateTagException {

        for (Tag t : toAdd) {
            this.tags.add(t);
        }
    }
    //@@author


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

}
