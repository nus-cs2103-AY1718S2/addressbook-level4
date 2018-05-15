package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookShelf;

/** Indicates the BookShelf in the model has changed */
public class BookShelfChangedEvent extends BaseEvent {

    public final ReadOnlyBookShelf data;

    public BookShelfChangedEvent(ReadOnlyBookShelf data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of books " + data.size();
    }
}
