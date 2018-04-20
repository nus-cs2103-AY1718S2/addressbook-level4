package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyImdb;

/** Indicates the Imdb in the model has changed*/
public class ImdbChangedEvent extends BaseEvent {

    public final ReadOnlyImdb data;

    public ImdbChangedEvent(ReadOnlyImdb data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
