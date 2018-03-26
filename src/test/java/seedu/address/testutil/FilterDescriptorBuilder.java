package seedu.address.testutil;

import seedu.address.logic.commands.ListCommand.FilterDescriptor;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;

/**
 * A utility class to help with building {@code FilterDescriptor} objects.
 */
public class FilterDescriptorBuilder {
    private final FilterDescriptor descriptor;

    public FilterDescriptorBuilder() {
        descriptor = new FilterDescriptor();
    }

    public FilterDescriptorBuilder(FilterDescriptor descriptor) {
        this.descriptor = new FilterDescriptor(descriptor);
    }

    /** Adds a title filter to the {@code FilterDescriptor} we are building. */
    public FilterDescriptorBuilder withTitleFilter(String title) {
        descriptor.addTitleFilter(title);
        return this;
    }

    /** Adds a author filter to the {@code FilterDescriptor} we are building. */
    public FilterDescriptorBuilder withAuthorFilter(String author) {
        descriptor.addAuthorFilter(author);
        return this;
    }

    /** Adds a category filter to the {@code FilterDescriptor} we are building. */
    public FilterDescriptorBuilder withCategoryFilter(String category) {
        descriptor.addCategoryFilter(category);
        return this;
    }

    /** Adds a status filter to the {@code FilterDescriptor} we are building. */
    public FilterDescriptorBuilder withStatusFilter(Status status) {
        descriptor.addStatusFilter(status);
        return this;
    }

    /** Adds a priority filter to the {@code FilterDescriptor} we are building. */
    public FilterDescriptorBuilder withPriorityFilter(Priority priority) {
        descriptor.addPriorityFilter(priority);
        return this;
    }

    /** Adds a rating filter to the {@code FilterDescriptor} we are building. */
    public FilterDescriptorBuilder withRatingFilter(Rating rating) {
        descriptor.addRatingFilter(rating);
        return this;
    }

    public FilterDescriptor build() {
        return new FilterDescriptor(descriptor);
    }
}
