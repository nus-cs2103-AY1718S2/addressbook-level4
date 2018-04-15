package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedTag {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tag's %s field is missing!";

    @XmlElement (required = true)
    private String name;
    @XmlElement
    private String color;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTag() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code name} and color undefined.
     */
    public XmlAdaptedTag(String name) {
        this.name = name;
        this.color = "undefined";
    }

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code name} and {@code color}.
     */
    public XmlAdaptedTag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTag(Tag source) {
        name = source.name;
        color = source.color;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Tag toModelType() throws IllegalValueException {
        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name"));
        }
        if (!Tag.isValidTagName(name)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        if (this.color == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Color"));
        }
        if (!Tag.isValidTagColor(color)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_COLOR_CONSTRAINTS);
        }
        return new Tag(name, color);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTag)) {
            return false;
        }

        return name.equals(((XmlAdaptedTag) other).name) && color.equals(((XmlAdaptedTag) other).color);
    }
}
