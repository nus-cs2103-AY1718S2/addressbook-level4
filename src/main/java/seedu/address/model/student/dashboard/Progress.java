package seedu.address.model.student.dashboard;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Milestone's progress
 * Guarantees: details are present and not null, immutable.
 */
public class Progress {

    public static final String MESSAGE_PROGRESS_CONSTRAINTS =
            "totalTasks must always be more than or equals to numCompletedTask";

    private final int totalTasks;
    private final int numCompletedTasks;
    private final int progressValueInPercent;

    public Progress() {
        totalTasks = 0;
        numCompletedTasks = 0;
        progressValueInPercent = 0;
    }

    public Progress(int totalTasks, int numCompletedTasks) {
        checkArgument(isValidProgress(totalTasks, numCompletedTasks), MESSAGE_PROGRESS_CONSTRAINTS);

        this.totalTasks = totalTasks;
        this.numCompletedTasks = numCompletedTasks;
        progressValueInPercent = (totalTasks == 0) ? 0 : (int) (((double) numCompletedTasks / totalTasks) * 100);
    }

    /**
     * Returns if a given Progress attributes are valid
     */
    public static boolean isValidProgress(int totalTasks, int numCompletedTasks) {
        return totalTasks >= numCompletedTasks;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getNumCompletedTasks() {
        return numCompletedTasks;
    }

    public int getProgressValueInPercent() {
        return progressValueInPercent;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof Progress
                && this.totalTasks == ((Progress) obj).getTotalTasks()
                && this.numCompletedTasks == ((Progress) obj).getNumCompletedTasks());
    }

    @Override
    public String toString() {
        return progressValueInPercent + "%";
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTasks, numCompletedTasks, progressValueInPercent);
    }
}
