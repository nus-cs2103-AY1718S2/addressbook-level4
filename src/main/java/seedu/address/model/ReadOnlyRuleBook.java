//@@author ewaldhew
package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.rule.Rule;

/**
 * Unmodifiable view of a rule book
 */
public interface ReadOnlyRuleBook {

    /**
     * Returns an unmodifiable view of the rules list.
     * This list will not contain any duplicate rules.
     */
    ObservableList<Rule> getRuleList();

}
