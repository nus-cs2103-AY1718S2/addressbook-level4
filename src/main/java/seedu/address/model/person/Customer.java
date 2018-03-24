package seedu.address.model.person;

import java.util.Date;
import java.util.Optional;
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
    private final double standardInterest; //in percent
    private final double lateInterest; //in percent
    private final Runner runner;

    /**
     * Customer constructor
     */
    public Customer() {
        super();
        this.moneyBorrowed = 0;
        this.oweStartDate = new Date();
        this.oweDueDate = new Date();
        this.standardInterest = 0;
        this.lateInterest = 0;
        this.runner = new Runner();
    }

    public Customer(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                    double moneyBorrowed, Date oweStartDate, Date oweDueDate, double standardInterest,
                    double lateInterest, Runner runner) {
        super(name, phone, email, address, tags);
        this.moneyBorrowed = moneyBorrowed;
        this.standardInterest = standardInterest;
        this.lateInterest = lateInterest;
        this.oweStartDate = oweStartDate;
        this.oweDueDate = oweDueDate;
        this.runner = runner;
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
        final int numOfMsPerWeek = 60 * 60 * 24 * 7 * 1000; //10080 seconds per week; 1000 ms per second

        Date currentDate = new Date();
        long elapsedTime = currentDate.getTime() - oweStartDate.getTime();
        long elapsedWeeks = elapsedTime / numOfMsPerWeek;
        return moneyBorrowed * Math.pow(1 + standardInterest / 100, (double) elapsedWeeks);
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
                .append(getMoneyCurrentlyOwed())
                .append(" Standard Interest Rate: ")
                .append(getStandardInterest())
                .append(" Start Date: ")
                .append(getOweStartDate())
                .append(" Due Date: ")
                .append(getOweDueDate())
                .append(" Runner: ")
                .append(Optional.ofNullable(runner.getName()))
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
