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
import seedu.address.testutil.JobBuilder;

//@@author ChengSashankh
public class PostJobCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new PostJobCommand(null);
    }

    @Test
    public void execute_jobAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingJobAdded modelStub = new PostJobCommandTest.ModelStubAcceptingJobAdded();
        Job validJob = new JobBuilder().build();

        CommandResult commandResult = getPostJobCommandForJob(validJob, modelStub).execute();

        assertEquals(String.format(PostJobCommand.MESSAGE_SUCCESS, validJob), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validJob), modelStub.jobsAdded);
    }

    @Test
    public void execute_duplicateJob_throwsCommandException() throws Exception {
        PostJobCommandTest.ModelStub modelStub = new PostJobCommandTest.ModelStubThrowingDuplicateJobException();
        Job validJob = new JobBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(PostJobCommand.MESSAGE_DUPLICATE_JOB);

        getPostJobCommandForJob(validJob, modelStub).execute();
    }

    @Test
    public void equals() {
        Job backendJob = new JobBuilder().withJobTitle("Backend Job").build();
        Job frontendJob = new JobBuilder().withJobTitle("Frontend Job").build();

        PostJobCommand addBackendJobCommand = new PostJobCommand(backendJob);
        PostJobCommand addFrontendJobCommand = new PostJobCommand(frontendJob);

        // same object -> returns true
        assertTrue(addBackendJobCommand.equals(addBackendJobCommand));

        // same values -> returns true
        PostJobCommand addBackendJobCommandCopy = new PostJobCommand(backendJob);
        assertTrue(addBackendJobCommand.equals(addBackendJobCommandCopy));

        // different types -> returns false
        assertFalse(addBackendJobCommand.equals(1));

        // null -> returns false
        assertFalse(addBackendJobCommand.equals(null));

        // different person -> returns false
        assertFalse(addBackendJobCommand.equals(addFrontendJobCommand));
    }

    /**
     * Generates a new PostJobCommand with the details of the given job.
     */
    private PostJobCommand getPostJobCommandForJob(Job job, Model model) {
        PostJobCommand command = new PostJobCommand(job);
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
     * A Model stub that always throw a DuplicateJobException when trying to add a job.
     */
    private class ModelStubThrowingDuplicateJobException extends PostJobCommandTest.ModelStub {
        @Override
        public void addJob(Job job) throws DuplicateJobException {
            throw new DuplicateJobException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the job being added.
     */
    private class ModelStubAcceptingJobAdded extends PostJobCommandTest.ModelStub {
        final ArrayList<Job> jobsAdded = new ArrayList<>();

        @Override
        public void addJob(Job job) throws DuplicateJobException {
            requireNonNull(job);
            jobsAdded.add(job);
        }

        @Override
        public void refreshReport() { }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
//@@author
