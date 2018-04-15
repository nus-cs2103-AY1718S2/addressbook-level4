package seedu.address.logic.commands;
//@@author samuelloh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.exceptions.StorageFileMissingException.STORAGE_FILE_MISSING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Schedule;
import seedu.address.model.UserPrefs;

public class MoreInfoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    /**
     * NOTE: This test will succeed when there is no data around initially and hence no data folder.
     * If this test is failing at the moment, please delete any data in the local folder and try again.
     */
    @Test
    public void execute_storageFileMissing_failure() {
        MoreInfoCommand moreInfoCommand = prepareCommand(INDEX_FIRST);
        String expectedMessage = STORAGE_FILE_MISSING;
        assertCommandFailure(moreInfoCommand, model , expectedMessage);
    }

    @Test
    public void execute_storageFileMissing_success() throws Exception {
        MoreInfoCommand moreInfoCommand = prepareCommand(INDEX_FIRST);
        String expectedMessage = MoreInfoCommand.MESSAGE_MOREINFO_STUDENT_SUCCESS.substring(0, 40)
                + "Alice Pauline";


        String pathOfDataFileToCreate = "data/addressBook.xml";
        File dataFileToCreate = new File(pathOfDataFileToCreate);
        Boolean fileCreated = false;
        if (!dataFileToCreate.exists()) {
            new File("data").mkdir();
            dataFileToCreate.createNewFile();
            fileCreated = true;
        }

        assertCommandSuccess(moreInfoCommand, model , expectedMessage, model);

        if (fileCreated == true) {
            File parent = dataFileToCreate.getParentFile();
            dataFileToCreate.delete();
            parent.delete();

        }

    }

    @Test
    public void equals() throws Exception {
        final MoreInfoCommand standardCommand = prepareCommand(INDEX_FIRST);

        // same values -> returns true
        Index sameIndex = INDEX_FIRST;
        MoreInfoCommand commandWithSameValues = prepareCommand(sameIndex);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preProcessStudent();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MoreInfoCommand(INDEX_SECOND)));

    }
    /**
     * Returns a {@code MoreInfo} with parameters {@code index}
     */
    private MoreInfoCommand prepareCommand(Index index) {
        MoreInfoCommand moreInfoCommand = new MoreInfoCommand(index);
        moreInfoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return moreInfoCommand;
    }
}

//@@author
