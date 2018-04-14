package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.Alias;

//@@author jingyinno
/**
 * JAXB-friendly adapted version of the Alias.
 */
public class XmlAdaptedAlias {

    @XmlElement(required = true)
    private String command;
    @XmlElement(required = true)
    private String aliasName;

    /**
     * Constructs an XmlAdaptedAlias.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAlias() {}

    /**
     * Constructs a {@code XmlAdaptedAlias} with the given {@code command} and {@code aliasName}.
     */
    public XmlAdaptedAlias(String command, String aliasName) {
        this.command = command;
        this.aliasName = aliasName;
    }

    /**
     * Converts a given Alias into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAlias.
     */
    public XmlAdaptedAlias(Alias source) {
        command = source.getCommand();
        aliasName = source.getAlias();
    }

    /**
     * Converts this jaxb-friendly adapted alias object into the model's Alias object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted alias.
     */
    public Alias toModelType() throws IllegalValueException {
        if (this.command == null) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);
        }
        if (!Alias.isValidAliasParameter(command)) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);
        }
        final String command = this.command;

        if (this.aliasName == null) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);
        }
        if (!Alias.isValidAliasParameter(aliasName)) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);
        }
        final String aliasName = this.aliasName;

        return new Alias(command, aliasName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAlias)) {
            return false;
        }

        XmlAdaptedAlias otherAlias = (XmlAdaptedAlias) other;
        return Objects.equals(aliasName, otherAlias.aliasName)
                && Objects.equals(command, otherAlias.command);
    }
}
