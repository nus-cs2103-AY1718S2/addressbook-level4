# nhatquang3112
###### \java\seedu\address\logic\commands\AddToDoCommandIntegrationTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.ToDo;
import seedu.address.testutil.ToDoBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddToDoCommand}.
 */
public class AddToDoCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newToDo_success() throws Exception {
        ToDo validToDo = new ToDoBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addToDo(validToDo);

        assertCommandSuccess(prepareCommand(validToDo, model), model,
                String.format(AddToDoCommand.MESSAGE_SUCCESS, validToDo), expectedModel);
    }

    @Test
    public void execute_duplicateToDo_throwsCommandException() {
        ToDo toDoInList = model.getAddressBook().getToDoList().get(0);
        assertCommandFailure(prepareCommand(toDoInList, model), model, AddToDoCommand.MESSAGE_DUPLICATE_TODO);
    }

    /**
     * Generates a new {@code AddToDoCommand} which upon execution, adds {@code todo} into the {@code model}.
     */
    private AddToDoCommand prepareCommand(ToDo todo, Model model) {
        AddToDoCommand command = new AddToDoCommand(todo);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\AddToDoCommandTest.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.DuplicateEventException;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;
import seedu.address.testutil.ToDoBuilder;

public class AddToDoCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullToDo_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddToDoCommand(null);
    }

    @Test
    public void execute_todoAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingToDoAdded modelStub = new ModelStubAcceptingToDoAdded();
        ToDo validToDo = new ToDoBuilder().build();

        CommandResult commandResult = getAddToDoCommandForToDo(validToDo, modelStub).execute();

        assertEquals(String.format(AddToDoCommand.MESSAGE_SUCCESS, validToDo), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validToDo), modelStub.todosAdded);
    }

    @Test
    public void execute_duplicateToDo_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateToDoException();
        ToDo validToDo = new ToDoBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddToDoCommand.MESSAGE_DUPLICATE_TODO);

        getAddToDoCommandForToDo(validToDo, modelStub).execute();
    }

    @Test
    public void equals() {
        ToDo todoA = new ToDoBuilder().withContent("ToDo A").build();
        ToDo todoB = new ToDoBuilder().withContent("ToDo B").build();
        AddToDoCommand addToDoACommand = new AddToDoCommand(todoA);
        AddToDoCommand addToDoBCommand = new AddToDoCommand(todoB);

        // same object -> returns true
        assertTrue(addToDoACommand.equals(addToDoACommand));

        // same values -> returns true
        AddToDoCommand addToDoACommandCopy = new AddToDoCommand(todoA);
        assertTrue(addToDoACommand.equals(addToDoACommandCopy));

        // different types -> returns false
        assertFalse(addToDoACommand.equals(1));

        // null -> returns false
        assertFalse(addToDoACommand.equals(null));

        // different to-do -> returns false
        assertFalse(addToDoACommand.equals(addToDoBCommand));
    }

    /**
     * Generates a new AddToDoCommand with the details of the given to-do.
     */
    private AddToDoCommand getAddToDoCommandForToDo(ToDo todo, Model model) {
        AddToDoCommand command = new AddToDoCommand(todo);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) throws DuplicateEventException {
            fail("This method should not be called.");
        }

        @Override
        public void addToDo(ToDo todo) throws DuplicateToDoException {
            fail("This method should not be called.");
        }

        @Override
        public void addGroup(Group group) throws DuplicateGroupException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTag(Tag target, Tag editedTag) throws TagNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void removeTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData
        ) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteToDo(ToDo target) throws ToDoNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void updateToDo(ToDo target, ToDo editedToDo)
                throws DuplicateToDoException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ToDo> getFilteredToDoList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredToDoList(Predicate<ToDo> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateToDoException when trying to add a to-do.
     */
    private class ModelStubThrowingDuplicateToDoException extends ModelStub {
        @Override
        public void addToDo(ToDo todo) throws DuplicateToDoException {
            throw new DuplicateToDoException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the to-do being added.
     */
    private class ModelStubAcceptingToDoAdded extends ModelStub {
        final ArrayList<ToDo> todosAdded = new ArrayList<>();

        @Override
        public void addToDo(ToDo todo) throws DuplicateToDoException {
            requireNonNull(todo);
            todosAdded.add(todo);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\CheckToDoCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CheckToDoCommand}.
 */
public class CheckToDoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ToDo toDoToCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        ToDo checkedToDo = new ToDo(toDoToCheck.getContent(), new Status("done"));
        CheckToDoCommand checkToDoCommand = prepareCommand(INDEX_FIRST_TODO);

        String expectedMessage = String.format(CheckToDoCommand.MESSAGE_CHECK_TODO_SUCCESS, toDoToCheck);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateToDo(toDoToCheck, checkedToDo);

        assertCommandSuccess(checkToDoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        CheckToDoCommand checkToDoCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(checkToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ToDo toDoToCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        ToDo checkedToDo = new ToDo(toDoToCheck.getContent(), new Status("done"));
        CheckToDoCommand checkToDoCommand = prepareCommand(INDEX_FIRST_TODO);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // check -> first to-do checked
        checkToDoCommand.execute();
        undoRedoStack.push(checkToDoCommand);

        // undo -> reverts addressbook back to previous state and filtered to-do list to show all to-dos
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first to-do checked again
        expectedModel.updateToDo(toDoToCheck, checkedToDo);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        CheckToDoCommand checkToDoCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> checkToDoCommand not pushed into undoRedoStack
        assertCommandFailure(checkToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        CheckToDoCommand checkToDoFirstCommand = prepareCommand(INDEX_FIRST_TODO);
        CheckToDoCommand checkToDoSecondCommand = prepareCommand(INDEX_SECOND_TODO);

        // same object -> returns true
        assertTrue(checkToDoFirstCommand.equals(checkToDoFirstCommand));

        // same values -> returns true
        CheckToDoCommand checkToDoFirstCommandCopy = prepareCommand(INDEX_FIRST_TODO);
        assertTrue(checkToDoFirstCommand.equals(checkToDoFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        checkToDoFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(checkToDoFirstCommand.equals(checkToDoFirstCommandCopy));

        // different types -> returns false
        assertFalse(checkToDoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(checkToDoFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(checkToDoFirstCommand.equals(checkToDoSecondCommand));
    }

    /**
     * Returns a {@code CheckToDoCommand} with the parameter {@code index}.
     */
    private CheckToDoCommand prepareCommand(Index index) {
        CheckToDoCommand checkToDoCommand = new CheckToDoCommand(index);
        checkToDoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return checkToDoCommand;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteToDoCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.ToDo;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteToDoCommand}.
 */
public class DeleteToDoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ToDo toDoToDelete = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        DeleteToDoCommand deleteToDoCommand = prepareCommand(INDEX_FIRST_TODO);

        String expectedMessage = String.format(DeleteToDoCommand.MESSAGE_DELETE_TODO_SUCCESS, toDoToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteToDo(toDoToDelete);

        assertCommandSuccess(deleteToDoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        DeleteToDoCommand deleteToDoCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ToDo toDoToDelete = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        DeleteToDoCommand deleteToDoCommand = prepareCommand(INDEX_FIRST_TODO);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first to-do deleted
        deleteToDoCommand.execute();
        undoRedoStack.push(deleteToDoCommand);

        // undo -> reverts addressbook back to previous state and filtered to-do list to show all to-dos
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first to-do deleted again
        expectedModel.deleteToDo(toDoToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        DeleteToDoCommand deleteToDoCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteToDoCommand not pushed into undoRedoStack
        assertCommandFailure(deleteToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteToDoCommand deleteToDoFirstCommand = prepareCommand(INDEX_FIRST_TODO);
        DeleteToDoCommand deleteToDoSecondCommand = prepareCommand(INDEX_SECOND_TODO);

        // same object -> returns true
        assertTrue(deleteToDoFirstCommand.equals(deleteToDoFirstCommand));

        // same values -> returns true
        DeleteToDoCommand deleteToDoFirstCommandCopy = prepareCommand(INDEX_FIRST_TODO);
        assertTrue(deleteToDoFirstCommand.equals(deleteToDoFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteToDoFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteToDoFirstCommand.equals(deleteToDoFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteToDoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteToDoFirstCommand.equals(null));

        // different to-do -> returns false
        assertFalse(deleteToDoFirstCommand.equals(deleteToDoSecondCommand));
    }

    /**
     * Returns a {@code DeleteToDoCommand} with the parameter {@code index}.
     */
    private DeleteToDoCommand prepareCommand(Index index) {
        DeleteToDoCommand deleteToDoCommand = new DeleteToDoCommand(index);
        deleteToDoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteToDoCommand;
    }
}
```
###### \java\seedu\address\logic\commands\EditToDoCommandTest.java
``` java
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
```
###### \java\seedu\address\logic\commands\UnCheckToDoCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code UnCheckToDoCommand}.
 */
public class UnCheckToDoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ToDo toDoToUnCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        ToDo unCheckedToDo = new ToDo(toDoToUnCheck.getContent(), new Status("undone"));
        UnCheckToDoCommand unCheckToDoCommand = prepareCommand(INDEX_FIRST_TODO);

        String expectedMessage = String.format(UnCheckToDoCommand.MESSAGE_UNCHECK_TODO_SUCCESS, toDoToUnCheck);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateToDo(toDoToUnCheck, unCheckedToDo);

        assertCommandSuccess(unCheckToDoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        UnCheckToDoCommand unCheckToDoCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unCheckToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ToDo toDoToUnCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        ToDo unCheckedToDo = new ToDo(toDoToUnCheck.getContent(), new Status("undone"));
        UnCheckToDoCommand unCheckToDoCommand = prepareCommand(INDEX_FIRST_TODO);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // uncheck -> first to-do unchecked
        unCheckToDoCommand.execute();
        undoRedoStack.push(unCheckToDoCommand);

        // undo -> reverts addressbook back to previous state and filtered to-do list to show all to-dos
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first to-do unChecked again
        expectedModel.updateToDo(toDoToUnCheck, unCheckedToDo);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        UnCheckToDoCommand unCheckToDoCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> unCheckToDoCommand not pushed into undoRedoStack
        assertCommandFailure(unCheckToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        UnCheckToDoCommand unCheckToDoFirstCommand = prepareCommand(INDEX_FIRST_TODO);
        UnCheckToDoCommand unCheckToDoSecondCommand = prepareCommand(INDEX_SECOND_TODO);

        // same object -> returns true
        assertTrue(unCheckToDoFirstCommand.equals(unCheckToDoFirstCommand));

        // same values -> returns true
        UnCheckToDoCommand unCheckToDoFirstCommandCopy = prepareCommand(INDEX_FIRST_TODO);
        assertTrue(unCheckToDoFirstCommand.equals(unCheckToDoFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        unCheckToDoFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(unCheckToDoFirstCommand.equals(unCheckToDoFirstCommandCopy));

        // different types -> returns false
        assertFalse(unCheckToDoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unCheckToDoFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unCheckToDoFirstCommand.equals(unCheckToDoSecondCommand));
    }

    /**
     * Returns a {@code UnCheckToDoCommand} with the parameter {@code index}.
     */
    private UnCheckToDoCommand prepareCommand(Index index) {
        UnCheckToDoCommand unCheckToDoCommand = new UnCheckToDoCommand(index);
        unCheckToDoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unCheckToDoCommand;
    }
}
```
###### \java\seedu\address\model\todo\ContentTest.java
``` java
package seedu.address.model.todo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ContentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Content(null));
    }

    @Test
    public void constructor_invalidContent_throwsIllegalArgumentException() {
        String invalidContent = "&";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Content(invalidContent));
    }

    @Test
    public void isValidContent() {
        // null content
        Assert.assertThrows(NullPointerException.class, () -> Content.isValidContent(null));

        // invalid content
        assertFalse(Content.isValidContent("")); // empty string
        assertFalse(Content.isValidContent(" ")); // spaces only
        assertFalse(Content.isValidContent("^")); // only non-alphanumeric characters
        assertFalse(Content.isValidContent("Something to do*")); // contains non-alphanumeric characters

        // valid content
        assertTrue(Content.isValidContent("hello world")); // alphabets only
        assertTrue(Content.isValidContent("12345")); // numbers only
        assertTrue(Content.isValidContent("hello world the 2nd")); // alphanumeric characters
        assertTrue(Content.isValidContent("Hello World")); // with capital letters
        assertTrue(Content.isValidContent("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void isSameContentHashCode() {
        Content firstContent = new Content(VALID_CONTENT);
        Content secondContent = new Content(VALID_CONTENT);
        assertTrue(firstContent.hashCode() == secondContent.hashCode());
    }
}
```
###### \java\seedu\address\model\todo\StatusTest.java
``` java
package seedu.address.model.todo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_DONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_UNDONE;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "invalid status";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null status
        Assert.assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid status
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("^")); // only non-alphanumeric characters
        assertFalse(Status.isValidStatus("Some status*")); // contains non-alphanumeric characters
        assertFalse(Status.isValidStatus("invalid status")); // is neither "done" or "undone"

        // valid status
        assertTrue(Status.isValidStatus("done"));
        assertTrue(Status.isValidStatus("undone"));
    }

    @Test
    public void isSameStatusHashCode() {
        Status firstStatus = new Status(VALID_STATUS_DONE);
        Status secondStatus = new Status(VALID_STATUS_DONE);
        assertTrue(firstStatus.hashCode() == secondStatus.hashCode());

        Status thirdStatus = new Status(VALID_STATUS_UNDONE);
        Status forthStatus = new Status(VALID_STATUS_UNDONE);
        assertTrue(thirdStatus.hashCode() == forthStatus.hashCode());
    }

    @Test
    public void equals() {
        Status firstStatus = new Status(VALID_STATUS_DONE);
        Status secondStatus = new Status(VALID_STATUS_DONE);
        assertTrue(firstStatus.equals(firstStatus));
        assertTrue(firstStatus.equals(secondStatus));
    }

    @Test
    public void isSameStatusString() {
        Status status = new Status(VALID_STATUS_DONE);
        assertTrue(status.toString().equals(VALID_STATUS_DONE));
    }
}
```
###### \java\seedu\address\model\todo\ToDoTest.java
``` java
package seedu.address.model.todo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.ToDoBuilder;

public class ToDoTest {
    @Test
    public void equals() {
        ToDo todoA = new ToDoBuilder().withContent("Something to do").withStatus("undone").build();
        ToDo todoB = new ToDoBuilder().withContent("Something to do").withStatus("undone").build();
        ToDo todoC = new ToDoBuilder().withContent("Something to do").withStatus("done").build();

        // different types -> returns false
        assertFalse(todoA.equals(1));

        // same content -> returns true
        assertTrue(todoA.hashCode() == todoB.hashCode());

        // same content, different status -> returns true
        assertTrue(todoA.equals(todoC));
    }
}
```
###### \java\seedu\address\model\UniqueToDoListTest.java
``` java
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.todo.UniqueToDoList;

public class UniqueToDoListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueToDoList uniqueToDoList = new UniqueToDoList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueToDoList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedToDoTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT;
import static seedu.address.storage.XmlAdaptedToDo.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalToDos.TODO_A;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Status;
import seedu.address.testutil.Assert;

public class XmlAdaptedToDoTest {

    @Test
    public void toModelType_validToDoDetails_returnsToDo() throws Exception {
        XmlAdaptedToDo todo = new XmlAdaptedToDo(TODO_A);
        assertEquals(TODO_A, todo.toModelType());
    }

    @Test
    public void toModelType_invalidContent_throwsIllegalValueException() {
        XmlAdaptedToDo todo =
                new XmlAdaptedToDo(INVALID_CONTENT);
        String expectedMessage = Content.MESSAGE_CONTENT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, todo::toModelType);
    }

    @Test
    public void toModelType_nullContent_throwsIllegalValueException() {
        XmlAdaptedToDo todo = new XmlAdaptedToDo((String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, todo::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        XmlAdaptedToDo todo = new XmlAdaptedToDo(VALID_CONTENT, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, todo::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        XmlAdaptedToDo todo =
                new XmlAdaptedToDo(VALID_CONTENT, INVALID_STATUS);
        String expectedMessage = Status.MESSAGE_STATUS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, todo::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedToDo todoA = new XmlAdaptedToDo(TODO_A);
        XmlAdaptedToDo todoB = new XmlAdaptedToDo(TODO_A);
        assertTrue(todoA.equals(todoA));
        assertFalse(todoA.equals(1));
        assertTrue(todoA.equals(todoB));
    }
}
```
###### \java\seedu\address\testutil\EditToDoDescriptorBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.logic.commands.EditToDoCommand.EditToDoDescriptor;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.ToDo;

/**
 * A utility class to help with building EditToDoDescriptor objects.
 */
public class EditToDoDescriptorBuilder {

    private EditToDoDescriptor descriptor;

    public EditToDoDescriptorBuilder() {
        descriptor = new EditToDoDescriptor();
    }

    public EditToDoDescriptorBuilder(EditToDoDescriptor descriptor) {
        this.descriptor = new EditToDoDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditToDoDescriptor} with fields containing {@code toDo}'s details
     */
    public EditToDoDescriptorBuilder(ToDo toDo) {
        descriptor = new EditToDoDescriptor();
        descriptor.setContent(toDo.getContent());
    }

    /**
     * Sets the {@code Content} of the {@code EditToDoDescriptor} that we are building.
     */
    public EditToDoDescriptorBuilder withContent(String content) {
        descriptor.setContent(new Content(content));
        return this;
    }

    public EditToDoDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\ToDoBuilder.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_UNDONE;

import seedu.address.model.todo.Content;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;

/**
 * A utility class to help with building ToDo objects.
 */
public class ToDoBuilder {

    public static final String DEFAULT_CONTENT = "Something to do";

    private Content content;

    private Status status;

    public ToDoBuilder() {
        content = new Content(DEFAULT_CONTENT);
        status = new Status(VALID_STATUS_UNDONE);
    }

    /**
     * Initializes the ToDoBuilder with the data of {@code todoToCopy}.
     */
    public ToDoBuilder(ToDo todoToCopy) {
        content = todoToCopy.getContent();
        status = todoToCopy.getStatus();
    }

    /**
     * Sets the {@code Content} of the {@code ToDo} that we are building.
     */
    public ToDoBuilder withContent(String content) {
        this.content = new Content(content);
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code ToDo} that we are building.
     */
    public ToDoBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public ToDo build() {
        return new ToDo(content, status);
    }

}
```
###### \java\seedu\address\testutil\TypicalToDos.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.todo.ToDo;

/**
 * A utility class containing a list of {@code ToDo} objects to be used in tests.
 */
public class TypicalToDos {
    public static final ToDo TODO_A = new ToDoBuilder().withContent("ToDo A").build();
    public static final ToDo TODO_B = new ToDoBuilder().withContent("ToDo B").build();
    public static final ToDo TODO_C = new ToDoBuilder().withContent("ToDo C").build();
    public static final ToDo TODO_D = new ToDoBuilder().withContent("ToDo D").build();
    public static final ToDo TODO_E = new ToDoBuilder().withContent("ToDo E").build();


    public static List<ToDo> getTypicalToDos() {
        return new ArrayList<>(Arrays.asList(TODO_A, TODO_B, TODO_C));
    }
}
```
###### \java\seedu\address\ui\ToDoCardTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysToDo;

import org.junit.Test;

import guitests.guihandles.ToDoCardHandle;
import seedu.address.model.todo.ToDo;
import seedu.address.testutil.ToDoBuilder;

public class ToDoCardTest extends GuiUnitTest {

    @Test
    public void display() {
        ToDo toDo = new ToDoBuilder().build();
        ToDoCard toDoCard = new ToDoCard(toDo, 1);
        uiPartRule.setUiPart(toDoCard);
        assertCardDisplay(toDoCard, toDo, 1);
    }

    @Test
    public void equals() {
        ToDo toDo = new ToDoBuilder().build();
        ToDoCard toDoCard = new ToDoCard(toDo, 0);

        // same to-do, same index -> returns true
        ToDoCard copy = new ToDoCard(toDo, 0);
        assertTrue(toDoCard.equals(copy));

        // same object -> returns true
        assertTrue(toDoCard.equals(toDoCard));

        // null -> returns false
        assertFalse(toDoCard.equals(null));

        // different types -> returns false
        assertFalse(toDoCard.equals(0));

        // different to-do, same index -> returns false
        ToDo differentToDo = new ToDoBuilder().withContent("different content").build();
        assertFalse(toDoCard.equals(new ToDoCard(differentToDo, 0)));

        // same to-do, different index -> returns false
        assertFalse(toDoCard.equals(new ToDoCard(toDo, 1)));
    }

    /**
     * Asserts that {@code toDoCard} displays the details of {@code expectedToDo} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ToDoCard toDoCard, ToDo expectedToDo, int expectedId) {
        guiRobot.pauseForHuman();

        ToDoCardHandle toDoCardHandle = new ToDoCardHandle(toDoCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", toDoCardHandle.getId());

        // verify to-do details are displayed correctly
        assertCardDisplaysToDo(expectedToDo, toDoCardHandle);
    }
}
```
###### \java\seedu\address\ui\ToDoListPanelTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalToDos.getTypicalToDos;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysToDo;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ToDoCardHandle;
import guitests.guihandles.ToDoListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.todo.ToDo;

public class ToDoListPanelTest extends GuiUnitTest {
    private static final ObservableList<ToDo> TYPICAL_TODOS =
            FXCollections.observableList(getTypicalToDos());

    private ToDoListPanelHandle toDoListPanelHandle;

    @Before
    public void setUp() {
        ToDoListPanel toDoListPanel = new ToDoListPanel(TYPICAL_TODOS);
        uiPartRule.setUiPart(toDoListPanel);

        toDoListPanelHandle = new ToDoListPanelHandle(getChildNode(toDoListPanel.getRoot(),
                ToDoListPanelHandle.TODO_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TODOS.size(); i++) {
            toDoListPanelHandle.navigateToCard(TYPICAL_TODOS.get(i));
            ToDo expectedToDo = TYPICAL_TODOS.get(i);
            ToDoCardHandle actualCard = toDoListPanelHandle.getToDoCardHandle(i);

            assertCardDisplaysToDo(expectedToDo, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
```
###### \java\systemtests\AddToDoCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.CONTENT_E;
import static seedu.address.testutil.TypicalToDos.TODO_E;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.testutil.ToDoUtil;

public class AddToDoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void addToDo() throws Exception {
        /* ------------------------ Perform addToDo operations on the shown unfiltered list ---------------------- */

        /* Case: add a to-do to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ToDo toAdd = TODO_E;
        String command = "   " + AddToDoCommand.COMMAND_WORD + "  " + CONTENT_E + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: invalid keyword -> rejected */
        command = "addsToDo " + ToDoUtil.getToDoDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: add a duplicate to-do -> rejected */
        command = ToDoUtil.getAddToDoCommand(TODO_E);
        assertCommandFailure(command, AddToDoCommand.MESSAGE_DUPLICATE_TODO);
    }

    /**
     * Executes the {@code AddToDoCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command node displays an empty string.<br>
     * 2. Command node has the default style class.<br>
     * 3. Result display node displays the success message of executing {@code AddToDoCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ToDo toAdd) {
        assertCommandSuccess(ToDoUtil.getAddToDoCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ToDo)}. Executes {@code command}
     * instead.
     * @see AddToDoCommandSystemTest#assertCommandSuccess(ToDo)
     */
    private void assertCommandSuccess(String command, ToDo toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addToDo(toAdd);
        } catch (DuplicateToDoException dpt) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddToDoCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ToDo)} except asserts that
     * the,<br>
     * 1. Result display node displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code ToDoListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddToDoCommandSystemTest#assertCommandSuccess(String, ToDo)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command node displays {@code command}.<br>
     * 2. Command node has the error style class.<br>
     * 3. Result display node displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\CheckToDoCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CheckToDoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

public class CheckToDoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void checkToDo() throws Exception {
        Model model = getModel();

        /* ----------------- Performing check operation while an unfiltered list is being shown --------------------- */

        /* Case: check the first to-do in the address book, command with leading space and trailing space and multiple
        spaces between each field
         * -> checked
         */
        Index index = INDEX_FIRST_TODO;
        ToDo toDoToCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        String command = " " + CheckToDoCommand.COMMAND_WORD + " " + " " + index.getOneBased() + " ";
        ToDo checkedToDo = new ToDo(toDoToCheck.getContent(), new Status("done"));

        assertCommandSuccess(command, index, checkedToDo);

        /* Case: undo checking the last to-do in the list -> last to-do restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo checking the last to-do in the list -> last to-do checked again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateToDo(
                getModel().getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased()), checkedToDo);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* --------------------------------- Performing invalid checkToDo operation --------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(CheckToDoCommand.COMMAND_WORD + " 0",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CheckToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(CheckToDoCommand.COMMAND_WORD + " -1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CheckToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredToDoList().size() + 1;
        assertCommandFailure(CheckToDoCommand.COMMAND_WORD + " " + invalidIndex,
                Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(CheckToDoCommand.COMMAND_WORD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CheckToDoCommand.MESSAGE_USAGE));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ToDo, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toCheck the index of the current model's filtered list
     * @see CheckToDoCommandSystemTest#assertCommandSuccess(String, Index, ToDo, Index)
     */
    private void assertCommandSuccess(String command, Index toCheck, ToDo checkedToDo) {
        assertCommandSuccess(command, toCheck, checkedToDo, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display node displays the success message of executing {@code CheckToDoCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the to-do at index {@code toCheck} being
     * updated to values specified {@code checkedToDo}.<br>
     *
     * @param toCheck the index of the current model's filtered list.
     * @see CheckToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toCheck, ToDo checkedToDo,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateToDo(
                    expectedModel.getFilteredToDoList().get(toCheck.getZeroBased()), checkedToDo);
            expectedModel.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        } catch (DuplicateToDoException | ToDoNotFoundException e) {
            throw new IllegalArgumentException(
                    "checkedToDo is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(CheckToDoCommand.MESSAGE_CHECK_TODO_SUCCESS, checkedToDo), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see CheckToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command node displays an empty string.<br>
     * 2. Asserts that the result display node displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command node has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command node displays {@code command}.<br>
     * 2. Asserts that result display node displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command node has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\DeleteToDoCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteToDoCommand.MESSAGE_DELETE_TODO_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndexToDo;
import static seedu.address.testutil.TestUtil.getToDo;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

public class DeleteToDoCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_TODO_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteToDoCommand.MESSAGE_USAGE);

    @Test
    public void deleteToDo() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first to-do in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteToDoCommand.COMMAND_WORD + "      " + INDEX_FIRST_TODO.getOneBased() + "   ";
        ToDo deletedToDo = removeToDo(expectedModel, INDEX_FIRST_TODO);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TODO_SUCCESS, deletedToDo);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last to-do in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastToDoIndex = getLastIndexToDo(modelBeforeDeletingLast);
        assertCommandSuccess(lastToDoIndex);

        /* Case: undo deleting the last to-do in the list -> last to-do restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last to-do in the list -> last to-do deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeToDo(modelBeforeDeletingLast, lastToDoIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);


        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteToDoCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_TODO_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteToDoCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_TODO_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getToDoList().size() + 1);
        command = DeleteToDoCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(
                DeleteToDoCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_TODO_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(
                DeleteToDoCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_TODO_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETEtOdO 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code ToDo} at the specified {@code index} in {@code model}'s address book.
     * @return the removed to-do
     */
    private ToDo removeToDo(Model model, Index index) {
        ToDo targetToDo = getToDo(model, index);
        try {
            model.deleteToDo(targetToDo);
        } catch (ToDoNotFoundException tnfe) {
            throw new AssertionError("targetToDo is retrieved from model.");
        }
        return targetToDo;
    }

    /**
     * Deletes the to-do at {@code toDelete} by creating a default {@code DeleteToDoCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteToDoCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ToDo deletedToDo = removeToDo(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TODO_SUCCESS, deletedToDo);

        assertCommandSuccess(
                DeleteToDoCommand.COMMAND_WORD + " "
                        + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\EditToDoCommandSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditToDoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;
import seedu.address.testutil.ToDoBuilder;
import seedu.address.testutil.ToDoUtil;

public class EditToDoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void editToDo() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit to-do operation while an unfiltered list is being shown ---------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_TODO;
        String command = " " + EditToDoCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + CONTENT_DESC + "  ";
        ToDo editedToDo = new ToDoBuilder().withContent(VALID_CONTENT).build();
        assertCommandSuccess(command, index, editedToDo);

        /* Case: undo editing the last to-do in the list -> last to-do restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last to-do in the list -> last to-do edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateToDo(
                getModel().getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased()), editedToDo);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a to-do with new content same as existing content -> edited */
        command = EditToDoCommand.COMMAND_WORD + " " + index.getOneBased() + CONTENT_DESC;
        ToDo validToDo = new ToDoBuilder().withContent(VALID_CONTENT).build();
        assertCommandSuccess(command, index, validToDo);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + " 0" + CONTENT_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + " -1" + CONTENT_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredToDoList().size() + 1;
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + " " + invalidIndex + CONTENT_DESC,
                Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + CONTENT_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditToDoCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + " " + INDEX_FIRST_TODO.getOneBased(),
                EditToDoCommand.MESSAGE_NOT_EDITED_TODO);

        /* Case: invalid content -> rejected */
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + " " + INDEX_FIRST_TODO.getOneBased()
                + INVALID_CONTENT_DESC, Content.MESSAGE_CONTENT_CONSTRAINTS);

        /* Case: edit a to-do with new values same as another to-do's values -> rejected */
        ToDo anotherToDo = new ToDoBuilder().withContent("Another thing to do").build();
        executeCommand(ToDoUtil.getAddToDoCommand(anotherToDo));
        assertTrue(getModel().getAddressBook().getToDoList().contains(anotherToDo));
        index = INDEX_FIRST_TODO;
        assertFalse(getModel().getFilteredToDoList().get(index.getZeroBased()).equals(anotherToDo));
        command = EditToDoCommand.COMMAND_WORD + " " + index.getOneBased() + " c/" + "Another thing to do";
        assertCommandFailure(command, EditToDoCommand.MESSAGE_DUPLICATE_TODO);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ToDo, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditToDoCommandSystemTest#assertCommandSuccess(String, Index, ToDo, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ToDo editedToDo) {
        assertCommandSuccess(command, toEdit, editedToDo, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditToDoCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the to-do at index {@code toEdit} being
     * updated to values specified {@code editedToDo}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ToDo editedToDo,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateToDo(
                    expectedModel.getFilteredToDoList().get(toEdit.getZeroBased()), editedToDo);
            expectedModel.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        } catch (DuplicateToDoException | ToDoNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedToDo is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditToDoCommand.MESSAGE_EDIT_TODO_SUCCESS, editedToDo), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see EditToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\UnCheckToDoCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UnCheckToDoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

public class UnCheckToDoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void unCheckToDo() throws Exception {
        Model model = getModel();

        /* ----------------- Performing uncheck operation while an unfiltered list is being shown --------------------*/

        /* Case: uncheck the first to-do in the address book, command with leading space and trailing space and multiple
        spaces between each field
         * -> unchecked
         */
        Index index = INDEX_FIRST_TODO;
        ToDo toDoToUnCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        String command = " " + UnCheckToDoCommand.COMMAND_WORD + " " + " " + index.getOneBased() + " ";
        ToDo unCheckedToDo = new ToDo(toDoToUnCheck.getContent(), new Status("undone"));

        assertCommandSuccess(command, index, unCheckedToDo);

        /* Case: undo unchecking the last to-do in the list -> last to-do restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo unchecking the last to-do in the list -> last to-do unchecked again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateToDo(
                getModel().getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased()), unCheckedToDo);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* --------------------------------- Performing invalid UnCheckToDo operation --------------------------------*/

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(UnCheckToDoCommand.COMMAND_WORD + " 0",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnCheckToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(UnCheckToDoCommand.COMMAND_WORD + " -1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnCheckToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredToDoList().size() + 1;
        assertCommandFailure(UnCheckToDoCommand.COMMAND_WORD + " " + invalidIndex,
                Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(UnCheckToDoCommand.COMMAND_WORD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnCheckToDoCommand.MESSAGE_USAGE));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ToDo, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toUnCheck the index of the current model's filtered list
     * @see UnCheckToDoCommandSystemTest#assertCommandSuccess(String, Index, ToDo, Index)
     */
    private void assertCommandSuccess(String command, Index toUnCheck, ToDo unCheckedToDo) {
        assertCommandSuccess(command, toUnCheck, unCheckedToDo, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display node displays the success message of executing {@code UnCheckToDoCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the to-do at index {@code toUnCheck} being
     * updated to values specified {@code unCheckedToDo}.<br>
     *
     * @param toUnCheck the index of the current model's filtered list.
     * @see UnCheckToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toUnCheck, ToDo unCheckedToDo,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateToDo(
                    expectedModel.getFilteredToDoList().get(toUnCheck.getZeroBased()), unCheckedToDo);
            expectedModel.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        } catch (DuplicateToDoException | ToDoNotFoundException e) {
            throw new IllegalArgumentException(
                    "unCheckedToDo is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(
                        UnCheckToDoCommand.MESSAGE_UNCHECK_TODO_SUCCESS, unCheckedToDo), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see CheckToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command node displays an empty string.<br>
     * 2. Asserts that the result display node displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command node has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command node displays {@code command}.<br>
     * 2. Asserts that result display node displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command node has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
