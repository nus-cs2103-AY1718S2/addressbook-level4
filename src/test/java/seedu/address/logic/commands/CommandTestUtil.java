package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_DATE_TIME_CS2010_QUIZ;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_DATE_TIME_MA2108_HOMEWORK;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_NAME_CS2010_QUIZ;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_NAME_MA2108_HOMEWORK;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_REMARK_CS2010_QUIZ;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_REMARK_MA2108_HOMEWORK;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_TAG_CS2010;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_TAG_MA2108;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DeskBoard;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.NameContainsKeywordsPredicate;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlDeskBoardStorage;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    //@@author
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTaskDescriptor DESC_MA2108_HOMEWORK;
    public static final EditCommand.EditActivityDescriptor DESC_CS2010_QUIZ;

    static {
        DESC_MA2108_HOMEWORK = new EditTaskDescriptorBuilder().withName(VALID_NAME_MA2108_HOMEWORK)
                .withDateTime(VALID_DATE_TIME_MA2108_HOMEWORK).withRemark(VALID_REMARK_MA2108_HOMEWORK)
                .withTags(VALID_TAG_CS2010).build();
        DESC_CS2010_QUIZ = new EditTaskDescriptorBuilder().withName(VALID_NAME_CS2010_QUIZ)
                .withDateTime(VALID_DATE_TIME_CS2010_QUIZ).withRemark(VALID_REMARK_CS2010_QUIZ)
                .withTags(VALID_TAG_MA2108, VALID_TAG_CS2010).build();
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
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    //@@author jasmoon
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     */
    public static void assertCommandSuccess(Command command, String expectedMessage)   {
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
     */
    public static void assertCommandFailure(Command command, String expectedMessage) {
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    //@@author
    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered activity list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        DeskBoard expectedAddressBook = new DeskBoard(actualModel.getDeskBoard());
        List<Activity> expectedFilteredList = new ArrayList<>(actualModel.getFilteredActivityList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getDeskBoard());
            assertEquals(expectedFilteredList, actualModel.getFilteredActivityList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the activity at the given {@code targetIndex} in the
     * {@code model}'s desk board.
     */
    public static void showActivityAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredActivityList().size());

        Activity activity = model.getFilteredActivityList().get(targetIndex.getZeroBased());
        final String[] splitName = activity.getName().fullName.split("\\s+");
        model.updateFilteredActivityList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredActivityList().size());
    }

    //@@author Kyomian
    /**
     * Removes the first activity in {@code model}'s filtered list from {@code model}'s desk board.
     */
    public static void removeFirstActivity(Model model) {
        Activity firstActivity = model.getFilteredActivityList().get(0);
        try {
            model.deleteActivity(firstActivity);
        } catch (ActivityNotFoundException pnfe) {
            throw new AssertionError("Activity in filtered list must exist in model.", pnfe);
        }
    }

    //@@author YuanQLLer
    /**
     * Removes the first activity in {@code model}'s filtered list from {@code model}'s desk board.
     */
    public static void removeFirstTask(Model model) {
        Activity firstActivity = model.getFilteredTaskList().get(0);
        try {
            model.deleteActivity(firstActivity);
        } catch (ActivityNotFoundException pnfe) {
            throw new AssertionError("Activity in filtered list must exist in model.", pnfe);
        }
    }

    //@@author
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

    //@@author karenfrilya97
    /**
     * Generates file to be imported containing activities in the {@code activityList}
     * and in the directory {@code filePath}.
     */
    public static void createXmlFile(List<Activity> activityList, String filePath) throws IOException {
        if (new File(filePath).exists()) {
            new File(filePath).delete();
        }

        DeskBoard deskBoard = new DeskBoard();
        deskBoard.addActivities(activityList);

        Storage storage = new StorageManager(new XmlDeskBoardStorage(""),
                new JsonUserPrefsStorage(""));
        storage.saveDeskBoard(deskBoard, filePath);
    }
}
