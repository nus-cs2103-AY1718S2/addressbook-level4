package seedu.address.model.login;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

//@@author kaisertanqr
/**
 * Represents a User of the addressbook
 *
 */
public class User {

    public static final String AB_FILEPATH_PREFIX = "data/addressbook-";
    public static final String AB_FILEPATH_POSTFIX = ".xml";

    private Username username;
    private Password password;
    private String addressBookFilePath;

    public User() {
        this.username = new Username("default");
        this.password = new Password("password");
        this.addressBookFilePath = "data/addressbook-default.xml";
    }

    public User(Username username, Password password) {
        this(username, password, AB_FILEPATH_PREFIX + username + AB_FILEPATH_POSTFIX);
    }

    public User(Username username, Password password, String addressBookFilePath) {
        requireAllNonNull(username, password, addressBookFilePath);
        this.username = username;
        this.password = password;
        this.addressBookFilePath = addressBookFilePath;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public String getAddressBookFilePath() {
        return addressBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherPerson = (User) other;
        return otherPerson.getUsername().equals(this.getUsername())
                && otherPerson.getPassword().equals(this.getPassword())
                && otherPerson.getAddressBookFilePath().equals(this.getAddressBookFilePath());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password, addressBookFilePath);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUsername())
                .append(" Username: ")
                .append(getPassword())
                .append(" Password: ")
                .append(getAddressBookFilePath())
                .append(" File Path: ");
        return builder.toString();
    }

}
