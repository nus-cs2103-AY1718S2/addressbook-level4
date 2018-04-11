package seedu.organizer.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.recurrence.Recurrence;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.DateAdded;
import seedu.organizer.model.task.DateCompleted;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.User;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String updatedPriority;
    @XmlElement(required = true)
    private String basePriority;
    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private String dateadded;
    @XmlElement(required = true)
    private String datecompleted;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private Boolean status;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedSubtask> subtasks = new ArrayList<>();

    @XmlElement(required = true)
    private XmlAdaptedUser user;
    @XmlElement(required = true)
    private XmlAdaptedRecurrence recurrence;

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {
    }

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String name, String updatedPriority, String basePriority, String deadline, String dateadded,
                          String datecompleted, String description, Boolean status, List<XmlAdaptedTag> tagged,
                          List<XmlAdaptedSubtask> subtasks, XmlAdaptedUser user, XmlAdaptedRecurrence recurrence) {
        this.name = name;
        this.updatedPriority = updatedPriority;
        this.basePriority = basePriority;
        this.deadline = deadline;
        this.dateadded = dateadded;
        this.datecompleted = datecompleted;
        this.description = description;
        this.status = status;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (subtasks != null) {
            this.subtasks = new ArrayList<>(subtasks);
        }
        this.user = user;
        this.recurrence = recurrence;
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        name = source.getName().fullName;
        updatedPriority = source.getUpdatedPriority().value;
        basePriority = source.getBasePriority().value;
        deadline = source.getDeadline().toString();
        dateadded = source.getDateAdded().toString();
        datecompleted = source.getDateCompleted().toString();
        description = source.getDescription().value;
        status = source.getStatus().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        subtasks = new ArrayList<>();
        for (Subtask subtask: source.getSubtasks()) {
            subtasks.add(new XmlAdaptedSubtask(subtask));
        }
        user = new XmlAdaptedUser(source.getUser());
        recurrence = new XmlAdaptedRecurrence(source.getRecurrence());
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }

        final List<Subtask> taskSubtasks = new ArrayList<>();
        for (XmlAdaptedSubtask subtask : subtasks) {
            taskSubtasks.add(subtask.toModelType());
        }

        if (this.user == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, User.class.getSimpleName()));
        }
        if (!User.isValidUsername(this.user.getUsername())) {
            throw new IllegalValueException(User.MESSAGE_USERNAME_CONSTRAINTS);
        }
        if (!User.isValidPassword(this.user.getPassword())) {
            throw new IllegalValueException(User.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final User user = new User(this.user.getUsername(), this.user.getPassword());

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.updatedPriority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(this.updatedPriority)) {
            throw new IllegalValueException(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        final Priority updatedPriority = new Priority(this.updatedPriority);

        if (this.basePriority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(this.basePriority)) {
            throw new IllegalValueException(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        final Priority basePriority = new Priority(this.basePriority);

        if (this.deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName
                    ()));
        }
        if (!Deadline.isValidDeadline(this.deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        }
        final Deadline deadline = new Deadline(this.deadline);

        if (this.dateadded == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DateAdded.class.getSimpleName
                    ()));
        }
        if (!DateAdded.isValidDateAdded(this.dateadded)) {
            throw new IllegalValueException(DateAdded.MESSAGE_DATEADDED_CONSTRAINTS);
        }
        final DateAdded dateadded = new DateAdded(this.dateadded);

        if (this.datecompleted == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateCompleted.class.getSimpleName()));
        }
        if (!DateCompleted.isValidDateCompleted(this.datecompleted)) {
            throw new IllegalValueException(DateCompleted.MESSAGE_DATECOMPLETED_CONSTRAINTS);
        }
        final DateCompleted datecompleted = new DateCompleted(this.datecompleted);

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description description = new Description(this.description);

        if (this.status == null) {
            this.status = false;
        }
        final Status status = new Status(this.status);

        final Set<Tag> tags = new HashSet<>(taskTags);

        final List<Subtask> subtasks = new ArrayList<>(taskSubtasks);

        if (this.recurrence == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Recurrence.class.getSimpleName()));
        }
        final Recurrence recurrence = new Recurrence(this.recurrence.getIsRecurring(),
                this.recurrence.getRecurrenceGroup());

        return new Task(name, updatedPriority, basePriority, deadline, dateadded, datecompleted, description,
                status, tags, subtasks, user, recurrence);


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
                && Objects.equals(updatedPriority, otherTask.updatedPriority)
                && Objects.equals(deadline, otherTask.deadline)
                && Objects.equals(dateadded, otherTask.dateadded)
                && Objects.equals(datecompleted, otherTask.datecompleted)
                && Objects.equals(description, otherTask.description)
                && Objects.equals(status, otherTask.status)
                && Objects.equals(user, otherTask.user)
                && tagged.equals(otherTask.tagged);
    }
}
