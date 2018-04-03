package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.skill.Skill;
import seedu.address.model.skill.UniqueSkillList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * Two persons can have the same profilePicture
 */
public class Person {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final ProfilePicture profilePicture;
    private final CurrentPosition currentPosition;
    private final Company company;

    private final UniqueSkillList skills;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, CurrentPosition currentPosition,
                  Company company, ProfilePicture profilePicture, Set<Skill> skills) {
        requireAllNonNull(name, phone, email, address, skills, currentPosition, company);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.currentPosition = currentPosition;
        this.company = company;

        if (profilePicture == null) {
            this.profilePicture = new ProfilePicture();
        } else {
            this.profilePicture = profilePicture;
        }
        // protect internal skills from changes in the arg list
        this.skills = new UniqueSkillList(skills);
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

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public Company getCompany() {
        return company;
    }

    public CurrentPosition getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Returns an immutable skill set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Skill> getSkills() {
        return Collections.unmodifiableSet(skills.toSet());
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
                && otherPerson.getCurrentPosition().equals(this.getCurrentPosition())
                && otherPerson.getCompany().equals(this.getCompany());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, profilePicture, currentPosition, company, skills);
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
                .append(" Current Position: ")
                .append(getCurrentPosition())
                .append(" Company: ")
                .append(getCompany())
                .append(" Skills: ");
        getSkills().forEach(builder::append);
        return builder.toString();
    }
}
