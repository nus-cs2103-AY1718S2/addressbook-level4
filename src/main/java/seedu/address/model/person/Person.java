package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.policy.Policy;
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
    private final Age age;

    private final UniqueTagList tags;
    private final Income income;
    private final Expenditure actualSpending;
    private final Expenditure expectedSpending;
    private final Optional<Policy> policy;

    /**
     * Every field except actualSpending, expectedSpending must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Income income,
                  Expenditure actualSpending, Expenditure expectedSpending, Age age, Optional<Policy> policy) {
        requireAllNonNull(name, phone, email, address, tags, policy);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.income = income;
        this.actualSpending = actualSpending == null ? new Expenditure(0.0) : actualSpending;
        this.expectedSpending = expectedSpending == null ? new Expenditure(0.0) : expectedSpending;
        this.age = age;
        this.policy = policy;
    }

    /**
     * Special method used solely for machine learning result model update
     *
     * @param weights
     * @return
     */
    public Person updateSelectedField(ArrayList<Double> weights) {
        Double expectedSpending = 0.0;
        expectedSpending += weights.get(0) * this.income.value;


        return new Person(name, phone, email, address, getTags(), income,
                actualSpending, new Expenditure(expectedSpending), age, policy);
    }


    public Name getName() {
        return name;
    }

    public Age getAge() {
        return this.age;
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

    public Income getIncome() {
        return this.income;
    }

    public Expenditure getActualSpending() {
        return this.actualSpending;
    }

    public Expenditure getExpectedSpending() {
        return this.expectedSpending;
    }

    public Optional<Policy> getPolicy() {
        return this.policy;
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
        return Objects.hash(name, phone, email, address, tags, income, actualSpending, expectedSpending, policy);
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
                .append(" Income: ")
                .append(getIncome())
                .append(" ActualSpending: ")
                .append(getActualSpending())
                .append(" ExpectedSpending: ")
                .append(getExpectedSpending())
                .append(" Policy: ");

        if (!policy.isPresent()) {
            builder.append("None");
        } else {
            builder.append(getPolicy().get());
        }

        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
