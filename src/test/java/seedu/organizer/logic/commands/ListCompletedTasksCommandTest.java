package seedu.organizer.logic.commands;

import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.predicates.TaskByStatusPredicate;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

//@@author dominickenn
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCompletedTasksCommand.
 */
public class ListCompletedTasksCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCompletedTasksCommand listCommand;

    @Before
    public void setUp() {
        Status done = new Status(true);
        TaskByStatusPredicate predicate = new TaskByStatusPredicate(done);

        model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
        expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());
        try {
            model.loginUser(ADMIN_USER);
            expectedModel.loginUser(ADMIN_USER);
        } catch (UserNotFoundException unf) {
            throw new AssertionError("Admin user should exist");
        } catch (CurrentlyLoggedInException cli) {
            throw new AssertionError("No user should be currently logged in");
        } catch (UserPasswordWrongException upw) {
            throw new AssertionError("Admin user password should not be wrong");
        }

        listCommand = new ListCompletedTasksCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedModel.updateFilteredTaskList(predicate);
    }

    @Test
    public void execute_listIsNotFiltered_showsCompletedTasks() {
        assertCommandSuccess(listCommand, model, ListCompletedTasksCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsCompletedTasks() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        assertCommandSuccess(listCommand, model, ListCompletedTasksCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

