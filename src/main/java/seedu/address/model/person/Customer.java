package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Customer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Customer extends Person {

    //TODO: create classes for these new fields rather than use primitives
    private final String moneyOwed; //moneyOwed is a formula that DEPENDS on these other new fields
    private final String interestRate;
    //oweStartDate
    //oweDueDate
    //standardInterest
    //lateInterest

    /**
     * Customer constructor
     */
    public Customer(Name name, Phone phone, Email email, Address address, Set<Tag> tags, String moneyOwed,
                    String interestRate) {
        super(name, phone, email, address, tags);
        this.moneyOwed = moneyOwed;
        this.interestRate = interestRate;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */

    public String getMoneyOwed() {
        return moneyOwed;
    }
    //moneyOwed = moneyOwed*(monthlyInterest)^(monthsElapsed)
    public String getInterestRate() {
        return interestRate;
    }

    //TODO: add setter for moneyOwed
    //TODO: add setter for interestRate
    //update moneyOwed based on interest rate?
    //other fields for pertinent information?

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherPerson = (Customer) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getMoneyOwed().equals(this.getMoneyOwed())
                && otherPerson.getInterestRate().equals(this.getInterestRate());

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(),
                getTags(), moneyOwed, interestRate);
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
                .append(" Money Owed: ")
                .append(getMoneyOwed())
                .append(" Interest Rate: ")
                .append(getInterestRate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
