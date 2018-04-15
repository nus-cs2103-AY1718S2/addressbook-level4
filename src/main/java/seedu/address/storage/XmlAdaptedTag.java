package seedu.address.storage;

import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedTag {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    @XmlValue
    private String tagName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTag() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName} and no tag type.
     */
    public XmlAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTag(Tag source) {
        tagName = source.tagName + "," + source.tagType.toString();
    }

    //@@author aussiroth
    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName} and {@code tagType}.
     */
    public XmlAdaptedTag(String tagName, String tagType) {
        this.tagName = tagName + "," + tagType;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Tag toModelType() throws IllegalValueException {
        String[] checkTagNameType = tagName.split(",");
        if (!Tag.isValidTagName(checkTagNameType[0])) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        //additional check if the comma exists
        if (checkTagNameType.length == 1) {
            logger.warning("Could not find tag type in file. Initialising it as DEFAULT type.");
            return new Tag(checkTagNameType[0]);
        }
        if (!Tag.isValidTagType(checkTagNameType[1])) {
            logger.warning("Tag Type in file is not recognised. Initialising it as DEFAULT type.");
            checkTagNameType[1] = Tag.AllTagTypes.DEFAULT.toString();
        }
        return new Tag(checkTagNameType[0], Tag.AllTagTypes.valueOf(checkTagNameType[1]));
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTag)) {
            return false;
        }

        return tagName.equals(((XmlAdaptedTag) other).tagName);
    }
}
