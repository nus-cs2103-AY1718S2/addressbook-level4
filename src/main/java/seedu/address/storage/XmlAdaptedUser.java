//@@author zacci
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.user.User;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedUser {

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
    private int securityLevel;

    /**
     * Constructs an XmlAdaptedUser.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedUser(String username, String password, int securityLevel) {
        this.username = username;
        this.password = password;
        this.securityLevel = securityLevel;
    }

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedUser
     */
    public XmlAdaptedUser(User source) {
        username = source.getUsername();
        password = source.getPassword();
        securityLevel = source.getSecurityLevel();
    }

    /**
     * Converts this jaxb-friendly adapted user object into the model's User object.
     *
     */
    public User toModelType() {
        return new User(username, password, securityLevel);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUser)) {
            return false;
        }

        XmlAdaptedUser otherUser = (XmlAdaptedUser) other;
        return this.username.equals(((XmlAdaptedUser) other).username)
                && this.password.equals(((XmlAdaptedUser) other).password)
                && this.securityLevel == (((XmlAdaptedUser) other).securityLevel);
    }
}
