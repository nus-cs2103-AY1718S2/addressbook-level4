//@@author nhatquang3112
package seedu.address.model.todo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a ToDo in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ToDo {

    private final Content content;
    private Status status;

    /**
     * Every field must be present and not null.
     * Constructs an {@code ToDo} with the given details.
     * Status is "undone" by default
     */
    public ToDo(Content content) {
        requireAllNonNull(content);
        this.content = content;
        this.status = new Status("undone");
    }

    /**
     * Every field must be present and not null.
     * Constructs an {@code ToDo} with the given details.
     */
    public ToDo(Content content, Status status) {
        requireAllNonNull(content);
        requireAllNonNull(status);
        this.content = content;
        this.status = status;
    }

    public Content getContent() {
        return content;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ToDo)) {
            return false;
        }

        ToDo otherToDo = (ToDo) other;
        return otherToDo.getContent().equals(this.getContent());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getContent());
        return builder.toString();
    }
}
