package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;
//@@author ongkc
/**
 * Record all transactions to the list.
 *
 */
public class TransactionList implements Iterable<Transaction> {

    private final ObservableList<Transaction> internalList = FXCollections.observableArrayList();


    /**
     * Adds a transaction to the list.
     *
     */
    public void add(Transaction toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Transaction> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Transaction> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && this.internalList.equals(((TransactionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    //@@author phmignot
    /**
     * Removes the equivalent transaction from the list of transactions.
     *
     * @throws TransactionNotFoundException if no such transaction could be found in the list.
     */
    public void remove(Transaction toRemove) throws TransactionNotFoundException {
        requireNonNull(toRemove);
        final boolean transactionFoundAndDeleted = internalList.remove(toRemove);
        if (!transactionFoundAndDeleted) {
            throw new TransactionNotFoundException();
        }
    }
    /**
     * Replaces the list of transactions by a input list of transaction.
     * @param transactions that will be the new transactions' list.
     */
    public void setTransactions(List<Transaction> transactions) {
        requireAllNonNull(transactions);
        internalList.setAll(transactions);
    }

    public void setPerson(Person target, Person editedPerson) throws DuplicatePersonException, PersonNotFoundException {
        for (Transaction transaction : this.asObservableList()) {
            Person payer = transaction.getPayer();
            UniquePersonList payees = transaction.getPayees();
            Transaction editedTransaction = new Transaction(transaction);
            UniquePersonList editedpayees = new UniquePersonList();
            editedpayees.setPersons(payees);
            if (!target.equals(editedPerson) && (payees.contains(editedPerson) || payer.equals(editedPerson))) {
                throw new DuplicatePersonException();
            }
            if (payees.contains(target) && payer.equals(target)) {
                throw new DuplicatePersonException();
            }
            if (payer.equals(target)) {
                editedTransaction.setPayer(editedPerson);
            }
            if (payees.contains(target)) {
                editedpayees.setPerson(target, editedPerson);
                editedTransaction.setPayees(editedpayees);
            }
            internalList.set(internalList.indexOf(transaction), editedTransaction);
        }
    }
}
