package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing Task Commands
 */
//@@author WoodySIN
public class CommandTaskTestUtil {

    public static final String VALID_TITLE_EXAM = "Test Preparation";
    public static final String VALID_TITLE_MARK = "Mark Test";
    public static final String VALID_TASK_DESC_EXAM = "Giving Practical Exam tips and reviewing past year test";
    public static final String VALID_TASK_DESC_MARK = "Grade test papers for CS1020 tutorial classes";
    public static final String VALID_DEADLINE_EXAM = "01-06-2018";
    public static final String VALID_DEADLINE_MARK = "04-06-2018";
    public static final String VALID_PRIORITY_EXAM = "1";
    public static final String VALID_PRIORITY_MARK = "2";

    public static final String TITLE_DESC_EXAM = " " + PREFIX_TITLE + VALID_TITLE_EXAM;
    public static final String TITLE_DESC_MARK = " " + PREFIX_TITLE + VALID_TITLE_MARK;
    public static final String TASK_DESC_DESC_EXAM = " " + PREFIX_TASK_DESC + VALID_TASK_DESC_EXAM;
    public static final String TASK_DESC_DESC_MARK = " " + PREFIX_TASK_DESC + VALID_TASK_DESC_MARK;
    public static final String DEADLINE_DESC_EXAM = " " + PREFIX_DEADLINE + VALID_DEADLINE_EXAM;
    public static final String DEADLINE_DESC_MARK = " " + PREFIX_DEADLINE + VALID_DEADLINE_MARK;
    public static final String PRIORITY_DESC_EXAM = " " + PREFIX_PRIORITY + VALID_PRIORITY_EXAM;
    public static final String PRIORITY_DESC_MARK = " " + PREFIX_PRIORITY + VALID_PRIORITY_MARK;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + " "; // '&' not allowed in title
    public static final String INVALID_TASK_DESC_DESC = " " + PREFIX_TASK_DESC
            + " "; // empty string not allowed in task description
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "30-13-2018"; // invalid date
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "0"; // priority from 1 to 3

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditTaskCommand.EditTaskDescriptor DESC_EXAM;
    public static final EditTaskCommand.EditTaskDescriptor DESC_MARK;
    static {
        DESC_EXAM = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_EXAM).withDesc(VALID_TASK_DESC_EXAM)
                .withDeadline(VALID_DEADLINE_EXAM).withPriority(VALID_PRIORITY_EXAM).build();
        DESC_MARK = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_MARK).withDesc(VALID_TASK_DESC_MARK)
                .withDeadline(VALID_DEADLINE_MARK).withPriority(VALID_PRIORITY_MARK).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered task list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstTask(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        try {
            model.deleteTask(firstTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("Task in filtered list must exist in model.", tnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
