//@@author zacci
package seedu.address.model;

/**
 * Represents User's Session.
 */
public class Session {

    private String username;
    private int securityLevel;
    private boolean isLoggedIn = false;

    public Session() {
        resetSession();
    }

    /**
     * Sets session details for user upon successful login
     */
    public void login(String username, int securityLevel) {
        this.username = username;
        this.securityLevel = securityLevel;
        this.isLoggedIn = true;
    }

    public void logout() {
        resetSession();
    }

    private void resetSession() {
        username = "";
        securityLevel = 0;
        isLoggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public boolean checkIsLoggedIn () {
        return isLoggedIn;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof Session)) {
            return false;
        }

        // state check
        Session other = (Session) obj;
        return username.equals(other.username)
                && securityLevel == other.securityLevel;
    }
}
