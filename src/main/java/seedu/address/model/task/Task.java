package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task implements Comparable<Task> {

    private final Title title;
    private final TaskDescription taskDesc;
    private final Deadline deadline;
    private final Priority priority;

    private final long actualPriority;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, TaskDescription taskDesc, Deadline deadline, Priority priority) {
        requireAllNonNull(title, taskDesc, deadline, priority);
        this.title = title;
        this.taskDesc = taskDesc;
        this.deadline = deadline;
        this.priority = priority;

        //calculates priority based on deadline and priority input of user
        this.actualPriority = calculatePriority(deadline.daysBetween, priority.value);
    }

    public Title getTitle() {
        return title;
    }

    public TaskDescription getTaskDesc() {
        return taskDesc;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public long getActualPriority() {
        return actualPriority;
    }

    /**
     * Simple formula to calculate the priority of a task.
     * @param daysBetween
     * @param priority
     * @return
     */
    private long calculatePriority(long daysBetween, int priority) {
        long calPriority = ((1 / (daysBetween + 1)) * 50) + priority;

        return calPriority;
    }

    public int getDeadlineDay() {
        return deadline.day;
    }

    public int getDeadlineYear() {
        return deadline.year;
    }

    public int getDeadlineMonth() {
        return deadline.month;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.person.Person)) {
            return false;
        }

        seedu.address.model.task.Task otherPerson = (seedu.address.model.task.Task) other;
        return otherPerson.getTaskDesc().equals(this.getTaskDesc())
                && otherPerson.getDeadline().equals(this.getDeadline())
                && otherPerson.getPriority().equals(this.getPriority())
                && (otherPerson.getActualPriority() == (this.getActualPriority()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskDesc, deadline, priority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Task TaskDescription: ")
                .append(getTaskDesc())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Calculated Priority: ")
                .append(getPriority());
        return builder.toString();
    }

    @Override
    public int compareTo(Task task) {
        return task.getPriority().value - this.getPriority().value;
    }
}

