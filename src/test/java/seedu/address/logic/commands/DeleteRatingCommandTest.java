package seedu.address.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
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
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
 */
public class DeleteRatingCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToDeleteRating = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteRatingCommand deleteRatingCommand = prepareCommand(INDEX_FIRST_PERSON);

        PersonBuilder firstPerson = new PersonBuilder(personToDeleteRating);
        Person editedPerson = firstPerson.withRating("-1", "-1",
                "-1", "-1").build();

        String expectedMessage = String.format(DeleteRatingCommand.MESSAGE_DELETE_RATING_SUCCESS,
                personToDeleteRating.getName());

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToDeleteRating, editedPerson);

        assertCommandSuccess(deleteRatingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteRatingCommand deleteRatingCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteRatingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDeleteRating = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteRatingCommand deleteRatingCommand = prepareCommand(INDEX_FIRST_PERSON);

        PersonBuilder firstPerson = new PersonBuilder(personToDeleteRating);
        Person editedPerson = firstPerson.withRating("-1", "-1",
                "-1", "-1").build();

        String expectedMessage = String.format(DeleteRatingCommand.MESSAGE_DELETE_RATING_SUCCESS,
                personToDeleteRating.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToDeleteRating, editedPerson);

        assertCommandSuccess(deleteRatingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of list of candidates
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteRatingCommand deleteRatingCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteRatingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToDeleteRating = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonBuilder firstPerson = new PersonBuilder(personToDeleteRating);
        Person editedPerson = firstPerson.withRating("-1", "-1",
                "-1", "-1").build();


        DeleteRatingCommand deleteRatingCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // deleteRating -> first person's rating deleted
        deleteRatingCommand.execute();
        undoRedoStack.push(deleteRatingCommand);

        // undo -> reverts HR+ back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person's rating deleted again
        expectedModel.updatePerson(personToDeleteRating, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteRatingCommand deleteRatingCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteRatingCommand not pushed into undoRedoStack
        assertCommandFailure(deleteRatingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#remark} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeletedRating() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteRatingCommand deleteRatingCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withRating("-1",
                "-1", "-1", "-1").build();

        // deleteRating -> modifies second person in unfiltered person list / first person in filtered person list
        deleteRatingCommand.execute();
        undoRedoStack.push(deleteRatingCommand);

        // undo -> reverts HR+ back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertNotEquals(personToModify, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> modifies same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Returns a {@code DeleteRatingCommand} with the parameter {@code index}.
     */
    private DeleteRatingCommand prepareCommand(Index index) {
        DeleteRatingCommand deleteRatingCommand = new DeleteRatingCommand(index);
        deleteRatingCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteRatingCommand;
    }
}
