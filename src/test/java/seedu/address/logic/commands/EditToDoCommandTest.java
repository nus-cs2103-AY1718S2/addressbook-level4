package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TODO;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditToDoCommand.EditToDoDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.ToDo;
import seedu.address.testutil.EditToDoDescriptorBuilder;
import seedu.address.testutil.ToDoBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests
 * for EditToDoCommand.
 */
public class EditToDoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        ToDo editedToDo = new ToDoBuilder().build();
        EditToDoDescriptor descriptor = new EditToDoDescriptorBuilder(editedToDo).build();
        EditToDoCommand editToDoCommand = prepareCommand(INDEX_FIRST_TODO, descriptor);

        String expectedMessage = String.format(EditToDoCommand.MESSAGE_EDIT_TODO_SUCCESS, editedToDo);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateToDo(model.getFilteredToDoList().get(0), editedToDo);

        assertCommandSuccess(editToDoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditToDoCommand editToDoCommand = prepareCommand(INDEX_FIRST_TODO, new EditToDoDescriptor());
        ToDo editedToDo = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());

        String expectedMessage = String.format(EditToDoCommand.MESSAGE_EDIT_TODO_SUCCESS, editedToDo);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editToDoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateToDoUnfilteredList_failure() {
        ToDo firstToDo = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        EditToDoDescriptor descriptor = new EditToDoDescriptorBuilder(firstToDo).build();
        EditToDoCommand editToDoCommand = prepareCommand(INDEX_SECOND_TODO, descriptor);

        assertCommandFailure(editToDoCommand, model, EditToDoCommand.MESSAGE_DUPLICATE_TODO);
    }

    @Test
    public void execute_invalidToDoIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        EditToDoDescriptor descriptor = new EditToDoDescriptorBuilder().withContent(VALID_CONTENT).build();
        EditToDoCommand editToDoCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ToDo editedToDo = new ToDoBuilder().build();
        ToDo toDoToEdit = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        EditToDoDescriptor descriptor = new EditToDoDescriptorBuilder(editedToDo).build();
        EditToDoCommand editToDoCommand = prepareCommand(INDEX_FIRST_TODO, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first to-do edited
        editToDoCommand.execute();
        undoRedoStack.push(editToDoCommand);

        // undo -> reverts addressbook back to previous state and filtered to-do list to show all to-dos
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first to-do edited again
        expectedModel.updateToDo(toDoToEdit, editedToDo);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        EditToDoDescriptor descriptor = new EditToDoDescriptorBuilder().withContent(VALID_CONTENT).build();
        EditToDoCommand editToDoCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editToDoCommand not pushed into undoRedoStack
        assertCommandFailure(editToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        EditToDoDescriptor descriptor = new EditToDoDescriptorBuilder().withContent(VALID_CONTENT).build();
        EditToDoDescriptor otherDescriptor = new EditToDoDescriptorBuilder().withContent("Another thing to do").build();

        final EditToDoCommand standardCommand = prepareCommand(INDEX_FIRST_TODO, descriptor);

        // same values -> returns true
        EditToDoDescriptor copyDescriptor = new EditToDoDescriptor(descriptor);
        EditToDoCommand commandWithSameValues = prepareCommand(INDEX_FIRST_TODO, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditToDoCommand(INDEX_SECOND_TODO, descriptor)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditToDoCommand(INDEX_FIRST_TODO, otherDescriptor)));
    }

    /**
     * Returns an {@code EditToDoCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditToDoCommand prepareCommand(Index index, EditToDoDescriptor descriptor) {
        EditToDoCommand editToDoCommand = new EditToDoCommand(index, descriptor);
        editToDoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editToDoCommand;
    }
}
