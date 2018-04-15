package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the Personal Trainer Pro app.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Height height;
    private final Weight weight;
    private final BodyMassIndex bodyMassIndex;
    private final String bodyMassIndexClassification;
    private final Gender gender;
    private final Age age;
    private final ActivityLevel activityLevel;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Height height,
                  Weight weight, Gender gender, Age age, ActivityLevel activityLevel, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, height, weight, age, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.bodyMassIndex = new BodyMassIndex(height.toString(), weight.toString());
        this.bodyMassIndexClassification = bodyMassIndex.classification;
        this.gender = gender;
        this.age = age;
        this.activityLevel = activityLevel;
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

    public Height getHeight() {
        return height;
    }

    public Weight getWeight() {
        return weight;
    }

    public BodyMassIndex getBodyMassIndex() {
        return bodyMassIndex;
    }

    public String getBodyMassIndexClassification() {
        return bodyMassIndexClassification;
    }

    public Gender getGender() {
        return gender;
    }

    public Age getAge() {
        return age;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
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
        return otherPerson.getName().alphabeticallyEquals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().alphabeticallyEquals(this.getAddress())
                && otherPerson.getHeight().equals(this.getHeight())
                && otherPerson.getWeight().equals(this.getWeight())
                && otherPerson.getGender().equals(this.getGender())
                && otherPerson.getAge().equals(this.getAge())
                && otherPerson.getActivityLevel().equals(this.getActivityLevel());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, height, weight, gender, age, activityLevel, tags);
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
                .append(" Height: ")
                .append(getHeight())
                .append(" Weight: ")
                .append(getWeight())
                .append(" Gender: ")
                .append(getGender())
                .append(" Age: ")
                .append(getAge())
                .append(" Activity Level: ")
                .append(getActivityLevel())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
