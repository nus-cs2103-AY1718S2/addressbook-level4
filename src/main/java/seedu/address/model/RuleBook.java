package seedu.address.model;

import seedu.address.model.rule.UniqueRuleList;

/**
 * Stores a list of rules that can be used for notifications, etc.
 */
public class RuleBook implements ReadOnlyRuleBook {

    private final UniqueRuleList rules;

    public RuleBook() {
        rules = new UniqueRuleList();
    }

    /**
     * Creates a RuleBook using the rules in {@code toBeCopied}
     */
    public RuleBook(ReadOnlyRuleBook toBeCopied) {
        this();
    }
}
