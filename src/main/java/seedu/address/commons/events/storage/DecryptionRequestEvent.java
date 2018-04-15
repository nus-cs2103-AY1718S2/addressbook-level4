package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;
//@@author tinyjy94
/**
 * Indicates a request for decryption
 */
public class DecryptionRequestEvent extends BaseEvent {
    private final String password;

    public DecryptionRequestEvent(String password) {
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
