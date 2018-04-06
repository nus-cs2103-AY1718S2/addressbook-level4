package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_ILLEGAL_VALUES_IN_FILE;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT3;
import static seedu.address.testutil.TypicalActivities.DEMO1;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author karenfrilya97
public class ImportCommandTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/ImportCommandTest/");
    private static final String ASSIGNMENT3_DEMO1_FILE_PATH = TEST_DATA_FOLDER + "validImportFile.xml";
    private static final String MISSING_FILE_PATH = TEST_DATA_FOLDER + "missing.xml";
    private static final String ILLEGAL_VALUES_FILE_PATH = TEST_DATA_FOLDER + "illegalValues.xml";
    private static final String DUPLICATE_ACTIVITY_FILE_PATH = TEST_DATA_FOLDER + "duplicateActivity.xml";

    private Model validModel = new ModelManager(getTypicalDeskBoard(), new UserPrefs());

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
    }

    @Test
    public void execute_validFilePathValidModel_success() throws CommandException {
        TaskCommand taskCommand = new TaskCommandTest().getTaskCommandForGivenTask(ASSIGNMENT3, validModel);
        taskCommand.executeUndoableCommand();
        EventCommand eventCommand = new EventCommandTest().getEventCommandForGivenEvent(DEMO1, taskCommand.model);
        eventCommand.executeUndoableCommand();

        ImportCommand importCommand = getImportCommandForGivenFilePath(ASSIGNMENT3_DEMO1_FILE_PATH, validModel);
        importCommand.executeUndoableCommand();

        assertEquals(eventCommand.model.getDeskBoard(), importCommand.model.getDeskBoard());
    }

    @Test
    public void execute_nonexistentFilePath_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_FILE_NOT_FOUND, MISSING_FILE_PATH));
        ImportCommand importCommand = getImportCommandForGivenFilePath(MISSING_FILE_PATH, validModel);
        importCommand.executeUndoableCommand();
    }

    @Test
    public void execute_illegalValuesInFile_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_ILLEGAL_VALUES_IN_FILE, "javax.xml.bind.UnmarshalException\n" +
                " - with linked exception"));
        ImportCommand importCommand = getImportCommandForGivenFilePath(ILLEGAL_VALUES_FILE_PATH, validModel);
        importCommand.executeUndoableCommand();
    }

    @Test
    // The file to import contains one existing activity and a few new activities.
    // The existing activity should be ignored while the rest added into existing desk board.
    public void execute_fileContainsExistingActivity_ignoresDuplicateActivity() throws CommandException {
        ImportCommand withDuplicate = getImportCommandForGivenFilePath(DUPLICATE_ACTIVITY_FILE_PATH, validModel);
        withDuplicate.executeUndoableCommand();
        ImportCommand withoutDuplicate = getImportCommandForGivenFilePath(ASSIGNMENT3_DEMO1_FILE_PATH, validModel);
        withoutDuplicate.executeUndoableCommand();
        assertEquals(withDuplicate.model.getDeskBoard(), withoutDuplicate.model.getDeskBoard());
    }

    @Test
    public void equals() {
        String filePath = TEST_DATA_FOLDER + "deskBoard.xml";
        String differentFilePath = TEST_DATA_FOLDER + "differentDeskBoard.xml";

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
    private ImportCommand getImportCommandForGivenFilePath(String filePath, Model model) {
        ImportCommand command = new ImportCommand(filePath);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
