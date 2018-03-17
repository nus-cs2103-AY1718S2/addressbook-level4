package seedu.address.model.student.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a milestone in a Student's dashboard
 * Guarantees: details are present and not null, immutable.
 */
public class Milestone {

    private Date dueDate;
    private List<Task> taskList;
    private Progress progress;
    private String objective;

    public Milestone(Date dueDate, String objective) {
        this.dueDate = dueDate;
        this.objective = objective;
        progress = new Progress();
        taskList = new ArrayList<>();
    }

    public Date getDueDate() {
        return dueDate;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public Progress getProgress() {
        return progress;
    }

    public String getObjective() {
        return objective;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof Milestone
                && this.dueDate.equals(((Milestone) obj).getDueDate())
                && this.taskList.equals(((Milestone) obj).getTaskList())
                && this.progress.equals(((Milestone) obj).getProgress())
                && this.objective.equals(((Milestone) obj).getObjective()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Objective: ")
                .append(objective)
                .append("Due Date: ")
                .append(dueDate)
                .append("Progress: ")
                .append(progress)
                .append("Tasks: ");
        taskList.forEach(builder::append);
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueDate, taskList, progress, objective);
    }
}
