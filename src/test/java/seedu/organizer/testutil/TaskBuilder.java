package seedu.organizer.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import seedu.organizer.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final User DEFAULT_USER = new User("admin", "admin");
    public static final String DEFAULT_NAME = "Study";
    public static final String DEFAULT_PRIORITY = "2";
    public static final String DEFAULT_DEADLINE = "2018-05-18";
    public static final String DEFAULT_DATEADDED = LocalDate.now().toString();
    public static final String DEFAULT_DATECOMPLETED = "not completed";
    public static final String DEFAULT_DESCRIPTION = "Study for CS2103T Exam";
    public static final String DEFAULT_TAGS = "friends";
    public static final Boolean DEFAULT_STATUS = false;
    public static final String DEFAULT_SUBTASKS = "Buy some answer";

    private Name name;
    private Priority updatedPriority;
    private Priority basePriority;
    private Deadline deadline;
    private DateAdded dateAdded;
    private DateCompleted dateCompleted;
    private Description description;
    private Status status;
    private Set<Tag> tags;
    private List<Subtask> subtasks;
    private User user;
    private Recurrence recurrence;

    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        updatedPriority = new Priority(DEFAULT_PRIORITY);
        basePriority = new Priority(DEFAULT_PRIORITY);
        deadline = new Deadline(DEFAULT_DEADLINE);
        dateAdded = new DateAdded(DEFAULT_DATEADDED);
        dateCompleted = new DateCompleted(DEFAULT_DATECOMPLETED);
        description = new Description(DEFAULT_DESCRIPTION);
        status = new Status(DEFAULT_STATUS);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        subtasks = SampleDataUtil.getSubtaskList(DEFAULT_SUBTASKS);
        user = DEFAULT_USER;
        recurrence = new Recurrence();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        updatedPriority = taskToCopy.getUpdatedPriority();
        basePriority = taskToCopy.getBasePriority();
        deadline = taskToCopy.getDeadline();
        dateAdded = taskToCopy.getDateAdded();
        dateCompleted = taskToCopy.getDateCompleted();
        description = taskToCopy.getDescription();
        status = taskToCopy.getStatus();
        tags = new HashSet<>(taskToCopy.getTags());
        subtasks = new ArrayList<>(taskToCopy.getSubtasks());
        user = taskToCopy.getUser();
        recurrence = taskToCopy.getRecurrence();
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    //@@author agus
    /**
     * Parses the {@code subtask} into a {@code List<Subtask>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withSubtask(String... subtask) {
        this.subtasks = SampleDataUtil.getSubtaskList(subtask);
        return this;
    }
    //@@author

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Task} that we are building.
     */
    public TaskBuilder withStatus(Boolean status) {
        this.status = new Status(status);
        this.dateCompleted = new DateCompleted(status);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String updatedPriority) {
        this.updatedPriority = new Priority(updatedPriority);
        this.basePriority = new Priority(updatedPriority);
        return this;
    }

    /**
     * Sets the base {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withBasePriority(String basePriority) {
        this.basePriority = new Priority(basePriority);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    /**
     * Sets the {@code DateAdded} of the {@code Task} that we are building.
     */
    public TaskBuilder withDateAdded(String dateAdded) {
        this.dateAdded = new DateAdded(dateAdded);
        return this;
    }

    /**
     * Sets the {@code DateCompleted} of the {@code Task} that we are building.
     */
    public TaskBuilder withDateCompleted(String dateCompleted) {
        this.dateCompleted = new DateCompleted(dateCompleted);
        return this;
    }

    /**
     * Sets the {@code DateCompleted} of the {@code Task} that we are building.
     */
    public TaskBuilder withRecurrence(boolean isRecurring, int recurrenceGroup) {
        this.recurrence = new Recurrence(isRecurring, recurrenceGroup);
        return this;
    }

    /**
     * Add {@code Subtask} to the {@code Task} that we are building.
     */
    public TaskBuilder addSubtask(String name) {
        this.subtasks.add(new Subtask(new Name(name)));
        return this;
    }

    /**
     * Returns a task
     */
    public Task build() {
        return new Task(name, updatedPriority, basePriority, deadline, dateAdded, dateCompleted,
                description, status, tags, subtasks, user, recurrence);
    }

}
