package seedu.organizer.model.subtask;

//@@author aguss787
import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Status;

/**
 * Represents a Subtask inside the Task.
 */
public class Subtask {
    private final Name name;
    private final Status status;

    /**
     * Contruct a {@code Subtask} with {@code false} status
     */
    public Subtask(Name name) {
        requireNonNull(name);
        this.name = name;
        this.status = null;
    }


    /**
     * Contruct a {@code Subtask}
     */
    public Subtask(Name name, Status status) {
        requireNonNull(name);
        this.name = name;
        this.status = status;
    }

    public Name getName() {
        return name;
    }

    public Status getStatus() {
        if (status == null) {
            return new Status(false);
        }
        return status;
    }

    /**
     * Two subtask is same if and only if the name and parent is the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subtask)) {
            return false;
        }
        Subtask subtask = (Subtask) o;
        return Objects.equals(name, subtask.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" [")
                .append(getStatus())
                .append("]");
        return builder.toString();
    }
}
