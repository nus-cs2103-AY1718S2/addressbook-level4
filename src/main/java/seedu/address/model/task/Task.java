package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    private final TaskDescription taskDesc;
    private final Deadline deadline;
    private final Priority priority;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskDescription taskDesc, Deadline deadline, int priority) {
        requireAllNonNull(taskDesc, deadline, priority);
        this.taskDesc = taskDesc;
        this.deadline = deadline;

        //calculates priority based on deadline and priority input of user
        this.priority = calculatePriority(deadline, priority);
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

    /**
     * Simple formula to calculate the priority of a task.
     * @param deadline
     * @param priority
     * @return
     */
    private Priority calculatePriority(Deadline deadline, int priority) {
        String value = "";
        Date dateNow = new Date();
        LocalDate now = dateNow.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(now, deadline.value);

        long calPriority = ((1 / daysBetween) * 80) + priority;

        value = Long.toString(calPriority);
        return new Priority(value);
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
                && otherPerson.getPriority().equals(this.getPriority());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskDesc, deadline, priority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Task Description: ")
                .append(getTaskDesc())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Calculated Priority: ")
                .append(getPriority());
        return builder.toString();
    }

}

