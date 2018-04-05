package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * JAXB-friendly version of a filepath.
 */
public class XmlAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Filepath is missing.";

    @XmlElement(required = true)
    private String filepath;

    /**
     * Constructs an XmlAdaptedItem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedItem() {}

    /**
     * Constructs an {@code XmlAdaptedItem} with the given item details.
     */
    public XmlAdaptedItem(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Converts this jaxb-friendly adapted Item object into a string
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item
     */
    public String toModelType() throws IllegalValueException {

        if (this.filepath == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }

        return filepath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedItem)) {
            return false;
        }

        XmlAdaptedItem otherTask = (XmlAdaptedItem) other;
        return Objects.equals(filepath, otherTask.filepath);
    }
}
