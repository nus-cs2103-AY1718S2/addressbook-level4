package seedu.address.model.rule;

import static java.util.Objects.requireNonNull;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of rules that enforce uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueRuleList {

    private final ObservableList<Rule> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Rule toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a rule to the list.
     *
     * @throws DuplicateRuleException if the rule to add is a duplicate of an existing rule in the list.
     */
    public void add(Rule toAdd) throws DuplicateRuleException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRuleException();
        }
        internalList.add(toAdd);
    }
}
