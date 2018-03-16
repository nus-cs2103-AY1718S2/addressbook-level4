package seedu.organizer.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private Boolean status;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {
    }

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String name, String priority, String deadline, String description,
                          Boolean status, List<XmlAdaptedTag>
            tagged) {
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.description = description;
        this.status = status;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        name = source.getName().fullName;
        priority = source.getPriority().value;
        deadline = source.getDeadline().toString();
        description = source.getDescription().value;
        status = source.getStatus().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(this.priority)) {
            throw new IllegalValueException(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        final Priority priority = new Priority(this.priority);

        if (this.deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName
                    ()));
        }
        if (!Deadline.isValidDeadline(this.deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        }
        final Deadline deadline = new Deadline(this.deadline);

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description description = new Description(this.description);

        if (this.status == null) {
            this.status = false;
        }
        final Status status = new Status(this.status);

        final Set<Tag> tags = new HashSet<>(personTags);
        return new Task(name, priority, deadline, description, status, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTask)) {
            return false;
        }

        XmlAdaptedTask otherPerson = (XmlAdaptedTask) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(priority, otherPerson.priority)
                && Objects.equals(deadline, otherPerson.deadline)
                && Objects.equals(description, otherPerson.description)
                && Objects.equals(status, otherPerson.status)
                && tagged.equals(otherPerson.tagged);
    }
}
