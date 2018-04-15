//@@author phmignot
package seedu.address.model.transaction;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that the {@code Person} involved in a transaction matches the {@code Person} given.
 */
public class TransactionContainsPersonPredicate implements Predicate<Transaction> {

    private final Person person;

    public TransactionContainsPersonPredicate(Person person) {
        this.person = person;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.isImplied(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionContainsPersonPredicate // instanceof handles nulls
                && this.person.equals(((TransactionContainsPersonPredicate) other).person)); // state check
    }

}
