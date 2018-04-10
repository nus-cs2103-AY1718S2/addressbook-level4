package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author KevinCJH

/**
 * Loads the redirected url of google login authentication
 */
public class LoadGoogleLoginRedirectEvent extends BaseEvent {

    private String redirectUrl;

    public LoadGoogleLoginRedirectEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
