package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.Password;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPassword {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private byte[] currPassword;

    @XmlElement(required = true)
    private byte[] prevPassword;

    /**
     * Constructs an XmlAdaptedPassword.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPassword() {}

    /**
     * Constructs an {@code XmlAdaptedPassword} with the given password.
     */
    public XmlAdaptedPassword(Password password) {
        this.currPassword = password.getPassword();
        this.prevPassword = password.getPrevPassword();
    }

    /**
     * Converts this jaxb-friendly adapted password object into the model's Password object.
     *
     */
    public Password toModelType() {
        return new Password(currPassword, prevPassword);
    }

    /**
     * Updates the password given a new password
     * @param password is the password to be changed to
     */
    public void updatePassword(Password password) {
        this.currPassword = password.getPassword();
        this.prevPassword = password.getPrevPassword();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPassword)) {
            return false;
        }

        XmlAdaptedPassword otherPassword = (XmlAdaptedPassword) other;
        return Objects.equals(currPassword, otherPassword.currPassword)
                && Objects.equals(prevPassword, otherPassword.prevPassword);
    }
}
