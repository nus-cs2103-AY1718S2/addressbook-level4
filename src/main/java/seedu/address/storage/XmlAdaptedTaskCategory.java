package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.TaskCategory;

/**
 * JAXB-friendly version of the Task Category.
 */
public class XmlAdaptedTaskCategory {

    @XmlValue
    private String taskCategoryName;

    /**
     * Constructs an XmlAdaptedTaskCategory.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTaskCategory() {}

    /**
     * Constructs a {@code XmlAdaptedTaskCategory} with the given {@code taskCategoryName}.
     */
    public XmlAdaptedTaskCategory(String taskCategoryName) {
        this.taskCategoryName = taskCategoryName;
    }

    /**
     * Converts a given TaskCategory into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTaskCategory.
     */
    public XmlAdaptedTaskCategory(TaskCategory source) {
        taskCategoryName = source.taskCategoryName;
    }

    /**
     * Converts this jaxb-friendly adapted taskCategory object into the model's TaskCategory object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public TaskCategory toModelType() throws IllegalValueException {
        if (!TaskCategory.isValidTaskCategoryName(taskCategoryName)) {
            throw new IllegalValueException(TaskCategory.MESSAGE_TASK_CATEGORY_CONSTRAINTS);
        }
        return new TaskCategory(taskCategoryName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTaskCategory)) {
            return false;
        }

        return taskCategoryName.equals(((XmlAdaptedTaskCategory) other).taskCategoryName);
    }

}
