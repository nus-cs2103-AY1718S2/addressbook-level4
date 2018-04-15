//@@author nhatquang3112
package seedu.address.testutil;

import seedu.address.logic.commands.EditToDoCommand.EditToDoDescriptor;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.ToDo;

/**
 * A utility class to help with building EditToDoDescriptor objects.
 */
public class EditToDoDescriptorBuilder {

    private EditToDoDescriptor descriptor;

    public EditToDoDescriptorBuilder() {
        descriptor = new EditToDoDescriptor();
    }

    public EditToDoDescriptorBuilder(EditToDoDescriptor descriptor) {
        this.descriptor = new EditToDoDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditToDoDescriptor} with fields containing {@code toDo}'s details
     */
    public EditToDoDescriptorBuilder(ToDo toDo) {
        descriptor = new EditToDoDescriptor();
        descriptor.setContent(toDo.getContent());
    }

    /**
     * Sets the {@code Content} of the {@code EditToDoDescriptor} that we are building.
     */
    public EditToDoDescriptorBuilder withContent(String content) {
        descriptor.setContent(new Content(content));
        return this;
    }

    public EditToDoDescriptor build() {
        return descriptor;
    }
}
