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
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.report.Report;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.InterviewBuilder;

public class AddInterviewCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullInterview_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddInterviewCommand(null);
    }

    @Test
    public void execute_interviewAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingInterviewAdded modelStub = new ModelStubAcceptingInterviewAdded();
        Interview validInterview = new InterviewBuilder().build();

        CommandResult commandResult = getAddInterviewCommandForInterview(validInterview, modelStub).execute();

        assertEquals(String.format(AddInterviewCommand.MESSAGE_SUCCESS, validInterview), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validInterview), modelStub.interviewsAdded);
    }

    @Test
    public void execute_duplicateInterview_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateInterviewException();
        Interview validInterview = new InterviewBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddInterviewCommand.MESSAGE_DUPLICATE_INTERVIEW);

        getAddInterviewCommandForInterview(validInterview, modelStub).execute();
    }

    @Test
    public void equals() {
        Interview alice = new InterviewBuilder().withInterviewee("Alice").build();
        Interview bob = new InterviewBuilder().withInterviewee("Bob").build();
        AddInterviewCommand addAliceCommand = new AddInterviewCommand(alice);
        AddInterviewCommand addBobCommand = new AddInterviewCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddInterviewCommand addAliceCommandCopy = new AddInterviewCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddInterviewCommand getAddInterviewCommandForInterview(Interview interview, Model model) {
        AddInterviewCommand command = new AddInterviewCommand(interview);
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
        public void addJob(Job job) throws DuplicateJobException {
            fail("This method should not be called.");
        }

        @Override
        public void addInterview(Interview interview) throws DuplicateInterviewException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteInterview(Interview target) throws InterviewNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addReport(Report report) {

            fail("This method should not be called.");
        }

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
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void updateReport(Tag population) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public Report getReport() {
            fail("This method should not be called.");
            return null;
        };

        @Override
        public void refreshReport() {
            fail("This method should not be called.");
        };

        @Override
        public ObservableList<Report> getReportHistory() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredJobList(Predicate<Job> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Job> getFilteredJobList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteJob(Job target) throws JobNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateJob(Job target, Job editedJob)
                throws DuplicateJobException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Interview> getFilteredInterviewList() {
            fail("this method should not be called");
            return null;
        }

        @Override
        public void updateFilteredInterviewList(Predicate<Interview> predicate) {
            fail("This method should not be called.");
        }

    }

    /**
     * A Model stub that always throw a DuplicateInterviewException when trying to add a interview.
     */
    private class ModelStubThrowingDuplicateInterviewException extends ModelStub {
        @Override
        public void addInterview(Interview interview) throws DuplicateInterviewException {
            throw new DuplicateInterviewException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the interview being added.
     */
    private class ModelStubAcceptingInterviewAdded extends ModelStub {
        final ArrayList<Interview> interviewsAdded = new ArrayList<>();

        @Override
        public void addInterview(Interview interview) throws DuplicateInterviewException {
            requireNonNull(interview);
            interviewsAdded.add(interview);
        }

        @Override
        public void refreshReport() { }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
