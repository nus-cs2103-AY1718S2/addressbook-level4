package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.dashboard.Date;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.Progress;
import seedu.address.model.student.dashboard.Task;
import seedu.address.model.student.dashboard.UniqueTaskList;

/**
 * JAXB-friendly adapted version of the Milestone.
 */
public class XmlAdaptedMilestone {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Milestone's %s field is missing!";


    @XmlElement (required = true)
    private String dueDate;
    @XmlElement (required = true)
    private String progress;
    @XmlElement (required = true)
    private String description;

    @XmlElement
    private List<XmlAdaptedTask> tasksList;

    /**
     * Constructs an XmlAdaptedMilestone.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMilestone() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedMilestone(List<XmlAdaptedTask> tasksList, String dueDate, String progress, String description) {
        if (tasksList != null) {
            this.tasksList = new ArrayList<>(tasksList);
        }
        this.dueDate = dueDate;
        this.progress = progress;
        this.description = description;
    }

    /**
     * Converts a given Milestone into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMilestone
     */
    public XmlAdaptedMilestone(Milestone source) {
        tasksList = new ArrayList<>();
        for (Task task : source.getTaskList()) {
            tasksList.add(new XmlAdaptedTask(task));
        }
        dueDate = source.getDueDate().getValue();
        progress = source.getProgress().getValue();
        description = source.getDescription();
    }

    /**
     * Converts this jaxb-friendly adapted milestone object into the model's Milestone object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted milestone
     */
    public Milestone toModelType() throws IllegalValueException {
        final UniqueTaskList modelTaskList = new UniqueTaskList();
        if (tasksList != null) {
            for (XmlAdaptedTask task : tasksList) {
                modelTaskList.add(task.toModelType());
            }
        }

        if (this.dueDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        final Date modelDate = new Date(this.dueDate);

        if (this.progress == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Progress.class.getSimpleName()));
        }
        final Progress modelProgress = new Progress(this.progress);

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }
        final String modelDescription = this.description;

        return new Milestone(modelDate, modelTaskList, modelProgress, modelDescription);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMilestone)) {
            return false;
        }

        XmlAdaptedMilestone otherMilestone = (XmlAdaptedMilestone) other;
        return Objects.equals(dueDate, otherMilestone.dueDate)
                && Objects.equals(progress, otherMilestone.progress)
                && Objects.equals(description, otherMilestone.description)
                && tasksList.equals(otherMilestone.tasksList);
    }
}
