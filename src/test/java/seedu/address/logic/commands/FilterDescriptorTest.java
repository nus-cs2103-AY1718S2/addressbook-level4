package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand.FilterDescriptor;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Status;
import seedu.address.testutil.FilterDescriptorBuilder;

public class FilterDescriptorTest {

    @Test
    public void equals() {
        FilterDescriptor descriptorA = new FilterDescriptorBuilder()
                .withAuthorFilter("a1").withStatusFilter(Status.UNREAD).build();
        FilterDescriptor descriptorB = new FilterDescriptorBuilder()
                .withTitleFilter("t1").withAuthorFilter("a2").withPriorityFilter(Priority.HIGH).build();

        // same filters in same sequence -> returns true
        FilterDescriptor descriptorWithSameFilters = new FilterDescriptorBuilder()
                .withAuthorFilter("a1").withStatusFilter(Status.UNREAD).build();
        assertTrue(descriptorA.equals(descriptorWithSameFilters));

        // same filters in same sequence -> returns true (ignores case in search terms)
        descriptorWithSameFilters = new FilterDescriptorBuilder()
                .withAuthorFilter("A1").withStatusFilter(Status.UNREAD).build();
        assertTrue(descriptorA.equals(descriptorWithSameFilters));

        // same filters in different sequence -> returns true
        descriptorWithSameFilters = new FilterDescriptorBuilder()
                .withStatusFilter(Status.UNREAD).withAuthorFilter("a1").build();
        assertTrue(descriptorA.equals(descriptorWithSameFilters));

        // same object -> returns true
        assertTrue(descriptorA.equals(descriptorA));

        // null -> returns false
        assertFalse(descriptorA.equals(null));

        // different types -> returns false
        assertFalse(descriptorA.equals(5));

        // different filters -> returns false
        assertFalse(descriptorA.equals(descriptorB));

        // different copies of the same filter -> returns true
        FilterDescriptor editedDescriptor = new FilterDescriptorBuilder()
                .withAuthorFilter("a1").withAuthorFilter("a1").withStatusFilter(Status.UNREAD).build();
        assertTrue(descriptorA.equals(editedDescriptor));
    }
}
