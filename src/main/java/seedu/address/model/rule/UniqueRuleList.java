package seedu.address.model.rule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.rule.exceptions.DuplicateRuleException;
import seedu.address.model.rule.exceptions.RuleNotFoundException;

/**
 * A list of rules that enforce uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueRuleList implements Iterable<Rule> {

    private final ObservableList<Rule> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent element as the given argument.
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

    /**
     * Replaces the rule {@code target} in the list with {@code editedRule}.
     *
     * @throws DuplicateRuleException if the replacement is equivalent to another existing rule in the list.
     * @throws RuleNotFoundException if {@code target} could not be found in the list.
     */
    public void setRule(Rule target, Rule editedRule)
            throws DuplicateRuleException, RuleNotFoundException {
        requireNonNull(editedRule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RuleNotFoundException();
        }

        if (!target.equals(editedRule) && internalList.contains(editedRule)) {
            throw new DuplicateRuleException();
        }

        internalList.set(index, editedRule);
    }

    /**
     * Removes the equivalent rule from the list.
     *
     * @throws RuleNotFoundException if no such rule could be found in the list.
     */
    public boolean remove(Rule toRemove) throws RuleNotFoundException {
        requireNonNull(toRemove);
        final boolean ruleFoundAndDeleted = internalList.remove(toRemove);
        if (!ruleFoundAndDeleted) {
            throw new RuleNotFoundException();
        }
        return ruleFoundAndDeleted;
    }

    public void setRules(UniqueRuleList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setRules(List<Rule> rules) throws DuplicateRuleException {
        requireAllNonNull(rules);
        final UniqueRuleList replacement = new UniqueRuleList();
        for (final Rule rule : rules) {
            replacement.add(rule);
        }
        setRules(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Rule> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Rule> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRuleList // instanceof handles nulls
                && this.internalList.equals(((UniqueRuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
