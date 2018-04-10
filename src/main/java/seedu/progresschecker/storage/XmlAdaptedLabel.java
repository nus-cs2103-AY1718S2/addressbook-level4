package seedu.progresschecker.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.model.issues.Labels;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedLabel {

    @XmlValue
    private String label;

    /**
     * Constructs an XmlAdaptedAssignee.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLabel() {}

    /**
     * Constructs a {@code XmlAdaptedAssignee} with the given {@code assignee}.
     */
    public XmlAdaptedLabel(String label) {
        this.label = label;
    }

    /**
     * Converts a given Assignee into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedLabel(Labels source) {
        label = source.fullLabels;
    }

    /**
     * Converts this jaxb-friendly adapted assignee object into the model's Assignee object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Labels toModelType() throws IllegalValueException {
        return new Labels(label);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAssignee)) {
            return false;
        }

        return label.equals(((XmlAdaptedLabel) other).label);
    }
}
