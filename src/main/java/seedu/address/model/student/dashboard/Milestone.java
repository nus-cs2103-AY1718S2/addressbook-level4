package seedu.address.model.student.dashboard;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a milestone in a Student's dashboard
 */
public class Milestone {

    private final Date dueDate;
    private final UniqueTaskList taskList;
    private final Progress progress;
    private final String description;

    public Milestone(Date dueDate, String description) {
        this.dueDate = dueDate;
        this.description = description;
        progress = new Progress();
        taskList = new UniqueTaskList();
    }

    public Milestone(Date dueDate, UniqueTaskList taskList, Progress progress, String description) {
        this.dueDate = dueDate;
        this.taskList = taskList;
        this.progress = progress;
        this.description = description;
    }

    /**
     * Creates and return a deep copy of the {@code toCopy} Milestone
     */
    public static Milestone copyMilestone(Milestone toCopy) {
        // TODO: REMOVE THIS
        Date copyDueDate = new Date(toCopy.getDueDate().getValue());
        UniqueTaskList copyTaskList = toCopy.getTaskList();
        Progress copyProgress = new Progress(toCopy.getProgress().getTotalTasks(),
                toCopy.getProgress().getNumCompletedTasks());
        String copyDescription = new String(toCopy.getDescription());

        return new Milestone(copyDueDate, copyTaskList, copyProgress, copyDescription);
    }

    /**
     * Creates and returns a deep copy of the list of Milestone.
     */
    public static List<Milestone> copyMilestoneList(List<Milestone> listToCopy) {
        return listToCopy.stream().map(Milestone::copyMilestone).collect(Collectors.toList());
    }

    public Date getDueDate() {
        return dueDate;
    }

    public UniqueTaskList getTaskList() {
        return taskList;
    }

    public Progress getProgress() {
        return progress;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof Milestone
                && this.dueDate.equals(((Milestone) obj).getDueDate())
                && this.taskList.equals(((Milestone) obj).getTaskList())
                && this.progress.equals(((Milestone) obj).getProgress())
                && this.description.equals(((Milestone) obj).getDescription()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int index = 1;

        builder.append("Description: ")
                .append(description)
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
        return Objects.hash(dueDate, taskList, progress, description);
    }
}
