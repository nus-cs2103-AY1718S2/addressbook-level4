package seedu.address.commons.events.network;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.SearchCommand.SearchDescriptor;

/**
 * Indicates a request to search for books using the Google Books API.
 */
public class GoogleApiSearchRequestEvent extends BaseEvent {

    public final String searchParameters;

    public GoogleApiSearchRequestEvent(String searchParameters) {
        this.searchParameters = searchParameters;
    }

    @Override
    public String toString() {
        return "searching for: " + searchParameters;
    }
}
