package seedu.address.model.student.dashboard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author yapni
/**
 * Represents a Milestone's progress
 * Guarantees: details are present and not null, immutable.
 */
public class Progress {

    public static final String MESSAGE_PROGRESS_CONSTRAINTS =
            "totalTasks must always be more than or equals to numCompletedTask";

    private static final String PROGRESS_FORMAT_REGEX = "([0-9]+)" + "/" + "([0-9]+)";

    private static final Pattern progressFormatPattern = Pattern.compile(PROGRESS_FORMAT_REGEX);

    // Indexes of capturing group in the Progress Matcher's Pattern
    private static final int GROUP_NUM_COMPLETED = 1;
    private static final int GROUP_TOTAL_TASKS = 2;

    private final int totalTasks;
    private final int numCompletedTasks;
    private final int progressInPercent;

    private final String value;

    public Progress() {
        totalTasks = 0;
        numCompletedTasks = 0;
        progressInPercent = 0;
        value = "0/0";
    }

    public Progress(int totalTasks, int numCompletedTasks) {
        checkArgument(isValidProgress(totalTasks, numCompletedTasks), MESSAGE_PROGRESS_CONSTRAINTS);

        this.totalTasks = totalTasks;
        this.numCompletedTasks = numCompletedTasks;
        this.progressInPercent = (totalTasks == 0) ? 0 : (int) (((double) numCompletedTasks / totalTasks) * 100);
        this.value = numCompletedTasks + "/" + totalTasks;
    }

    public Progress(String value) {
        requireNonNull(value);
        checkArgument(isValidProgress(value), MESSAGE_PROGRESS_CONSTRAINTS);

        String trimmedValue = value.trim();
        Matcher matcher = progressFormatPattern.matcher(trimmedValue);
        matcher.matches();

        this.numCompletedTasks = Integer.parseInt(matcher.group(GROUP_NUM_COMPLETED));
        this.totalTasks = Integer.parseInt(matcher.group(GROUP_TOTAL_TASKS));
        this.progressInPercent = (totalTasks == 0) ? 0 : (int) (((double) numCompletedTasks / totalTasks) * 100);
        this.value = trimmedValue;
    }

    /**
     * Returns if a given Progress attributes are valid
     */
    public static boolean isValidProgress(int totalTasks, int numCompletedTasks) {
        return totalTasks >= numCompletedTasks;
    }

    /**
     * Returns if a given Progress attributes are valid
     */
    public static boolean isValidProgress(String value) {
        requireNonNull(value);

        Matcher matcher = progressFormatPattern.matcher(value.trim());
        if (!matcher.matches()) {
            return false;
        }

        int numCompletedTasks = Integer.parseInt(matcher.group(GROUP_NUM_COMPLETED));
        int totalTasks = Integer.parseInt(matcher.group(GROUP_TOTAL_TASKS));

        return isValidProgress(totalTasks, numCompletedTasks);
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getNumCompletedTasks() {
        return numCompletedTasks;
    }

    public int getProgressInPercent() {
        return progressInPercent;
    }

    public String getValue() {
        return value;
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
        return progressInPercent + "%";
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTasks, numCompletedTasks, progressInPercent);
    }
}
