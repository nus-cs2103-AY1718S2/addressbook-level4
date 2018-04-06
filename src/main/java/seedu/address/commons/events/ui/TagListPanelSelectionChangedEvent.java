package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TagCard;

//@@author yong-jie
/**
 * Represents a selection change in the Tag List Panel
 */
public class TagListPanelSelectionChangedEvent extends BaseEvent {

    private final TagCard newSelection;

    public TagListPanelSelectionChangedEvent(TagCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TagCard getNewSelection() {
        return newSelection;
    }
}
