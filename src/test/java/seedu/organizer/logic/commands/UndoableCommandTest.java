package seedu.organizer.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.organizer.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.organizer.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalOrganizer(), new UserPrefs());

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
            expectedModel.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);

        showPersonAtIndex(model, INDEX_FIRST_TASK);

        // undo() should cause the model's filtered list to show all persons
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalOrganizer(), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showPersonAtIndex(model, INDEX_FIRST_TASK);

        // redo() should cause the model's filtered list to show all persons
        dummyCommand.redo();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first task in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            Task taskToDelete = model.getFilteredTaskList().get(0);
            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException pnfe) {
                fail("Impossible: taskToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
