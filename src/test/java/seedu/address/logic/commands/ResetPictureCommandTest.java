package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

//@@author dezhanglee
public class ResetPictureCommandTest {

    private ModelManager model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
    private Index index = INDEX_FIRST_PERSON;

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {


        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.resetPicture();
        ResetPictureCommand resetPictureCommand = prepareCommand(index);
        String expectedMessage = String.format(ResetPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased());
        Model expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(resetPictureCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {

        showPersonAtIndex(model, index);

        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.resetPicture();
        ResetPictureCommand resetPictureCommand = prepareCommand(index);
        String expectedMessage = String.format(ResetPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased());
        Model expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(resetPictureCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredListInvalidIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ResetPictureCommand resetPictureCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(resetPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        ResetPictureCommand resetPicCommandFirst = prepareCommand(INDEX_FIRST_PERSON);
        ResetPictureCommand resetPicCommandSecond = prepareCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(resetPicCommandFirst.equals(resetPicCommandFirst));

        // same values -> returns true
        ResetPictureCommand resetPicCommandFirstCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(resetPicCommandFirstCopy.equals(resetPicCommandFirst));

        // different types -> returns false
        assertFalse(resetPicCommandFirst.equals(1));

        // null -> returns false
        assertFalse(resetPicCommandFirst.equals(null));

        // different indexes -> returns false
        assertFalse(resetPicCommandSecond.equals(resetPicCommandFirst));

    }


    /**
     * Returns an {@code ResetPictureCommand} with parameters {@code index}
     */
    private ResetPictureCommand prepareCommand(Index index) {
        ResetPictureCommand resetPictureCommand = new ResetPictureCommand(index);
        resetPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return resetPictureCommand;
    }
}
