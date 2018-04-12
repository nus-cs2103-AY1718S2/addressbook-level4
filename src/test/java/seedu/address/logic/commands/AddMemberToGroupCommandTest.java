//@@author jas5469
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
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
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.TypicalPersons;

public class AddMemberToGroupCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddMemberToGroupCommand(null, null);
    }

    @Test
    public void execute_groupAcceptedByModel_addSuccessful() throws Exception {
        AddMemberToGroupCommandTest.ModelStubAcceptingGroupAdded modelStub = new
                AddMemberToGroupCommandTest.ModelStubAcceptingGroupAdded();
        Person person = modelStub.getFilteredPersonList().get(0);
        Group validGroup = getTypicalAddressBook().getGroupList().get(0);

        CommandResult commandResult = getAddMemberToGroupCommandForGroup(INDEX_FIRST_PERSON, validGroup,
                modelStub).execute();

        assertEquals(String.format(AddMemberToGroupCommand.MESSAGE_ADD_PERSON_TO_GROUP_SUCCESS,
                person.getName().toString(), validGroup.getInformation().toString()),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_duplicatedMember_fail() throws Exception {
        AddMemberToGroupCommandTest.ModelStubAcceptingGroupAdded modelStub = new
                AddMemberToGroupCommandTest.ModelStubAcceptingGroupAdded();
        Group groupAlreadyAdded = getTypicalAddressBook().getGroupList().get(3);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMemberToGroupCommand.MESSAGE_DUPLICATE_PERSON);

        getAddMemberToGroupCommandForGroup(INDEX_FIRST_PERSON, groupAlreadyAdded,
                modelStub).execute();
    }


    @Test
    public void equals() {
        Group groupF = new GroupBuilder().withPerson("Group F", TypicalPersons.ALICE).build();
        Group groupG = new GroupBuilder().withPerson("Group G", TypicalPersons.BENSON).build();
        AddMemberToGroupCommand addMemberToGroupFCommand = new AddMemberToGroupCommand(INDEX_FIRST_PERSON, groupF);
        AddMemberToGroupCommand addMemberToGroupGCommand = new AddMemberToGroupCommand(INDEX_FIRST_PERSON, groupG);

        // same object -> returns true
        assertTrue(addMemberToGroupFCommand.equals(addMemberToGroupFCommand));

        // same values -> returns true
        AddMemberToGroupCommand addMemberToGroupCommand = new  AddMemberToGroupCommand(INDEX_FIRST_PERSON, groupF);
        assertTrue(addMemberToGroupFCommand.equals(addMemberToGroupCommand));

        // different types -> returns false
        assertFalse(addMemberToGroupFCommand.equals(1));

        // null -> returns false
        assertFalse(addMemberToGroupFCommand.equals(null));

        // different groups -> returns false
        assertFalse(addMemberToGroupFCommand.equals(addMemberToGroupGCommand));
    }

    /**
     * Generates a new AddMemberToGroupCommand with the details of the given to-do.
     */
    private AddMemberToGroupCommand getAddMemberToGroupCommandForGroup(Index index, Group group, Model model) {
        AddMemberToGroupCommand command = new AddMemberToGroupCommand(index, group);
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
        public void deleteGroup(Group target) throws GroupNotFoundException {
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
        public void updateGroup(Group target, Group editedGroup)
                throws DuplicateGroupException {
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return model.getFilteredPersonList();
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
        public ObservableList<Group> getFilteredGroupList() {
            return model.getFilteredGroupList();
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
     * A Model stub that always accept the to-do being added.
     */
    private class ModelStubAcceptingGroupAdded extends AddMemberToGroupCommandTest.ModelStub {
        final ArrayList<Group> groupsAdded = new ArrayList<>();

        @Override
        public void addGroup(Group group) throws DuplicateGroupException {
            requireNonNull(group);
            groupsAdded.add(group);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
