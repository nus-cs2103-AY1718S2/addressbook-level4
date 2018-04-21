package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

//@@author JoonKai1995
/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task implements Comparable<Task> {

    private final Title title;
    private final TaskDescription taskDesc;
    private final Deadline deadline;
    private final Priority priority;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, TaskDescription taskDesc, Deadline deadline, Priority priority) {
        requireAllNonNull(title, taskDesc, deadline, priority);
        this.title = title;
        this.taskDesc = taskDesc;
        this.deadline = deadline;
        this.priority = priority;
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

        if (!(other instanceof seedu.address.model.task.Task)) {
            return false;
        }

        seedu.address.model.task.Task otherTask = (seedu.address.model.task.Task) other;
        return  otherTask.getTitle().equals(this.getTitle())
                && otherTask.getTaskDesc().equals(this.getTaskDesc())
                && otherTask.getDeadline().equals(this.getDeadline())
                && otherTask.getPriority().equals(this.getPriority());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskDesc, deadline, priority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Title: ")
                .append(getTitle())
                .append(" Task TaskDescription: ")
                .append(getTaskDesc())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Calculated Priority: ")
                .append(getPriority());
        return builder.toString();
    }

    //@@author WoodySIN
    @Override
    public int compareTo(Task task) {
        int yearDiff = this.getDeadlineYear() - task.getDeadlineYear();
        int monthDiff = this.getDeadlineMonth() - task.getDeadlineMonth();
        int dayDiff = this.getDeadlineDay() - task.getDeadlineDay();

        return compareDate(yearDiff, monthDiff, dayDiff);
    }

    /**
     * Compares the dates
     * @param yearDiff difference in year
     * @param monthDiff difference in month
     * @param dayDiff difference in day
     */
    private int compareDate (int yearDiff, int monthDiff, int dayDiff) {
        if (yearDiff != 0) {
            return yearDiff;
        }
        if (monthDiff != 0) {
            return monthDiff;
        }
        return dayDiff;
    }
}
