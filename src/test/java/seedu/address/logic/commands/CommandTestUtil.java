package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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
import seedu.address.testutil.EditActivityDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_MA2108_HOMEWORK = "MA2108 Homework 3";
    public static final String VALID_NAME_CS2010_QUIZ = "CS2010 Online Quiz 2";
    public static final String VALID_DATE_TIME_MA2108_HOMEWORK = "11/11/1111 11:11";
    public static final String VALID_DATE_TIME_CS2010_QUIZ = "22/12/2222 22:22";
    public static final String VALID_REMARK_MA2108_HOMEWORK = "3% of total grade";
    public static final String VALID_REMARK_CS2010_QUIZ = "5% of total grade";
    public static final String VALID_TAG_MA2108 = "MA2108";
    public static final String VALID_TAG_CS2010 = "CS2010";

    public static final String NAME_DESC_MA2108_HOMEWORK = " " + PREFIX_NAME + VALID_NAME_MA2108_HOMEWORK;
    public static final String NAME_DESC_CS2010_QUIZ = " " + PREFIX_NAME + VALID_NAME_CS2010_QUIZ;
    public static final String DATE_TIME_DESC_MA2108_HOMEWORK = " " + PREFIX_DATE_TIME + VALID_DATE_TIME_MA2108_HOMEWORK;
    public static final String DATE_TIME_DESC_CS2010_QUIZ = " " + PREFIX_DATE_TIME + VALID_DATE_TIME_CS2010_QUIZ;
    public static final String REMARK_DESC_MA2108_HOMEWORK = " " + PREFIX_REMARK + VALID_REMARK_MA2108_HOMEWORK;
    public static final String REMARK_DESC_CS2010_QUIZ = " " + PREFIX_REMARK + VALID_REMARK_CS2010_QUIZ;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_CS2010;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_MA2108;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DATE_TIME_DESC = " " + PREFIX_DATE_TIME + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditActivityDescriptor DESC_MA2108_HOMEWORK;
    public static final EditCommand.EditActivityDescriptor DESC_CS2010_QUIZ;

    static {
        DESC_MA2108_HOMEWORK = new EditActivityDescriptorBuilder().withName(VALID_NAME_MA2108_HOMEWORK)
                .withPhone(VALID_DATE_TIME_MA2108_HOMEWORK).withAddress(VALID_REMARK_MA2108_HOMEWORK)
                .withTags(VALID_TAG_CS2010).build();
        DESC_CS2010_QUIZ = new EditActivityDescriptorBuilder().withName(VALID_NAME_CS2010_QUIZ)
                .withPhone(VALID_DATE_TIME_CS2010_QUIZ).withAddress(VALID_REMARK_CS2010_QUIZ)
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
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredActivityList().size());

        Activity activity = model.getFilteredActivityList().get(targetIndex.getZeroBased());
        final String[] splitName = activity.getName().fullName.split("\\s+");
        model.updateFilteredActivityList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredActivityList().size());
    }

    /**
     * Deletes the first activity in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Activity firstActivity = model.getFilteredActivityList().get(0);
        try {
            model.deleteActivity(firstActivity);
        } catch (ActivityNotFoundException pnfe) {
            throw new AssertionError("Activity in filtered list must exist in model.", pnfe);
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
