package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.RuleCard;

/**
 * Represents a selection change in the Rule List Panel
 */
public class RulePanelSelectionChangedEvent extends BaseEvent {


    private final RuleCard newSelection;

    public RulePanelSelectionChangedEvent(RuleCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public RuleCard getNewSelection() {
        return newSelection;
    }
}
