package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.util.SecurityUtil;

/**
 * Represents the password of the address book
 * Guarantees: current and previous password are present and not null.
 */
public class Password {
    private static final String DEFAULT_PASSWORD = "test";

    private byte[] currentPassword;
    private byte[] prevPassword;

    public Password() {
        currentPassword = SecurityUtil.hashPassword(DEFAULT_PASSWORD);
        prevPassword = currentPassword;
    }

    public Password(String password) {
        currentPassword = SecurityUtil.hashPassword(password);
        prevPassword = currentPassword;
    }

    public Password(byte[] password, byte[] prevPassword) {
        currentPassword = password;
        this.prevPassword = prevPassword;
    }

    public byte[] getPassword() {
        return currentPassword;
    }

    /**
     * Getter for previous password
     * @return previousPassword
     */
    public byte[] getPrevPassword() {
        return prevPassword;
    }

    /**
     * Updates the password.
     * @param password is the password to be used. Cannot be null
     */
    public void updatePassword(Password password) {
        requireNonNull(password);

        prevPassword = password.getPrevPassword();
        currentPassword = password.getPassword();
    }

    /**
     * Similar to {@link #updatePassword(Password)}
     * @param password is the password to be used. Cannot be null
     */
    public void updatePassword(byte[] password) {
        requireNonNull(password);

        prevPassword = currentPassword;
        currentPassword = password;
    }

    @Override
    public String toString() {
        return new String(currentPassword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && Arrays.equals(this.currentPassword, (((Password) other).currentPassword))
                && Arrays.equals(this.prevPassword, (((Password) other).prevPassword))); // state check
    }

    @Override
    public int hashCode() {
        return currentPassword.hashCode();
    }
}
