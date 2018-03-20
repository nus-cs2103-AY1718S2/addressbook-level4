package seedu.address.testutil;

import seedu.address.model.todo.Content;
import seedu.address.model.todo.ToDo;

/**
 * A utility class to help with building ToDo objects.
 */
public class ToDoBuilder {

    public static final String DEFAULT_CONTENT = "Something to do";

    private Content content;

    public ToDoBuilder() {
        content = new Content(DEFAULT_CONTENT);
    }

    /**
     * Initializes the ToDoBuilder with the data of {@code todoToCopy}.
     */
    public ToDoBuilder(ToDo todoToCopy) {
        content = todoToCopy.getContent();
    }

    /**
     * Sets the {@code Content} of the {@code ToDo} that we are building.
     */
    public ToDoBuilder withContent(String content) {
        this.content = new Content(content);
        return this;
    }

    public ToDo build() {
        return new ToDo(content);
    }

}
