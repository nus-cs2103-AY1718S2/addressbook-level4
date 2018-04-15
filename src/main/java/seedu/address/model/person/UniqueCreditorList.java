package seedu.address.model.person;
//@@author ongkc
import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.DebtsList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
/**
 * A list of creditors
 */

public class UniqueCreditorList implements Iterable<Creditor> {

    private final ObservableList<Creditor> internalList = FXCollections.observableArrayList();

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
    public void add(Creditor toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setCreditors(UniqueCreditorList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setCreditors(DebtsList debtsList) {

        final UniqueCreditorList replacement = new UniqueCreditorList();
        for (DebtsList.Entry<Person, Balance> entry : debtsList.entrySet()) {
            if (entry.getValue().getDoubleValue() > 0) {
                Person person = entry.getKey();
                Balance debt = entry.getValue();
                Creditor creditor = new Creditor(person, debt);
                replacement.add(creditor);
            }
        }
        setCreditors(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Creditor> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


    @Override
    public Iterator<Creditor> iterator() {
        return internalList.iterator();
    }
}
