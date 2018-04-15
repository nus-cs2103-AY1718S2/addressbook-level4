package seedu.address.model.person;
//@@author ongkc
import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.DebtsList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
/**
 * A list of debtors
 */
public class UniqueDebtorList implements Iterable<Debtor> {

    private final ObservableList<Debtor> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent Debtor as the given argument.
     */
    public boolean contains(Debtor toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }
    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Debtor toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setDebtor(UniqueDebtorList replacement) {
        this.internalList.setAll(replacement.internalList);
    }
    public void setDebtors(DebtsList debtsList) {

        final UniqueDebtorList replacement = new UniqueDebtorList();
        for (DebtsList.Entry<Person, Balance> entry : debtsList.entrySet()) {
            if (entry.getValue().getDoubleValue() < 0) {
                Person person = entry.getKey();
                Balance debt = entry.getValue().getInverse();
                Debtor debtor = new Debtor(person, debt);
                replacement.add(debtor);
            }
        }
        setDebtor(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Debtor> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


    @Override
    public Iterator<Debtor> iterator() {
        return internalList.iterator();
    }
}
