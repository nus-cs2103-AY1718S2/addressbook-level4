package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Group.
 */
public class XmlAdaptedGroup {

    @XmlValue
    private String groupName;

    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {}

    /**
     * Constructs a {@code XmlAdaptedGroup} with the given {@code groupName}.
     */
    public XmlAdaptedGroup(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedGroup(Group source) {
        groupName = source.tagName;
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group
     */
    public Group toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(groupName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Group(groupName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedGroup)) {
            return false;
        }
        return groupName.equals(((XmlAdaptedGroup) other).groupName);
    }
}
