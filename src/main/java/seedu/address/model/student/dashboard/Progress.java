package seedu.address.model.student.dashboard;

import java.util.Objects;

/**
 * Represents a Milestone's progress
 * Guarantees: details are present and not null, immutable.
 */
public class Progress {

    private final int totalTasks;
    private final int numCompletedTasks;
    private final int progressValueInPercent;

    public Progress() {
        totalTasks = 0;
        numCompletedTasks = 0;
        progressValueInPercent = 0;
    }

    public Progress(int totalTasks, int numCompletedTasks) {
        this.totalTasks = totalTasks;
        this.numCompletedTasks = numCompletedTasks;
        progressValueInPercent = (numCompletedTasks/totalTasks) * 100;
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
