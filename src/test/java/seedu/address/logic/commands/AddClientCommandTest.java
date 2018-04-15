package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_TUTOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

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
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.ClientBuilder;

//@@author shookshire
public class AddClientCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullClient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddClientCommand(null);
    }

    @Test
    public void execute_clientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingClientAdded modelStub = new ModelStubAcceptingClientAdded();
        Client validClient = new ClientBuilder().build();

        CommandResult commandResultStudent = getAddClientCommandForStudent(validClient, modelStub).execute();

        assertEquals(String.format(AddClientCommand.MESSAGE_SUCCESS_STUDENT, validClient),
                commandResultStudent.feedbackToUser);
        assertEquals(Arrays.asList(validClient), modelStub.studentsAdded);

        Client validTutor = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND)
                .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_GRADE_BOB)
                .withCategory(VALID_CATEGORY_TUTOR_BOB).build();

        CommandResult commandResultTutor = getAddClientCommandForTutor(validTutor, modelStub).execute();

        assertEquals(String.format(AddClientCommand.MESSAGE_SUCCESS_TUTOR, validTutor),
                commandResultTutor.feedbackToUser);
        assertEquals(Arrays.asList(validTutor), modelStub.tutorsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateClientException();
        Client validClient = new ClientBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddClientCommand.MESSAGE_DUPLICATE_PERSON);

        getAddClientCommandForStudent(validClient, modelStub).execute();
    }

    @Test
    public void equals() {
        Client alice = new ClientBuilder().withName("Alice").build();
        Client bob = new ClientBuilder().withName("Bob").build();
        AddClientCommand addAliceCommand = new AddClientCommand(alice);
        AddClientCommand addBobCommand = new AddClientCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddClientCommand addAliceCommandCopy = new AddClientCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddClientCommand with the details of the given student.
     */
    private AddClientCommand getAddClientCommandForStudent(Client client, Model model) {
        AddClientCommand command = new AddClientCommand(client);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new AddClientCommand with the details of the given tutor.
     */
    private AddClientCommand getAddClientCommandForTutor(Client client, Model model) {
        AddClientCommand command = new AddClientCommand(client);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteClient(Client target, Category category) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteClosedClient(Client target, Category category) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateClient(Client target, Client editedPerson, Category category)
                throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addTutor(Client tutor) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addStudent(Client student) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addClosedTutor(Client tutor) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addClosedStudent(Client student) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredStudentList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredStudentList(Predicate<Client> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredTutorList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTutorList(Predicate<Client> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClosedTutorList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredClosedTutorList(Predicate<Client> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClosedStudentList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredClosedStudentList(Predicate<Client> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateRankedStudentList() {
            fail("This method should not be called.");
        }

        @Override
        public void updateRankedTutorList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByNameFilteredClientTutorList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByLocationFilteredClientTutorList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByGradeFilteredClientTutorList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortBySubjectFilteredClientTutorList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByNameFilteredClientStudentList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByLocationFilteredClientStudentList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByGradeFilteredClientStudentList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortBySubjectFilteredClientStudentList() {
            fail("This method should not be called.");
        }

        public void resetHighLight() {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateClientException extends AddClientCommandTest.ModelStub {
        @Override
        public void addTutor(Client tutor) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public void addStudent(Client student) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the client being added.
     */
    private class ModelStubAcceptingClientAdded extends AddClientCommandTest.ModelStub {
        final ArrayList<Client> tutorsAdded = new ArrayList<>();
        final ArrayList<Client> studentsAdded = new ArrayList<>();

        @Override
        public void addTutor(Client tutor) throws DuplicatePersonException {
            requireNonNull(tutor);
            tutorsAdded.add(tutor);
        }

        @Override
        public void addStudent(Client student) throws DuplicatePersonException {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
