package seedu.investigapptor.model.person.investigator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.investigapptor.model.crimecase.CrimeCase;
import seedu.investigapptor.model.crimecase.UniqueCrimeCaseList;
import seedu.investigapptor.model.crimecase.exceptions.CrimeCaseNotFoundException;
import seedu.investigapptor.model.crimecase.exceptions.DuplicateCrimeCaseException;
import seedu.investigapptor.model.person.Address;
import seedu.investigapptor.model.person.Email;
import seedu.investigapptor.model.person.Name;
import seedu.investigapptor.model.person.Person;
import seedu.investigapptor.model.person.Phone;
import seedu.investigapptor.model.tag.Tag;

/**
 * Represents a Investigator in the investigapptor book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Investigator extends Person {

    private UniqueCrimeCaseList crimeCases;
    private Rank rank;
    private ArrayList<Integer> caseListHashed;
    /**
     * Every field must be present and not null.
     */
    public Investigator(Name name, Phone phone, Email email, Address address, Rank rank, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.rank = rank;
        crimeCases = new UniqueCrimeCaseList();
    }
    public Investigator(Name name, Phone phone, Email email, Address address, Rank rank,
                        Set<Tag> tags, Set<CrimeCase> cases) {
        super(name, phone, email, address, tags);
        this.rank = rank;
        crimeCases = new UniqueCrimeCaseList(cases);
    }
    public Investigator(Name name, Phone phone, Email email, Address address, Rank rank,
                        Set<Tag> tags, ArrayList<Integer> caseListHashed) {
        super(name, phone, email, address, tags);
        this.rank = rank;
        crimeCases = new UniqueCrimeCaseList();
        this.caseListHashed = caseListHashed;
    }
    public void addCrimeCase(CrimeCase caseToAdd) throws DuplicateCrimeCaseException {
        crimeCases.add(caseToAdd);
    }
    /**
     * Increase the investigator rank by one
     */
    public void promote() throws Exception {
        rank.promote();
    }
    /**
     * Decrease the investigator rank by one
     */
    public void demote() throws Exception {
        rank.demote();
    }
    /**
     * Returns rank in string
     */
    public Rank getRank() {
        return rank;
    }

    @Override
    public boolean isInvestigator() {
        return true;
    }
    /**
     * Returns an immutable crime case set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public ObservableList<CrimeCase> getCrimeCases() {
        return crimeCases.asObservableList();
    }

    /**
     * Returns true if empty
     * else if not empty
     */
    public boolean emptyList() {
        if (getCrimeCases().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void removeCrimeCase(CrimeCase caseToRemove) throws CrimeCaseNotFoundException {
        crimeCases.remove(caseToRemove);
    }

    public ArrayList<Integer> getCaseListHashed() {
        return caseListHashed;
    }
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, rank, tags);
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
                .append(" Rank: ")
                .append(getRank())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" CrimeCases: ");
        getCrimeCases().forEach(builder::append);
        return builder.toString();
    }
}
