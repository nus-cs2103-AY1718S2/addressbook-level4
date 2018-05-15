package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditDescriptor;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;
import seedu.address.testutil.EditDescriptorBuilder;

public class EditDescriptorTest {

    @Test
    public void isAnyFieldEdited() {
        // has at least 1 modified field -> returns true
        EditDescriptor descriptor =
                new EditDescriptorBuilder().withStatus(Status.READ).withPriority(Priority.HIGH).build();
        assertTrue(descriptor.isValid());
        descriptor = new EditDescriptorBuilder().withStatus(Status.READ).build();
        assertTrue(descriptor.isValid());
        descriptor = new EditDescriptorBuilder().withPriority(Priority.HIGH).build();
        assertTrue(descriptor.isValid());
        descriptor = new EditDescriptorBuilder().withRating(new Rating(5)).build();
        assertTrue(descriptor.isValid());

        // has no modified field -> returns false
        descriptor = new EditDescriptorBuilder().build();
        assertFalse(descriptor.isValid());
    }

    @Test
    public void equals() {
        EditDescriptor descriptorA = new EditDescriptorBuilder().withStatus(Status.UNREAD)
                .withPriority(Priority.LOW).build();
        EditDescriptor descriptorB = new EditDescriptorBuilder().withStatus(Status.UNREAD)
                .withPriority(Priority.HIGH).withRating(new Rating(2)).build();

        // same values -> returns true
        EditDescriptor descriptorWithSameValues =
                new EditDescriptorBuilder().withStatus(Status.UNREAD).withPriority(Priority.LOW).build();
        assertTrue(descriptorA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(descriptorA.equals(descriptorA));

        // null -> returns false
        assertFalse(descriptorA.equals(null));

        // different types -> returns false
        assertFalse(descriptorA.equals(5));

        // different values -> returns false
        assertFalse(descriptorA.equals(descriptorB));

        // different status -> returns false
        EditDescriptor editedDescriptor =
                new EditDescriptorBuilder().withStatus(Status.READ).withPriority(Priority.LOW).build();
        assertFalse(descriptorA.equals(editedDescriptor));

        // different priority -> returns false
        editedDescriptor = new EditDescriptorBuilder()
                .withStatus(Status.UNREAD).withPriority(Priority.HIGH).build();
        assertFalse(descriptorA.equals(editedDescriptor));

        // different rating -> returns false
        editedDescriptor = new EditDescriptorBuilder()
                .withStatus(Status.UNREAD).withPriority(Priority.HIGH).withRating(new Rating(3)).build();
        assertFalse(descriptorA.equals(editedDescriptor));
    }
}
