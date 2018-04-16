package seedu.organizer.model.recurrence;

import java.util.Objects;

import seedu.organizer.model.recurrence.exceptions.TaskAlreadyRecurredException;

//@@author natania-d
/**
 * Represents a Task's recurrence in the organizer book.
 */

public class Recurrence {

    public final boolean isRecurring;
    public final int recurrenceGroup; // original task and its recurred versions have the same group

    /**
     * Constructs a default {@code Recurrence} with task not recurring.
     */
    public Recurrence() {
        this.isRecurring = false; // false when task is not recurring, true when otherwise
        this.recurrenceGroup = 0; // dummy group
    }

    /**
     * Constructs a {@code Recurrence} for task that is recurring.
     *
     * @param index A valid identifying index for a group of recurring tasks.
     */
    public Recurrence(boolean prevIsRecurring, int index, boolean recurCommand) throws TaskAlreadyRecurredException {
        if (prevIsRecurring && recurCommand) {
            throw new TaskAlreadyRecurredException();
        }
        this.isRecurring = true; // false when task is not recurring, true when otherwise
        this.recurrenceGroup = index; // unique group of recurring tasks
    }

    /**
     * Constructs a {@code Recurrence} for task that is recurring.
     *
     * @param isRecurring A boolean that shows whether task is recurring
     * @param index A valid identifying index for a group of recurring tasks.
     */
    public Recurrence(boolean isRecurring, int index) {
        this.isRecurring = isRecurring; // false when task is not recurring, true when otherwise
        this.recurrenceGroup = index; // unique group of recurring tasks
    }

    public boolean getIsRecurring() {
        return isRecurring;
    }

    public int getRecurrenceGroup() {
        return recurrenceGroup;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrence // instanceof handles nulls
                && this.isRecurring == (((Recurrence) other).isRecurring))
                && this.recurrenceGroup == (((Recurrence) other).recurrenceGroup); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRecurring, recurrenceGroup);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.valueOf(recurrenceGroup);
    }

}


