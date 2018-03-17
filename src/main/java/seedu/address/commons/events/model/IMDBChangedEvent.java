package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyIMDB;

/** Indicates the IMDB in the model has changed*/
public class IMDBChangedEvent extends BaseEvent {

    public final ReadOnlyIMDB data;

    public IMDBChangedEvent(ReadOnlyIMDB data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
