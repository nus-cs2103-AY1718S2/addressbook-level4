//@@author nhatquang3112
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_UNDONE;

import seedu.address.model.todo.Content;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;

/**
 * A utility class to help with building ToDo objects.
 */
public class ToDoBuilder {

    public static final String DEFAULT_CONTENT = "Something to do";

    private Content content;

    private Status status;

    public ToDoBuilder() {
        content = new Content(DEFAULT_CONTENT);
        status = new Status(VALID_STATUS_UNDONE);
    }

    /**
     * Initializes the ToDoBuilder with the data of {@code todoToCopy}.
     */
    public ToDoBuilder(ToDo todoToCopy) {
        content = todoToCopy.getContent();
        status = todoToCopy.getStatus();
    }

    /**
     * Sets the {@code Content} of the {@code ToDo} that we are building.
     */
    public ToDoBuilder withContent(String content) {
        this.content = new Content(content);
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code ToDo} that we are building.
     */
    public ToDoBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public ToDo build() {
        return new ToDo(content, status);
    }

}
