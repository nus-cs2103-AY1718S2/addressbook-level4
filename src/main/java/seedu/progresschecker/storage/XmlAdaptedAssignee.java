package seedu.progresschecker.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedAssignee {

    @XmlValue
    private String assignee;

    /**
     * Constructs an XmlAdaptedAssignee.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAssignee() {}

    /**
     * Constructs a {@code XmlAdaptedAssignee} with the given {@code assignee}.
     */
    public XmlAdaptedAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * Converts a given Assignee into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAssignee(Assignees source) {
        assignee = source.fullAssignees;
    }

    /**
     * Converts this jaxb-friendly adapted assignee object into the model's Assignee object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Assignees toModelType() throws IllegalValueException {
        return new Assignees(assignee);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAssignee)) {
            return false;
        }

        return assignee.equals(((XmlAdaptedAssignee) other).assignee);
    }
}
