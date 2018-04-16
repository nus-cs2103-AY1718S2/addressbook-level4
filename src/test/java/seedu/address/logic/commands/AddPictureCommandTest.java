package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_JPG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_PNG;
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
public class AddPictureCommandTest {

    private ModelManager model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
    private Index index = INDEX_FIRST_PERSON;

    @Test
    public void execute_validFilepathUnfilteredList_success() throws Exception {


        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.setPicture(VALID_LOCAL_IMAGE_JPG);
        AddPictureCommand addPictureCommand = prepareCommand(index, VALID_LOCAL_IMAGE_JPG);
        String expectedMessage = String.format(AddPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased());
        Model expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(addPictureCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_validFilepathFilteredList_success() throws Exception {

        showPersonAtIndex(model, index);

        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.setPicture(VALID_LOCAL_IMAGE_JPG);
        AddPictureCommand addPictureCommand = prepareCommand(index, VALID_LOCAL_IMAGE_JPG);
        String expectedMessage = String.format(AddPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased());
        Model expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(addPictureCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredListInvalidIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPictureCommand addPictureCommand = prepareCommand(outOfBoundIndex, VALID_LOCAL_IMAGE_JPG);

        assertCommandFailure(addPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        AddPictureCommand addPicCommandFirst = prepareCommand(INDEX_FIRST_PERSON, VALID_LOCAL_IMAGE_JPG);
        AddPictureCommand addPicCommandSecond = prepareCommand(INDEX_SECOND_PERSON, VALID_LOCAL_IMAGE_PNG);

        // same object -> returns true
        assertTrue(addPicCommandFirst.equals(addPicCommandFirst));

        // same values -> returns true
        AddPictureCommand addPicCommandFirstCopy = prepareCommand(INDEX_FIRST_PERSON, VALID_LOCAL_IMAGE_JPG);
        assertTrue(addPicCommandFirstCopy.equals(addPicCommandFirst));

        // different types -> returns false
        assertFalse(addPicCommandFirst.equals(1));

        // null -> returns false
        assertFalse(addPicCommandFirst.equals(null));

        // different indexes -> returns false
        AddPictureCommand apc = prepareCommand(INDEX_SECOND_PERSON, VALID_LOCAL_IMAGE_JPG);
        assertFalse(apc.equals(addPicCommandFirst));

        // same indexes, different files -> returns false
        assertFalse(apc.equals(addPicCommandSecond));

        // both index and files different -> returns false
        assertFalse(addPicCommandSecond.equals(addPicCommandFirst));

    }


    /**
     * Returns an {@code AddPictureCommand} with parameters {@code index} and {@code path}
     */
    private AddPictureCommand prepareCommand(Index index, String path) {
        AddPictureCommand addPictureCommand = new AddPictureCommand(index, path);
        addPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPictureCommand;
    }
}
