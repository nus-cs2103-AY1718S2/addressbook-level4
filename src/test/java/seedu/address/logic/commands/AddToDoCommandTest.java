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
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
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
        public void addToDo(ToDo todo) throws DuplicateToDoException {
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
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
