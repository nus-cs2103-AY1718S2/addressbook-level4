//@@author ewaldhew
package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.UniqueRuleList;
import seedu.address.model.rule.exceptions.DuplicateRuleException;
import seedu.address.model.rule.exceptions.RuleNotFoundException;

/**
 * Stores a list of rules that can be used for notifications, etc.
 * Duplicates are not allowed (by .equals comparison)
 */
public class RuleBook implements ReadOnlyRuleBook {

    private final UniqueRuleList rules;

    public RuleBook() {
        rules = new UniqueRuleList();
    }

    public RuleBook(ReadOnlyRuleBook toBeCopied) {
        rules = new UniqueRuleList();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setRules(List<Rule> rules) throws DuplicateRuleException {
        this.rules.setRules(rules);
    }

    /**
     * Resets the existing data of this {@code RuleBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRuleBook newData) {
        requireNonNull(newData);
        List<Rule> ruleList = newData.getRuleList();

        try {
            setRules(ruleList);
        } catch (DuplicateRuleException e) {
            throw new AssertionError("RuleBooks should not have duplicate rules");
        }
    }

    //// rule-level operations

    /**
     * Adds a rule to the address book.
     *
     * @throws DuplicateRuleException if an equivalent rule already exists.
     */
    public void addRule(Rule rule) throws DuplicateRuleException {
        rules.add(rule);
    }

    /**
     * Replaces the given rule {@code target} in the list with {@code editedRule}.
     *
     * @throws DuplicateRuleException if updating the rule's details causes the rule to be equivalent to
     *      another existing rule in the list.
     * @throws RuleNotFoundException if {@code target} could not be found in the list.
     */
    public void updateRule(Rule target, Rule editedRule)
            throws DuplicateRuleException, RuleNotFoundException {
        requireNonNull(editedRule);
        rules.setRule(target, editedRule);
    }

    /**
     * Removes {@code key} from this {@code RuleBook}.
     * @throws RuleNotFoundException if the {@code key} is not in this {@code RuleBook}.
     */
    public boolean removeRule(Rule key) throws RuleNotFoundException {
        if (rules.remove(key)) {
            return true;
        } else {
            throw new RuleNotFoundException();
        }
    }

    //// util methods

    @Override
    public String toString() {
        return rules.asObservableList().size() + " rules registered";
    }

    @Override
    public ObservableList<Rule> getRuleList() {
        return rules.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RuleBook // instanceof handles nulls
                && this.rules.equals(((RuleBook) other).rules));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(rules);
    }

}
