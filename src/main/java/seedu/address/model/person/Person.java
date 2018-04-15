package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.pair.PairHash;
import seedu.address.model.pair.UniquePairHashList;
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
    private final Remark remark;
    private final Rate rate;



    private final UniqueTagList tags;
    private final UniquePairHashList pairHashes;


    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Price price, Subject subject,
                  Level level, Status status, Role role, Set<Tag> tags, Remark remark, Rate rate,
                  Set<PairHash> pairHashes) {

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
        this.remark = remark;
        this.rate = rate;

        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);

        this.pairHashes = new UniquePairHashList(pairHashes);
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

    public Remark getRemark() {
        return remark;
    }

    public Rate getRate() {
        return rate;
    }

    /**
     * Returns an immutable tag list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * The tag list is sorted according to their tag type, and order is as defined in the enum.
     */
    public List<Tag> getTags() {
        Set<Tag> setTags = tags.toSet();
        List<Tag> tagsAsList = new ArrayList<>(setTags);
        Collections.sort(tagsAsList);
        return Collections.unmodifiableList(tagsAsList);
    }

    public Set<PairHash> getPairHashes() {
        return Collections.unmodifiableSet(pairHashes.toSet());
    }

    public void addPairHash(PairHash pairHash) throws UniquePairHashList.DuplicatePairHashException {
        pairHashes.add(pairHash);
    }

    //@@author alexawangzi
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
                && otherPerson.getLevel().equals(this.getLevel())
                && otherPerson.getSubject().equals(this.getSubject())
                && otherPerson.getPrice().equals(this.getPrice())
                && otherPerson.getRole().equals(this.getRole());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, price, subject, level, status, role, tags,
                            remark, rate, pairHashes);
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
                .append(getAddress());
        return builder.toString();
    }

    //@@author alexawangzi
    public boolean isMatched() {
        return (getStatus().value.equals("Matched"));
    }

    //@@author alexawangzi
    public boolean containsPairHash(PairHash pairHash) {
        return (pairHashes.contains(pairHash));
    }

    //@@author alexawangzi
    /**
     * Check if a person has missing fields
     * @return
     */
    public boolean hasMissingFieldForMatch() {
        return this.getSubject().value.equals("")
                || this.getLevel().value.equals("")
                || this.getRole().value.equals("")
                || this.getPrice().value.equals("");
    }
}
