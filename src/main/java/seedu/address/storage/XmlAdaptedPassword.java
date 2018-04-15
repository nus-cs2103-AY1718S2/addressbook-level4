package seedu.address.storage;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.Password;

//@@author yeggasd
/**
 * JAXB-friendly version of the Password.
 */
public class XmlAdaptedPassword {

    @XmlElement
    private byte[] currPassword;

    @XmlElement
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPassword)) {
            return false;
        }

        XmlAdaptedPassword otherPassword = (XmlAdaptedPassword) other;
        return Arrays.equals(currPassword, otherPassword.currPassword)
                && Arrays.equals(prevPassword, otherPassword.prevPassword);
    }
}
