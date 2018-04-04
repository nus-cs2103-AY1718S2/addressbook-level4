package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;
//@@author tinyjy94
/**
 * Indicates a request for encryption
 */
public class EncryptionRequestEvent extends BaseEvent {
    private final String password;

    public EncryptionRequestEvent(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return password;
    }

    public String getPassword() {
        return password;
    }
}
