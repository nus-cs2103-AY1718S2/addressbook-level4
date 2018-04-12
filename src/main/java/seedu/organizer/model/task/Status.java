package seedu.organizer.model.task;

//@@author agus
/**
 * Represents a Task's status in the organizer.
 */
public class Status {

    public static final String LABEL_FOR_DONE = "Completed";
    public static final String LABEL_FOR_NOT_DONE = "Not Completed";

    public final boolean value;

    /**
     * Constructs an {@code status}.
     *
     * @param newValue a boolean indicating the status of the task.
     *                 - false: not complete
     *                 - true: complete
     */
    public Status(boolean newValue) {
        this.value = newValue;
    }

    public Status getInverse() {
        return new Status(!this.value);
    }

    @Override
    public String toString() {
        if (this.value) {
            return LABEL_FOR_DONE;
        } else {
            return LABEL_FOR_NOT_DONE;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.value == ((Status) other).value); // state check
    }

    @Override
    public int hashCode() {
        if (this.value) {
            return 1;
        } else {
            return 0;
        }
    }
}
