package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.InterviewCommandTest.VALID_DATETIME;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;
import seedu.address.testutil.PersonBuilder;

public class StatusCommandTest {
    public static final int VALID_STATUS_INDEX = 7;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validDateUnfilteredList_success() throws Exception {
        Person firstPerson = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(firstPerson).withStatus(VALID_STATUS_INDEX).build();

        StatusCommand statusCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_STATUS_INDEX);
        String expectedMessage = String.format(StatusCommand.MESSAGE_STATUS_SUCCESS,
                updatedPerson.getName(), updatedPerson.getStatus().value);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, updatedPerson);

        assertCommandSuccess(statusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        StatusCommand statusCommand = prepareCommand(outOfBoundIndex, VALID_STATUS_INDEX);
        assertCommandFailure(statusCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        StatusCommand statusCommand = prepareCommand(outOfBoundIndex, VALID_STATUS_INDEX);
        assertCommandFailure(statusCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToUpdateStataus = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person scheduledPerson = new PersonBuilder(personToUpdateStataus).withStatus(VALID_STATUS_INDEX).build();
        StatusCommand statusCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_STATUS_INDEX);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // interview -> first person interview date scheduled
        statusCommand.execute();
        undoRedoStack.push(statusCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person scheduled again
        expectedModel.updatePerson(personToUpdateStataus, scheduledPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        StatusCommand statusCommand = prepareCommand(outOfBoundIndex, VALID_STATUS_INDEX);

        // execution failed -> interviewCommand not pushed into undoRedoStack
        assertCommandFailure(statusCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonScheduled() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        StatusCommand statusCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_STATUS_INDEX);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToUpdateStatus = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(personToUpdateStatus).withStatus(VALID_STATUS_INDEX).build();
        // status    -> update status for second person in unfiltered person list /
        //              first person in filtered person list
        statusCommand.execute();
        undoRedoStack.push(statusCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToUpdateStatus, updatedPerson);
        assertNotEquals(personToUpdateStatus, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        // redo -> same second person scheduled again
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final StatusCommand standardCommand = new StatusCommand(INDEX_FIRST_PERSON, new Status(VALID_STATUS_INDEX));

        // same values -> returns true
        StatusCommand commandWithSameValues = new StatusCommand(INDEX_FIRST_PERSON, new Status(VALID_STATUS_INDEX));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new InterviewCommand(INDEX_FIRST_PERSON, VALID_DATETIME)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new StatusCommand(INDEX_SECOND_PERSON, new Status(VALID_STATUS_INDEX))));

    }

    /**
     * Returns an {@code InterviewCommand}.
     */
    private StatusCommand prepareCommand(Index index, int statusIndex) {
        StatusCommand statusCommand = new StatusCommand(index, new Status(statusIndex));
        statusCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return statusCommand;
    }
}
