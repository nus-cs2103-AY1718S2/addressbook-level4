//@@author Jason1im
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to switch between login view and main app view.
 */
public class LoginEvent extends BaseEvent {
    public final boolean isLogin;

    public LoginEvent(boolean value) { this.isLogin = value; }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
