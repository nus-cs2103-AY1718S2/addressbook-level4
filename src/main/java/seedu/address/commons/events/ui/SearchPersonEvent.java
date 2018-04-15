package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author KevinChuangCH
/**
 * Represents a search for person.
 */
public class SearchPersonEvent extends BaseEvent {

    private final String searchName;
    private final String platformToSearch;

    public SearchPersonEvent(String platformToSearch, String searchName) {
        this.searchName = searchName;
        this.platformToSearch = platformToSearch;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getSearchName() {
        return searchName;
    }

    public String getPlatform() {
        return platformToSearch;
    }

}
