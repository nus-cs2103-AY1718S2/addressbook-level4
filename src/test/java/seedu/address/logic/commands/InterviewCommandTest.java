package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
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
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import java.time.LocalDateTime;

/**
 * Contains integration tests (interaction with the Model) and unit tests for InterviewCommand.
 */
public class InterviewCommandTest {

    public static final LocalDateTime VALID_DATETIME =
            LocalDateTime.ofEpochSecond(1540814400, 0, InterviewDate.LOCAL_ZONE_OFFSET);
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validDateUnfilteredList_success() throws Exception {
        Person firstPerson = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person scheduledPerson = new PersonBuilder(firstPerson).withInterviewDate(VALID_DATETIME).build();

        InterviewCommand interviewCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_DATETIME);
        String expectedMessage = String.format(InterviewCommand.MESSAGE_INTERVIEW_PERSON_SUCCESS,
                scheduledPerson.getName(), scheduledPerson.getInterviewDate().getDateTime().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, scheduledPerson);

        assertCommandSuccess(interviewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        InterviewCommand interviewCommand = prepareCommand(outOfBoundIndex, VALID_DATETIME);
        assertCommandFailure(interviewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        InterviewCommand interviewCommand = prepareCommand(outOfBoundIndex, VALID_DATETIME);
        assertCommandFailure(interviewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToInterview = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person scheduledPerson = new PersonBuilder(personToInterview).withInterviewDate(VALID_DATETIME).build();
        InterviewCommand interviewCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_DATETIME);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // interview -> first person interview date scheduled
        interviewCommand.execute();
        undoRedoStack.push(interviewCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person scheduled again
        expectedModel.updatePerson(personToInterview, scheduledPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        InterviewCommand interviewCommand = prepareCommand(outOfBoundIndex, VALID_DATETIME);

        // execution failed -> interviewCommand not pushed into undoRedoStack
        assertCommandFailure(interviewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonScheduled() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        InterviewCommand interviewCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_DATETIME);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToInterview = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person scheduledPerson = new PersonBuilder(personToInterview).withInterviewDate(VALID_DATETIME).build();
        // interview -> schedule interview for second person in unfiltered person list / first person in filtered person list
        interviewCommand.execute();
        undoRedoStack.push(interviewCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToInterview, scheduledPerson);
        assertNotEquals(personToInterview, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        // redo -> same second person scheduled again
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final InterviewCommand standardCommand = new InterviewCommand(INDEX_FIRST_PERSON, VALID_DATETIME);

        // same values -> returns true
        InterviewCommand commandWithSameValues = new InterviewCommand(INDEX_FIRST_PERSON, VALID_DATETIME);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new InterviewCommand(INDEX_SECOND_PERSON, VALID_DATETIME)));

        // different date time -> returns false
        assertFalse(standardCommand.equals(new InterviewCommand(INDEX_FIRST_PERSON, LocalDateTime.MIN)));
    }

    /**
     * Returns an {@code InterviewCommand}.
     */
    private InterviewCommand prepareCommand(Index index, LocalDateTime dateTime) {
        InterviewCommand interviewCommand = new InterviewCommand(index, dateTime);
        interviewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return interviewCommand;
    }
}
