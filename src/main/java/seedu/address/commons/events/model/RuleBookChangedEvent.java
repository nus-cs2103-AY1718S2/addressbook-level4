package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyRuleBook;

/** Indicates the RuleBook in the model has changed*/
public class RuleBookChangedEvent extends BaseEvent {

    public final ReadOnlyRuleBook data;

    public RuleBookChangedEvent(ReadOnlyRuleBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of rules " + data.getRuleList().size();
    }
}
