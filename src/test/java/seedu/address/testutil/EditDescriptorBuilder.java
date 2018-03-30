package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditDescriptor;
import seedu.address.model.book.Book;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;

/**
 * A utility class to help with building EditDescriptor objects.
 */
public class EditDescriptorBuilder {

    private EditDescriptor descriptor;

    public EditDescriptorBuilder() {
        descriptor = new EditDescriptor();
    }

    public EditDescriptorBuilder(EditDescriptor descriptor) {
        this.descriptor = new EditDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDescriptor} with fields containing {@code book}'s details.
     */
    public EditDescriptorBuilder(Book book) {
        descriptor = new EditCommand.EditDescriptor();
        descriptor.setStatus(book.getStatus());
        descriptor.setPriority(book.getPriority());
        descriptor.setRating(book.getRating());
    }

    /**
     * Sets the {@code Status} of the {@code EditDescriptor} that we are building.
     */
    public EditDescriptorBuilder withStatus(Status status) {
        descriptor.setStatus(status);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditDescriptor} that we are building.
     */
    public EditDescriptorBuilder withPriority(Priority priority) {
        descriptor.setPriority(priority);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code EditDescriptor} that we are building.
     */
    public EditDescriptorBuilder withRating(Rating rating) {
        descriptor.setRating(rating);
        return this;
    }

    public EditDescriptor build() {
        return descriptor;
    }
}
