package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.CommandHistory;
import seedu.progresschecker.logic.UndoRedoStack;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.Model;
import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.model.ReadOnlyProgressChecker;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.model.exercise.Exercise;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;
import seedu.progresschecker.model.person.exceptions.PersonNotFoundException;
import seedu.progresschecker.model.photo.PhotoPath;
import seedu.progresschecker.model.photo.exceptions.DuplicatePhotoException;
import seedu.progresschecker.testutil.IssueBuilder;

//@@author adityaa1998
public class CreateIssueCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullIssue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateIssueCommand(null);
    }

    @Test
    public void equals() {
        Issue one = new IssueBuilder().withTitle("one").build();
        Issue two = new IssueBuilder().withTitle("two").build();
        CreateIssueCommand createOneIssue = new CreateIssueCommand(one);
        CreateIssueCommand createTwoIssue = new CreateIssueCommand(two);

        // same object -> returns true
        assertTrue(createOneIssue.equals(createOneIssue));

        // same values -> returns true
        CreateIssueCommand createOneIssueCopy = new CreateIssueCommand(one);
        assertTrue(createOneIssue.equals(createOneIssue));

        // different types -> returns false
        assertFalse(createOneIssue.equals(1));

        // null -> returns false
        assertFalse(createOneIssue.equals(null));

        // different person -> returns false
        assertFalse(createOneIssue.equals(createTwoIssue));
    }

    @Test
    public void execute_authenticationError_throwsCommandException() throws Exception {
        ModelStub modelStub = new CreateIssueCommandTest.ModelStubCommandExceptionException();
        Issue validIssue = new IssueBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateIssueCommand.MESSAGE_FAILURE);

        getCreateIssueCommandForIssue(validIssue, modelStub).execute();
    }

    /**
     * Generates a new CreateIssueCommand with the details of the given issue.
     */
    private CreateIssueCommand getCreateIssueCommandForIssue(Issue issue, ModelStub model) {
        CreateIssueCommand command = new CreateIssueCommand(issue);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void execute_issueAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIssueAdded modelStub = new ModelStubAcceptingIssueAdded();
        Issue validIssue = new IssueBuilder().build();

        CommandResult commandResult = getCreateIssueCommandForIssue(validIssue, modelStub).execute();

        assertEquals(CreateIssueCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validIssue), modelStub.issueAdded);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void loginGithub(GitDetails gitdetails) throws IOException, CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void logoutGithub() throws CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void createIssueOnGitHub(Issue issue) throws IOException, CommandException {
            fail("This method should not be called. ");
        }

        @Override
        public void closeIssueOnGithub(Index index) throws IOException {
            fail("This method should not be called");
        }

        @Override
        public void listIssues(String state) throws IOException, CommandException, IllegalValueException {
            fail("This method should not be called");
        }

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void reopenIssueOnGithub(Index index) throws IOException, CommandException {
            fail("This method should not be called");
        }

        @Override
        public void sort() {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyProgressChecker newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyProgressChecker getProgressChecker() {
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

        @Override
        public void uploadPhoto(Person target, String path)
                throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateIssue(Index index, Issue editedIssue) throws IOException {
            fail("This method should not be called");
        }

        @Override
        public void addPhoto(PhotoPath photoPath) throws DuplicatePhotoException {
            fail("This method should not be called.");
        }

        @Override
        public void updateExercise(Exercise target, Exercise editedExercise) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Exercise> getFilteredExerciseList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Issue> getFilteredIssueList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredIssueList(Predicate<Issue> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a CommandException when trying to create a new issue.
     */
    private class ModelStubCommandExceptionException extends ModelStub {
        @Override
        public void createIssueOnGitHub(Issue issue) throws IOException, CommandException {
            throw new CommandException("");
        }

    }

    /**
     * A Model stub that always accept the issue being added.
     */
    private class ModelStubAcceptingIssueAdded extends ModelStub {
        final ArrayList<Issue> issueAdded = new ArrayList<>();

        @Override
        public void createIssueOnGitHub(Issue issue) throws IOException, CommandException {
            requireNonNull(issue);
            issueAdded.add(issue);
        }

        @Override
        public ReadOnlyProgressChecker getProgressChecker() {
            return new ProgressChecker();
        }
    }

}
