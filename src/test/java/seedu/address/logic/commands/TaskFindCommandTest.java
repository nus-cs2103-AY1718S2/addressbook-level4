package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalTasks.TASKONE;
import static seedu.address.testutil.TypicalTasks.TASKTHREE;
import static seedu.address.testutil.TypicalTasks.TASKTWO;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

//@@author CYX28
/**
 * Contains integration tests (interaction with the Model) for {@code TaskFindCommand}.
 */
public class TaskFindCommandTest {

    private Model model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());

    @Test
    public void equals() {
        TaskNameContainsKeywordsPredicate firstPredicate =
                new TaskNameContainsKeywordsPredicate(Collections.singletonList("first"));
        TaskNameContainsKeywordsPredicate secondPredicate =
                new TaskNameContainsKeywordsPredicate(Collections.singletonList("second"));

        TaskFindCommand taskFindFirstCommand = new TaskFindCommand(firstPredicate);
        TaskFindCommand taskFindSecondCommand = new TaskFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(taskFindFirstCommand.equals(taskFindFirstCommand));

        // same values -> returns true
        TaskFindCommand taskFindFirstCommandCopy = new TaskFindCommand(firstPredicate);
        assertTrue(taskFindFirstCommand.equals(taskFindFirstCommandCopy));

        // different types -> returns false
        assertFalse(taskFindFirstCommand.equals(5));

        // null -> returns false
        assertFalse(taskFindFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(taskFindFirstCommand.equals(taskFindSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskFindCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        TaskFindCommand command = prepareCommand("one two three");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(TASKTWO, TASKONE, TASKTHREE));
    }

    /**
     * Parses {@code userInput} into a {@code TaskFindCommand}.
     */
    private TaskFindCommand prepareCommand(String userInput) {
        TaskFindCommand command = new TaskFindCommand(
                new TaskNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Task>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(TaskFindCommand command, String expectedMessage, List<Task> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredTaskList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
