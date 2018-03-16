package seedu.address.storage;

//@@author karenfrilya97

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Task;

import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask extends XmlAdaptedActivity {

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String name, String dueDateTime, String remark, List<XmlAdaptedTag> tagged) {
        super(name, dueDateTime, remark, tagged);
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        super(source);
    }

    /**
     * Converts this jaxb-friendly adapted Task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        return (Task) super.toModelType();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && other instanceof XmlAdaptedTask;
    }

}
