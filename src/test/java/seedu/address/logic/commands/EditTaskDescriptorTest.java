package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_CATEGORY_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DUE_DATE_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TASKSECOND;

import org.junit.Test;

import seedu.address.logic.commands.TaskEditCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

//@@author CYX28
public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_TASKFIRST);
        assertTrue(DESC_TASKFIRST.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_TASKFIRST.equals(DESC_TASKFIRST));

        // null -> returns false
        assertFalse(DESC_TASKFIRST.equals(null));

        // different types -> returns false
        assertFalse(DESC_TASKFIRST.equals(1));

        // different values -> returns false
        assertFalse(DESC_TASKFIRST.equals(DESC_TASKSECOND));

        // different task name -> returns false
        EditTaskDescriptor editedTaskFirst = new EditTaskDescriptorBuilder(DESC_TASKFIRST)
                .withTaskName(VALID_TASK_NAME_TASKSECOND).build();
        assertFalse(DESC_TASKFIRST.equals(editedTaskFirst));

        // different task priority -> returns false
        editedTaskFirst = new EditTaskDescriptorBuilder(DESC_TASKFIRST)
                .withTaskPriority(VALID_TASK_PRIORITY_TASKSECOND).build();
        assertFalse(DESC_TASKFIRST.equals(editedTaskFirst));

        // different task description -> returns false
        editedTaskFirst = new EditTaskDescriptorBuilder(DESC_TASKFIRST)
                .withTaskDescription(VALID_TASK_DESCRIPTION_TASKSECOND).build();
        assertFalse(DESC_TASKFIRST.equals(editedTaskFirst));

        // different task due date -> returns false
        editedTaskFirst = new EditTaskDescriptorBuilder(DESC_TASKFIRST)
                .withTaskDueDate(VALID_TASK_DUE_DATE_TASKSECOND).build();
        assertFalse(DESC_TASKFIRST.equals(editedTaskFirst));

        // different task status -> returns false
        editedTaskFirst = new EditTaskDescriptorBuilder(DESC_TASKFIRST)
                .withTaskStatus(VALID_TASK_STATUS_TASKSECOND).build();
        assertFalse(DESC_TASKFIRST.equals(editedTaskFirst));

        // different task categories -> returns false
        editedTaskFirst = new EditTaskDescriptorBuilder(DESC_TASKFIRST)
                .withTaskCategories(VALID_TASK_CATEGORY_MEETING).build();
        assertFalse(DESC_TASKFIRST.equals(editedTaskFirst));
    }

}
