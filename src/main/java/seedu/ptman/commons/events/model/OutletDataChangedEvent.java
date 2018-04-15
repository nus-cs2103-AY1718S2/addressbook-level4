package seedu.ptman.commons.events.model;

import seedu.ptman.commons.events.BaseEvent;
import seedu.ptman.model.outlet.OutletInformation;

//@@author SunBangjie
/** Indicates the OutletInformation in the model has changed*/
public class OutletDataChangedEvent extends BaseEvent {
    public final OutletInformation data;

    public OutletDataChangedEvent(OutletInformation data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return OutletDataChangedEvent.class.getSimpleName();
    }
}
