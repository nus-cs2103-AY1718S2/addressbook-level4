package seedu.organizer.model.task;

import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.organizer.model.ModelManager.getCurrentlyLoggedInUser;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.organizer.model.recurrence.Recurrence;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.subtask.UniqueSubtaskList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.tag.UniqueTagList;
import seedu.organizer.model.user.User;

/**
 * Represents a Task in the organizer.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    private final Name name;
    private final Priority updatedPriority;
    private final Priority basePriority;
    private final Deadline deadline;
    private final DateAdded dateAdded;
    private final DateCompleted dateCompleted;
    private final Description description;
    private final Status status;
    private final User user;
    private final Recurrence recurrence;

    //@@author agus
    private final UniqueTagList tags;
    private final UniqueSubtaskList subtasks;
    //@@author

    /**
     * Every field must be present and not null except status and dateCompleted
     */
    public Task(Name name, Priority priority, Deadline deadline, Description description, Set<Tag> tags) {
        requireAllNonNull(name, priority, deadline, description, tags);
        this.name = name;
        this.updatedPriority = priority;
        this.basePriority = priority;
        this.deadline = deadline;
        this.dateAdded = new DateAdded();
        this.dateCompleted = new DateCompleted(false);
        this.description = description;
        this.status = null;
        this.user = getCurrentlyLoggedInUser();
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.subtasks = new UniqueSubtaskList();
        this.recurrence = new Recurrence();
    }

    /**
     * Every field must be present and not null except status and dateCompleted
     */
    public Task(Name name, Priority priority, Deadline deadline, Description description,
                Set<Tag> tags, User user) {
        requireAllNonNull(name, priority, deadline, description, tags, user);
        this.name = name;
        this.updatedPriority = priority;
        this.basePriority = priority;
        this.deadline = deadline;
        this.dateAdded = new DateAdded();
        this.dateCompleted = new DateCompleted(false);
        this.description = description;
        this.status = null;
        this.user = user;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.subtasks = new UniqueSubtaskList();
        this.recurrence = new Recurrence();
    }

    //@@author agus
    /**
     * Another constructor with custom status and subtask
     */
    public Task(Name name, Priority updatedPriority, Priority basePriority, Deadline deadline, DateAdded dateAdded,
                DateCompleted dateCompleted, Description description, Status status, Set<Tag> tags,
                List<Subtask> subtasks, User user, Recurrence recurrence) {
        requireAllNonNull(name, updatedPriority, deadline, description, tags);
        this.name = name;
        this.updatedPriority = updatedPriority;
        this.basePriority = basePriority;
        this.deadline = deadline;
        this.dateAdded = dateAdded;
        this.dateCompleted = dateCompleted;
        this.description = description;
        this.status = status;
        this.user = user;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.subtasks = new UniqueSubtaskList(subtasks);
        this.recurrence = recurrence;
    }
    //@@author

    public Name getName() {
        return name;
    }

    public Priority getUpdatedPriority() {
        return updatedPriority;
    }

    public Priority getBasePriority() {
        return basePriority;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public DateAdded getDateAdded() {
        return dateAdded;
    }

    public DateCompleted getDateCompleted() {
        if (dateCompleted == null) {
            return new DateCompleted(false);
        }
        return dateCompleted;
    }

    public Description getDescription() {
        return description;
    }

    //@@author agus
    public Status getStatus() {
        if (status == null) {
            return new Status(false);
        }
        return status;
    }
    //@@author

    public User getUser() {
        return user;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    //@@author agus
    public List<Subtask> getSubtasks() {
        return Collections.unmodifiableList(subtasks.toList());
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getName().equals(this.getName())
                && otherTask.getUpdatedPriority().equals(this.getUpdatedPriority())
                && otherTask.getDeadline().equals(this.getDeadline())
                && otherTask.getDescription().equals(this.getDescription())
                && otherTask.getUser().equals(this.getUser());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, updatedPriority, deadline, description, tags, status, user, recurrence);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Priority: ")
                .append(getUpdatedPriority())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Status: ")
                .append(getStatus())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" User : ")
                .append(getUser());
        return builder.toString();
    }

    //@@author dominickenn
    /**
     * Task's with higher priority are given preference
     * @return a Task comparator based on priority
     */
    public static Comparator<Task> priorityComparator() {
        return new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return (task2.getUpdatedPriority().value)
                        .compareTo(task1.getUpdatedPriority().value);
            }
        };
    }
}
