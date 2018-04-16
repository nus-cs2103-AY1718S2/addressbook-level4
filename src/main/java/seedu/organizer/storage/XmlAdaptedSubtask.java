package seedu.organizer.storage;

//@@author aguss787
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Status;

/**
 * JAXB-friendly adapted version of the Subtask.
 */
public class XmlAdaptedSubtask {

    @XmlElement(required = true)
    private String subtaskName;
    @XmlElement(required = true)
    private boolean subtaskStatus;

    /**
     * Constructs an XmlAdaptedSubtask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSubtask() {}

    /**
     * Constructs a {@code XmlAdaptedSubtask} with the given {@code subtaskName} and {@code subtaskStatus}.
     */
    public XmlAdaptedSubtask(String subtaskName, Boolean subtaskStatus) {
        this.subtaskName = subtaskName;
        this.subtaskStatus = subtaskStatus;
    }

    /**
     * Converts a given Subtask into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedSubtask(Subtask source) {
        subtaskName = source.getName().fullName;
        subtaskStatus = source.getStatus().value;
    }

    /**
     * Converts this jaxb-friendly adapted subtask object into the model's Subtask object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted subtask
     */
    public Subtask toModelType() throws IllegalValueException {
        if (!Name.isValidName(subtaskName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Subtask(new Name(subtaskName), new Status(subtaskStatus));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof XmlAdaptedSubtask)) {
            return false;
        }

        XmlAdaptedSubtask that = (XmlAdaptedSubtask) o;
        return subtaskStatus == that.subtaskStatus
                && Objects.equals(subtaskName, that.subtaskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subtaskName, subtaskStatus);
    }
}
