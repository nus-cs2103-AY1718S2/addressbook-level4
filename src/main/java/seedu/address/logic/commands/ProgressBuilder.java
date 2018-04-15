package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.student.dashboard.Progress;

//@@author yapni
/**
 * A utility class to help with building Progress objects.
 */
public class ProgressBuilder {

    private int totalTasks;
    private int numCompletedTasks;
    private int progressInPercent;
    private String value;

    /**
     * Initializes the ProgressBuilder with the data of {@code progressToCopy}.
     */
    public ProgressBuilder(Progress progressToCopy) {
        requireNonNull(progressToCopy);

        totalTasks = progressToCopy.getTotalTasks();
        numCompletedTasks = progressToCopy.getNumCompletedTasks();
        progressInPercent = progressToCopy.getProgressInPercent();
        value = progressToCopy.getValue();
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
        if (this.totalTasks > 0) {
            this.totalTasks -= 1;
        }
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Subtracts 1 from the {@code totalTask} and {@code numCompletedTask } of the {@code Progress} we are building.
     */
    public ProgressBuilder withOneLessCompletedTaskFromTotal() {
        if (this.totalTasks > 0) {
            this.totalTasks -= 1;
        }
        if (this.numCompletedTasks > 0) {
            this.numCompletedTasks -= 1;
        }
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
        assert this.numCompletedTasks <= this.totalTasks;

        this.progressInPercent = (int) (((double) numCompletedTasks / totalTasks) * 100);
        this.value = this.numCompletedTasks + "/" + this.totalTasks;
    }
}
