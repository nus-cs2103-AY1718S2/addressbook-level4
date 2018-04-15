//@@author ZhangYijiong
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.OrderCard;

/**
 * Gets an event the panel change selection
 */
public class OrderPanelSelectionChangedEvent extends BaseEvent {

    private final OrderCard newSelection;

    public OrderPanelSelectionChangedEvent(OrderCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public OrderCard getNewSelection() {
        return newSelection;
    }
}
