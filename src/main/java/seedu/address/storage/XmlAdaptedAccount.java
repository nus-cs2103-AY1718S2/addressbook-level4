//@@author Jason1im
package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Account;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB-friendly version of the Account.
 */
@XmlRootElement(name = "accountData")
public class XmlAdaptedAccount {

    @XmlAttribute(name = "username")
    private String username;
    @XmlAttribute(name = "password")
    private String password;

    /**
     * Constructs an XmlAdaptedAccount.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAccount() {}

    /**
     * Constructs an {@code XmlAdaptedAccount} with the given details.
     */
    public XmlAdaptedAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given Account into this class for JAXB use.
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAccount(Account source) {
        username = source.getUsername();
        password = source.getPassword();
    }

    /**
     * Converts this jaxb-friendly adapted skill object into the model's Account object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Account toModelType() throws IllegalValueException {
        if (!Account.isValidUsername(username)) {
            throw new IllegalValueException(Account.MESSAGE_USERNAME_CONSTRAINTS);
        } else if (!Account.isValidPassword(password)) {
            throw new IllegalValueException(Account.MESSAGE_PASSWORD_CONSTRAINTS);
        } else {
            return new Account(username, password);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAccount)) {
            return false;
        }

        return username.equals(((XmlAdaptedAccount) other).username)
                && password.equals(((XmlAdaptedAccount) other).password);
    }
}
