package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author KevinChuangCH
/**
 * Represents a search for person on all available social media platforms.
 */
public class SearchPersonOnAllPlatformEvent extends BaseEvent {

    private final String searchName;

    public SearchPersonOnAllPlatformEvent(String searchName) {
        this.searchName = searchName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getSearchName() {
        return searchName;
    }

}
