package seedu.progresschecker.logic.commands;

import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_PATH_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_PATH_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.progresschecker.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.progresschecker.testutil.TypicalPersons.getTypicalProgressChecker;

import org.junit.Test;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.logic.CommandHistory;
import seedu.progresschecker.logic.UndoRedoStack;
import seedu.progresschecker.model.Model;
import seedu.progresschecker.model.ModelManager;
import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.model.UserPrefs;
import seedu.progresschecker.model.person.Person;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for UploadCommand.
 */
public class UploadCommandTest {

    private Model model = new ModelManager(getTypicalProgressChecker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        UploadCommand uploadCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_PATH_AMY);

        String expectedMessage = String.format(UploadCommand.MESSAGE_SUCCESS);

        Model expectedModel = new ModelManager(new ProgressChecker(model.getProgressChecker()), new UserPrefs());
        expectedModel.uploadPhoto(model.getFilteredPersonList().get(0), VALID_PATH_AMY);

        assertCommandSuccess(uploadCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UploadCommand uploadCommand = prepareCommand(INDEX_FIRST_PERSON, INVALID_PATH_DESC);
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(UploadCommand.MESSAGE_SUCCESS);

        Model expectedModel = new ModelManager(new ProgressChecker(model.getProgressChecker()), new UserPrefs());

        assertCommandSuccess(uploadCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns an {@code UploadCommand} with parameters {@code index} and {@code path}
     */
    private UploadCommand prepareCommand(Index index, String path) {
        UploadCommand uploadCommand = new UploadCommand(index, path);
        uploadCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return uploadCommand;
    }
}
