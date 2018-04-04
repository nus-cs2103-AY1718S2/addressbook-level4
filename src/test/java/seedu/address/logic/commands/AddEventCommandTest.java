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
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = getAddEventCommandForEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateEventException();
        Event validEvent = new EventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        getAddEventCommandForEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        Event f1Race = new EventBuilder().build();
        Event iLight = new Event("iLight", "Marina Bay", "01/04/2018", "1930", "2359");
        AddEventCommand addF1Command = new AddEventCommand(f1Race);
        AddEventCommand addILightCommand = new AddEventCommand(iLight);

        // same object -> returns true
        assertTrue(addF1Command.equals(addF1Command));

        // same values -> returns true
        AddEventCommand addF1CommandCopy = new AddEventCommand(f1Race);
        assertTrue(addF1Command.equals(addF1CommandCopy));

        // different types -> returns false
        assertFalse(addF1Command.equals(1));

        // null -> returns false
        assertFalse(addF1Command.equals(null));

        // different event -> returns false
        assertFalse(addF1Command.equals(addILightCommand));
    }

    /**
     * Generates a new AddEventCommand with the details of the given event.
     */
    private AddEventCommand getAddEventCommandForEvent(Event event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
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
        public void updateToDo(ToDo target, ToDo editedToDo) throws DuplicateToDoException, ToDoNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateGroup(Group target, Group editedGroup) throws DuplicateGroupException,
                GroupNotFoundException {
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
        public ObservableList<Group> getFilteredGroupList() {
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

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            fail("This method should not be called.");
        }
      
        @Override
        public void indicateCalendarChanged() {
            fail("This method should not be called.");
        }

        @Override
        public void indicateTimetableChanged() {
            fail("This method should not be called.");
        }

        @Override
        public boolean calendarIsViewed() {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void switchView() {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateEventException when trying to add a event.
     */
    private class ModelStubThrowingDuplicateEventException extends ModelStub {
        @Override
        public void addEvent(Event event) throws DuplicateEventException {
            throw new DuplicateEventException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(Event event) throws DuplicateEventException {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
