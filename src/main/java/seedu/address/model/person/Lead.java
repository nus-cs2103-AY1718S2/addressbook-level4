//@@author WoodyLau
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
public class Lead extends Person {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Remark remark;
    private final Type type;

    private final UniqueTagList tags;

    private String company = null;
    private String industry = null;
    private int rating = 0;
    private String title = null;
    private String website = null;

    /**
     * Every field must be present and not null.
     */
    public Lead(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.type = new Type("Lead");
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

    public Remark getRemark() {
        return remark;
    }

    public Type getType() {
        return type;
    }

    public void setCompany(String newCompany) {
        this.company = newCompany;
    }

    public void setIndustry(String newIndustry) {
        this.industry = newIndustry;
    }

    public void setRating(int newRating) {
        if (newRating > 0 && newRating < 6) {
            this.rating = newRating;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompany() {
        return this.company;
    }

    public String getIndustry() {
        return this.industry;
    }

    public int getRating() {
        return this.rating;
    }

    public String getTitle() {
        return this.title;
    }

    public String getWebsite() {
        return this.website;
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

        if (!(other instanceof Lead)) {
            return false;
        }

        Lead otherPerson = (Lead) other;
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
                .append(" Type: ")
                .append(getType())
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
