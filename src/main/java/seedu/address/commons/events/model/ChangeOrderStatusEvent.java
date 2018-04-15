//@@author amad-person
package seedu.address.commons.events.model;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.order.Order;

/**
 * Represents a request to change the order status of an existing order in the application.
 */
public class ChangeOrderStatusEvent extends BaseEvent {
    private final Index index;
    private final Order targetOrder;
    private final String orderStatus;

    public ChangeOrderStatusEvent(Index index, Order targetOrder, String orderStatus) {
        this.index = index;
        this.targetOrder = targetOrder;
        this.orderStatus = orderStatus;
    }

    public Index getIndexOrderForStatusChange() {
        return this.index;
    }

    public Order getOrderForStatusChange() {
        return this.targetOrder;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
