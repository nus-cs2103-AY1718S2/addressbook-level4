package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.EXISTING_FILE_PATH;
import static seedu.address.logic.commands.CommandTestUtil.EXPORT_FILE_PATH;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_EXISTS;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FilePath;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlDeskBoardStorage;

//@@author karenfrilya97
public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ExportCommand(null);
    }

    @Test
    public void execute_validFilePath_success() throws Exception {
        Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        Storage storage = new StorageManager(new XmlDeskBoardStorage("main\\data\\deskBoard.xml"),
                new JsonUserPrefsStorage("main\\preferences.json"));
        ExportCommand exportCommand = getExportCommandForGivenFilePath(EXPORT_FILE_PATH, model, storage);

        CommandResult commandResult = exportCommand.execute();
        ReadOnlyDeskBoard actualDeskBoard = new XmlDeskBoardStorage(EXPORT_FILE_PATH).readDeskBoard()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, EXPORT_FILE_PATH)));

        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, EXPORT_FILE_PATH), commandResult.feedbackToUser);
        assertEquals(getTypicalDeskBoard(), actualDeskBoard);
        new File(EXPORT_FILE_PATH).delete(); // so that the test will not fail when run the second time onwards
    }

    /**
     * Test
     */
    @Test
    public void execute_existingFile_throwsCommandException() throws Throwable {
        String expectedMessage = String.format(MESSAGE_FILE_EXISTS, EXISTING_FILE_PATH);

        Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        Storage storage = new StorageManager(new XmlDeskBoardStorage("main\\data\\deskBoard.xml"),
                new JsonUserPrefsStorage("main\\preferences.json"));
        ExportCommand exportCommand = getExportCommandForGivenFilePath(EXISTING_FILE_PATH, model, storage);

        try {
            // assertCommandSuccess

            // exportCommand.execute
            requireNonNull(model);
            requireNonNull(storage);

            if (FileUtil.isFileExists(new File(EXISTING_FILE_PATH))) {
                throw new CommandException(String.format(MESSAGE_FILE_EXISTS, EXISTING_FILE_PATH));
            }
            storage.exportDeskBoard(
                    model.getDeskBoard(), EXISTING_FILE_PATH);
            new CommandResult(String.format(ExportCommand.MESSAGE_SUCCESS, EXISTING_FILE_PATH));

            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    /**
     * Generates a new ExportCommand with the given file path.
     */
    private ExportCommand getExportCommandForGivenFilePath(String filePathString, Model model, Storage storage) {
        ExportCommand command = new ExportCommand(new FilePath(filePathString));
        command.setData(model, storage, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
