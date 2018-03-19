package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalDates.DATE_FIRST_JAN;
import static seedu.address.testutil.TypicalDates.DATE_SECOND_FEB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.TAG_SET_FRIEND;
import static seedu.address.testutil.TypicalTags.TAG_SET_OWES_MONEY_FRIEND;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class DeleteBeforeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personsExist_success() throws Exception {
        DeleteBeforeCommand deleteBeforeCommand = prepareCommand(DATE_SECOND_FEB, TAG_SET_FRIEND);
        List<Person> personsToDelete = Arrays.asList(ALICE, BENSON);
        String expectedMessage = String.format(
                DeleteBeforeCommand.MESSAGE_DELETE_PERSONS_SUCCESS, 2, TAG_SET_FRIEND, DATE_SECOND_FEB);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePersons(personsToDelete);

        assertCommandSuccess(deleteBeforeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noSuchPerson_throwsCommandException() {
        DeleteBeforeCommand deleteBeforeCommand = prepareCommand(DATE_FIRST_JAN, TAG_SET_OWES_MONEY_FRIEND);

        assertCommandFailure(deleteBeforeCommand, model, Messages.MESSAGE_PERSONS_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_personsExist_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteBeforeCommand deleteBeforeCommand = prepareCommand(DATE_SECOND_FEB, TAG_SET_FRIEND);
        List<Person> personsToDelete = Arrays.asList(ALICE, BENSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first person deleted
        deleteBeforeCommand.execute();
        undoRedoStack.push(deleteBeforeCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deletePersons(personsToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_noSuchPersonUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteBeforeCommand deleteBeforeCommand = prepareCommand(DATE_FIRST_JAN, TAG_SET_OWES_MONEY_FRIEND);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteBeforeCommand, model, Messages.MESSAGE_PERSONS_NOT_FOUND);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteBeforeCommand deleteBeforeFirstCommand = prepareCommand(DATE_FIRST_JAN, TAG_SET_OWES_MONEY_FRIEND);
        DeleteBeforeCommand deleteBeforeSecondCommand = prepareCommand(DATE_SECOND_FEB, TAG_SET_FRIEND);

        // same object -> returns true
        assertTrue(deleteBeforeFirstCommand.equals(deleteBeforeFirstCommand));

        // same values -> returns true
        DeleteBeforeCommand deleteBeforeFirstCommandCopy = prepareCommand(DATE_FIRST_JAN, TAG_SET_OWES_MONEY_FRIEND);
        assertTrue(deleteBeforeFirstCommand.equals(deleteBeforeFirstCommandCopy));

        // one command preprocessed when previously equal -> returns true
        deleteBeforeFirstCommandCopy.preprocessUndoableCommand();
        assertTrue(deleteBeforeFirstCommand.equals(deleteBeforeFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteBeforeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteBeforeFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(deleteBeforeFirstCommand.equals(deleteBeforeSecondCommand));
    }

    /**
     * Returns a {@code DeleteBeforeCommand} with the parameter {@code date} and {@code tags}.
     */
    private DeleteBeforeCommand prepareCommand(DateAdded dateAdded, Set<Tag> tags) {
        DeleteBeforeCommand deleteBeforeCommand = new DeleteBeforeCommand(dateAdded, tags);
        deleteBeforeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteBeforeCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
