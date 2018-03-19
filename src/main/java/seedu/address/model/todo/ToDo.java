package seedu.address.model.todo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a ToDo in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ToDo {

    private final Content content;

    /**
     * Every field must be present and not null.
     */
    public ToDo(Content content) {
        requireAllNonNull(content);
        this.content = content;
    }

    public Content getContent() {
        return content;
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

