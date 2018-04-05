package seedu.address.model.account;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.scope.ScopeBuilder;

/**
 * Represents an Account in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Account {

    private static final String APP_ID = "366902457122089";
    private static final String REDIRECT_URL = "https://www.facebook.com/connect/login_success.html";

    private final Username username;
    private final Password password;

    private ScopeBuilder scopeBuilder;
    private FacebookClient client;

    /**
     * Every field must be present and not null.
     */
    public Account(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;

        setScopeBuilder();
        setClient();
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    /**
     * Sets the scope builder which is a set of permissions.
     */
    private void setScopeBuilder() {
        scopeBuilder = new ScopeBuilder();
    }

    /**
     * Sets the client used to get an access token.
     */
    private void setClient() {
        client = new DefaultFacebookClient(Version.VERSION_2_12);
    }

    /**
     * Returns the login dialog url using {@link #client}.
     */
    public String getLoginDialogUrl() {
        return client.getLoginDialogUrl(APP_ID, REDIRECT_URL, scopeBuilder);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Account)) {
            return false;
        }

        Account otherAccount = (Account) other;
        return otherAccount.getUsername().equals(this.getUsername())
                && otherAccount.getPassword().equals(this.getPassword());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Username: " + getUsername() + " Password: " + getPassword();
    }
}
