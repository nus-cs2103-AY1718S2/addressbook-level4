package seedu.address.model.rule;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Rule objects.
 */
public class DuplicateRuleException extends DuplicateDataException {
    public DuplicateRuleException() {
        super("Operation would result in duplicate rule");
    }
}
