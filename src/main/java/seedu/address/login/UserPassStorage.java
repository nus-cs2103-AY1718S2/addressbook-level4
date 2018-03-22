package seedu.address.login;

import java.util.HashMap;

/**
 * Storage for UserPass
 */
public class UserPassStorage extends HashMap<String, String> {
    public void put(UserPass userpass) {
        this.put(userpass.getUsername(), userpass.getPassword());
    }
}
