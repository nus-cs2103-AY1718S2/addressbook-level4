package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.dashboard.Task;

/**
 * JAXB-friendly adapted version of the Task.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private static final String COMPLETED = "completed";
    private static final String NOT_COMPLETED = "not completed";

    @XmlElement (required = true)
    private String name;
    @XmlElement (required = true)
    private String description;
    @XmlElement (required = true)
    private String isCompleted;

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String name, String description, String isCompleted) {
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedStudent
     */
    public XmlAdaptedTask(Task source) {
        name = source.getName();
        description = source.getDescription();
        isCompleted = source.isCompleted() ? COMPLETED : NOT_COMPLETED;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }

        if (this.isCompleted == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "isCompleted"));
        }

        return this.isCompleted.equals(COMPLETED) ? new Task(name, description, true)
                : new Task(name, description, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTask)) {
            return false;
        }

        XmlAdaptedTask otherTask = (XmlAdaptedTask) other;
        return Objects.equals(name, otherTask.name)
                && Objects.equals(description, otherTask.description)
                && Objects.equals(isCompleted, otherTask.isCompleted);
    }
}
