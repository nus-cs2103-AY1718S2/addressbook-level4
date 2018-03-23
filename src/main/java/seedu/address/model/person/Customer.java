package seedu.address.model.person;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Customer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Customer extends Person {

    //TODO: create classes for these new fields rather than use primitives???
    private final double moneyBorrowed; //moneyOwed is a formula that DEPENDS on these other new fields
    private final Date oweStartDate;
    private final Date oweDueDate;
    private final double standardInterest;
    private final double lateInterest;

    /**
     * Customer constructor
     */
    public Customer(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.moneyBorrowed = 0;
        this.standardInterest = 0;
        this.lateInterest = 0;
        this.oweStartDate = new Date();
        this.oweDueDate = new Date();
    }

    public double getMoneyBorrowed() {
        return moneyBorrowed;
    }

    public Date getOweStartDate() {
        return oweStartDate;
    }

    public Date getOweDueDate() {
        return oweDueDate;
    }

    public double getStandardInterest() {
        return standardInterest;
    }

    public double getLateInterest() {
        return lateInterest;
    }

    /**
     * @return amount of money owed, after compounded standardInterest, based on num of weeks that has passed since
     * oweStartDate
     */
    public double getMoneyCurrentlyOwed() {
        final int numOfMsInAWeek = 10080 * 1000;
        Date currentDate = new Date();
        long elapsedTime = currentDate.getTime() - oweStartDate.getTime();
        long elapsedWeeks = elapsedTime / numOfMsInAWeek;
        return moneyBorrowed * Math.pow(standardInterest, (double) elapsedWeeks);
    }

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
                && otherPerson.getAddress().equals(this.getAddress());

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(),
                getTags());
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
