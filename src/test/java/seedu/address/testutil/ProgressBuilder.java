package seedu.address.testutil;

import seedu.address.model.student.dashboard.Progress;

//@@author yapni
/**
 * A utility class to help with building Progress objects.
 */
public class ProgressBuilder {

    public static final int DEFAULT_TOTAL_TASKS = 3;
    public static final int DEFAULT_NUM_COMPLETED_TASKS = 0;

    private int totalTasks;
    private int numCompletedTasks;
    private int progressInPercent;
    private String value;

    public ProgressBuilder() {
        totalTasks = DEFAULT_TOTAL_TASKS;
        numCompletedTasks = DEFAULT_NUM_COMPLETED_TASKS;
        setProgressPercentAndValue();
    }

    public ProgressBuilder(Progress progressToCopy) {
        totalTasks = progressToCopy.getTotalTasks();
        numCompletedTasks = progressToCopy.getNumCompletedTasks();
        progressInPercent = progressToCopy.getProgressInPercent();
        value = progressToCopy.getValue();
    }

    /**
     * Sets the {@code totalTasks} of the {@code Progress} we are building
     */
    public ProgressBuilder withTotalTask(int totalTasks) {
        this.totalTasks = totalTasks;
        setProgressPercentAndValue();

        return this;
    }

    public ProgressBuilder withNumCompletedTasks(int numCompletedTasks) {
        this.numCompletedTasks = numCompletedTasks;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Adds 1 to the {@code totalTask} of the {@code Progress} we are building.
     */
    public ProgressBuilder withOneNewIncompletedTaskToTotal() {
        this.totalTasks += 1;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Subtracts 1 from the {@code totalTask} of the {@code Progress} we are building.
     */
    public ProgressBuilder withOneLessIncompletedTaskFromTotal() {
        this.totalTasks -= 1;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Subtracts 1 from the {@code totalTask} and {@code numCompletedTask } of the {@code Progress} we are building.
     */
    public ProgressBuilder withOneLessCompletedTaskFromTotal() {
        this.totalTasks -= 1;
        this.numCompletedTasks -= 1;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Adds 1 to the {@code numCompletedTasks} of the {@code Progress} we are building.
     */
    public ProgressBuilder withOneMoreCompletedTask() {
        this.numCompletedTasks += 1;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Creates and returns the Progress object with the current attributes.
     */
    public Progress build() {
        return new Progress(value);
    }

    /**
     * Sets the {@code progressInPercent} and {@code value} of the Progress we are building with the current attributes.
     */
    private void setProgressPercentAndValue() {
        this.progressInPercent = (int) (((double) numCompletedTasks / totalTasks) * 100);
        this.value = this.numCompletedTasks + "/" + this.totalTasks;
    }
}
