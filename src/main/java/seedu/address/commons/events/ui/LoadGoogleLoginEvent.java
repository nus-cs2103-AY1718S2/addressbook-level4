package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author KevinCJH

/**
 * Loads the url of google authentication
 */
public class LoadGoogleLoginEvent extends BaseEvent {

    private final String authenticationUrl;

    public LoadGoogleLoginEvent(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getAuthenticationUrl() {
        return authenticationUrl;
    }
}
