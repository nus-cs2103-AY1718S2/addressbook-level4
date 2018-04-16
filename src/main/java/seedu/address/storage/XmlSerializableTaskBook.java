package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;

//@@author CYX28
/**
 * An Immutable TaskBook that is serializable to XML format
 */
@XmlRootElement(name = "taskbook")
public class XmlSerializableTaskBook {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTaskCategory> taskCategories;

    /**
     * Creates an empty XmlSerializableTaskBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTaskBook() {
        tasks = new ArrayList<>();
        taskCategories = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTaskBook(ReadOnlyTaskBook src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        taskCategories.addAll(src.getTaskCategoryList().stream().map(XmlAdaptedTaskCategory::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this taskbook into the model's {@code TaskBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedTask} or {@code XmlAdaptedTaskCategory}.
     */
    public TaskBook toModelType() throws IllegalValueException {
        TaskBook taskBook = new TaskBook();
        for (XmlAdaptedTaskCategory c : taskCategories) {
            taskBook.addTaskCategory(c.toModelType());
        }
        for (XmlAdaptedTask task : tasks) {
            taskBook.addTask(task.toModelType());
        }
        return taskBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableTaskBook)) {
            return false;
        }

        XmlSerializableTaskBook otherTb = (XmlSerializableTaskBook) other;
        return tasks.equals(otherTb.tasks) && taskCategories.equals(otherTb.taskCategories);
    }

}
