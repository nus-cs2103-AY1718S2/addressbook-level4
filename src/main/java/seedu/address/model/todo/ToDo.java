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
     */
    public ToDo(Content content) {
        requireAllNonNull(content);
        this.content = content;
        this.status = new Status("undone");
    }

    public Content getContent() {
        return content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status newStatus) {
        this.status = newStatus;
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

