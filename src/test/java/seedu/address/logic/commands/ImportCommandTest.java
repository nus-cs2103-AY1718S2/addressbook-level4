package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.createXmlFile;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_ILLEGAL_VALUES_IN_FILE;
import static seedu.address.logic.logictestutil.ImportExportTestConstants.ASSIGNMENT3_DEMO1_FILE_PATH;
import static seedu.address.logic.logictestutil.ImportExportTestConstants.DUPLICATE_ACTIVITY_FILE_PATH;
import static seedu.address.logic.logictestutil.ImportExportTestConstants.ILLEGAL_VALUES_FILE_PATH;
import static seedu.address.logic.logictestutil.ImportExportTestConstants.IMPORT_TEST_DATA_FOLDER;
import static seedu.address.logic.logictestutil.ImportExportTestConstants.MISSING_FILE_PATH;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT1;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT3;
import static seedu.address.testutil.TypicalActivities.CIP;
import static seedu.address.testutil.TypicalActivities.DEMO1;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.FilePath;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlDeskBoardStorage;

//@@author karenfrilya97
public class ImportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model actualModel = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
    private Storage storage = new StorageManager(new XmlDeskBoardStorage(""), new JsonUserPrefsStorage(""));

    @Test
    public void constructor_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
    }

    @Test
    public void execute_validFilePath_success() throws Throwable {
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, ASSIGNMENT3_DEMO1_FILE_PATH);
        Model expectedModel = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        expectedModel.addActivity(ASSIGNMENT3);
        expectedModel.addActivity(DEMO1);

        createXmlFile(Arrays.asList(ASSIGNMENT3, DEMO1), ASSIGNMENT3_DEMO1_FILE_PATH);
        ImportCommand importCommand =
                getImportCommandForGivenFilePath(ASSIGNMENT3_DEMO1_FILE_PATH, actualModel, storage);

        assertCommandSuccess(importCommand, actualModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentFilePath_throwsCommandException() {
        String expectedMessage = String.format(MESSAGE_FILE_NOT_FOUND, MISSING_FILE_PATH);

        ImportCommand importCommand = getImportCommandForGivenFilePath(MISSING_FILE_PATH, actualModel, storage);

        assertCommandFailure(importCommand, expectedMessage);
    }

    /**
     * Test
     */
    public void execute_illegalValuesInFile_throwsCommandException() throws Throwable {
        String expectedMessage = String.format(MESSAGE_ILLEGAL_VALUES_IN_FILE, ILLEGAL_VALUES_FILE_PATH);

        ImportCommand importCommand = getImportCommandForGivenFilePath(ILLEGAL_VALUES_FILE_PATH, actualModel, storage);

        assertCommandFailure(importCommand, expectedMessage);
    }

    /**
     * The file in {@code DUPLICATE_ACTIVITY_FILE_PATH} contains {@code ASSIGNMENT3}, {@code DEMO1} and some activities
     * already in Desk Board. Only {@code ASSIGNMENT3} and {@code DEMO1} should be added into Desk Board, while
     * the existing activities are ignored.
     */
    @Test
    public void execute_fileContainsExistingActivity_addsAllExceptExistingActivity() throws Throwable {
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, DUPLICATE_ACTIVITY_FILE_PATH);
        Model expectedModel = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        expectedModel.addActivity(ASSIGNMENT3);
        expectedModel.addActivity(DEMO1);

        createXmlFile(Arrays.asList(ASSIGNMENT3, DEMO1, ASSIGNMENT1, CIP), DUPLICATE_ACTIVITY_FILE_PATH);
        ImportCommand importCommand =
                getImportCommandForGivenFilePath(DUPLICATE_ACTIVITY_FILE_PATH, actualModel, storage);

        assertCommandSuccess(importCommand, actualModel, expectedMessage, expectedModel);
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
    private ImportCommand getImportCommandForGivenFilePath(String filePathString, Model model, Storage storage) {
        ImportCommand command = new ImportCommand(new FilePath(filePathString));
        command.setData(model, storage, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
