package seedu.organizer.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.organizer.logic.commands.CommandTestUtil.DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.organizer.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.organizer.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_EXAM);
        assertTrue(DESC_EXAM.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EXAM.equals(DESC_EXAM));

        // null -> returns false
        assertFalse(DESC_EXAM.equals(null));

        // different types -> returns false
        assertFalse(DESC_EXAM.equals(5));

        // different values -> returns false
        assertFalse(DESC_EXAM.equals(DESC_STUDY));

        // different name -> returns false
        EditCommand.EditTaskDescriptor editedAmy = new EditTaskDescriptorBuilder(DESC_EXAM)
                .withName(VALID_NAME_STUDY).build();
        assertFalse(DESC_EXAM.equals(editedAmy));

        // different priority -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_EXAM).withPriority(VALID_PRIORITY_STUDY).build();
        assertFalse(DESC_EXAM.equals(editedAmy));

        // different deadline -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_EXAM).withDeadline(VALID_DEADLINE_STUDY).build();
        assertFalse(DESC_EXAM.equals(editedAmy));

        // different organizer -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_EXAM).withDescription(VALID_DESCRIPTION_STUDY).build();
        assertFalse(DESC_EXAM.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_EXAM).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_EXAM.equals(editedAmy));
    }
}
