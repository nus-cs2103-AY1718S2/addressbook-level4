package seedu.address.model.student.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a milestone in a Student's dashboard
 * Guarantees: details are present and not null, immutable.
 */
public class Milestone {

    private final Date dueDate;
    private final List<Task> taskList;
    private final Progress progress;
    private final String objective;

    public Milestone(Date dueDate, String objective) {
        this.dueDate = dueDate;
        this.objective = objective;
        progress = new Progress();
        taskList = new ArrayList<>();
    }

    public Milestone(Date dueDate, List<Task> taskList, Progress progress, String objective) {
        this.dueDate = dueDate;
        this.taskList = taskList;
        this.progress = progress;
        this.objective = objective;
    }

    /**
     * Creates and return a deep copy of the {@code toCopy} Milestone
     */
    public static Milestone copyMilestone(Milestone toCopy) {
        Date copyDueDate = new Date(toCopy.getDueDate().getValue());
        List<Task> copyTaskList = toCopy.getTaskList().stream()
                .map(task -> new Task(task.getName(), task.getDesc(), task.isCompleted()))
                .collect(Collectors.toList());
        Progress copyProgress = new Progress(toCopy.getProgress().getTotalTasks(),
                toCopy.getProgress().getNumCompletedTasks());
        String copyObjective = new String(toCopy.getObjective());

        return new Milestone(copyDueDate, copyTaskList, copyProgress, copyObjective);
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
        int index = 1;

        builder.append("Objective: ")
                .append(objective)
                .append(" ||")
                .append(" Due Date: ")
                .append(dueDate)
                .append(" ||")
                .append(" Progress: ")
                .append(progress)
                .append("\n")
                .append("Tasks: ");
        for (Task task : taskList) {
            builder.append(index++)
                    .append(" - ")
                    .append(task)
                    .append("\n");
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueDate, taskList, progress, objective);
    }
}
