package seedu.address.commons.events.model;

import java.util.HashMap;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyCustomerStats;

/** Indicates the CustomerStats in the model has changed*/
public class CustomerStatsChangedEvent extends BaseEvent {

    public final ReadOnlyCustomerStats data;

    public CustomerStatsChangedEvent(ReadOnlyCustomerStats data) {
        this.data = data;
    }

    @Override
    public String toString() {
        HashMap<String, Integer> ordersCount = data.getOrdersCount();
        return ordersCount.toString();
    }
}
