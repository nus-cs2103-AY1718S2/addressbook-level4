package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT3_DEMO1_FILE_PATH;
import static seedu.address.logic.commands.CommandTestUtil.DUPLICATE_ACTIVITY_FILE_PATH;
import static seedu.address.logic.commands.CommandTestUtil.ILLEGAL_VALUES_FILE_PATH;
import static seedu.address.logic.commands.CommandTestUtil.IMPORT_TEST_DATA_FOLDER;
import static seedu.address.logic.commands.CommandTestUtil.MISSING_FILE_PATH;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_ILLEGAL_VALUES_IN_FILE;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT3;
import static seedu.address.testutil.TypicalActivities.DEMO1;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DeskBoard;
import seedu.address.model.FilePath;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

//@@author karenfrilya97
public class ImportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());

    @Test
    public void constructor_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
    }

    /**
     * Test
     */
    @Test
    public void execute_validFilePath_success() throws CommandException, DuplicateActivityException {
        DeskBoard expectedDeskBoard = new DeskBoard(getTypicalDeskBoard());
        expectedDeskBoard.addActivity(ASSIGNMENT3);
        expectedDeskBoard.addActivity(DEMO1);

        ImportCommand importCommand = getImportCommandForGivenFilePath(ASSIGNMENT3_DEMO1_FILE_PATH, model);
        importCommand.executeUndoableCommand();

        assertEquals(expectedDeskBoard, importCommand.model.getDeskBoard());
    }

    @Test
    public void execute_nonexistentFilePath_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_FILE_NOT_FOUND, MISSING_FILE_PATH));
        ImportCommand importCommand = getImportCommandForGivenFilePath(MISSING_FILE_PATH, model);
        importCommand.executeUndoableCommand();
    }

    /**
     * Test
     */
    @Test
    public void execute_illegalValuesInFile_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_ILLEGAL_VALUES_IN_FILE, "javax.xml.bind.UnmarshalException\n"
                + " - with linked exception"));
        ImportCommand importCommand = getImportCommandForGivenFilePath(ILLEGAL_VALUES_FILE_PATH, model);
        importCommand.executeUndoableCommand();
    }

    /**
     * The file in {@code DUPLICATE_ACTIVITY_FILE_PATH} contains {@code ASSIGNMENT3}, {@code DEMO1} and some activities
     * already in Desk Board. Only {@code ASSIGNMENT3} and {@code DEMO1} should be added into Desk Board, while
     * the existing activities are ignored.
     */
    @Test
    public void execute_fileContainsExistingActivity_ignoresDuplicateActivity() throws CommandException {
        ImportCommand withDuplicate = getImportCommandForGivenFilePath(DUPLICATE_ACTIVITY_FILE_PATH, model);
        withDuplicate.executeUndoableCommand();
        ImportCommand withoutDuplicate = getImportCommandForGivenFilePath(ASSIGNMENT3_DEMO1_FILE_PATH, model);
        withoutDuplicate.executeUndoableCommand();
        assertEquals(withDuplicate.model.getDeskBoard(), withoutDuplicate.model.getDeskBoard());
    }

    @Test
    public void equals() {
        FilePath filePath = new FilePath(IMPORT_TEST_DATA_FOLDER + "deskBoard.xml");
        FilePath differentFilePath = new FilePath(IMPORT_TEST_DATA_FOLDER + "differentDeskBoard.xml");

        ImportCommand importCommand = new ImportCommand(filePath);
        ImportCommand differentImportCommand = new ImportCommand(differentFilePath);

        // same object -> returns true
        assertTrue(importCommand.equals(importCommand));

        // same values -> returns true
        ImportCommand importAssignmentCommandCopy = new ImportCommand(filePath);
        assertTrue(importCommand.equals(importAssignmentCommandCopy));

        // null -> returns false
        assertFalse(importCommand.equals(null));

        // different types -> returns false
        assertFalse(importCommand.equals(1));

        // different file path -> returns false
        assertFalse(importCommand.equals(differentImportCommand));
    }

    /**
     * Generates a new ImportCommand with the given file path.
     */
    private ImportCommand getImportCommandForGivenFilePath(String filePathString, Model model) {
        ImportCommand command = new ImportCommand(new FilePath(filePathString));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
