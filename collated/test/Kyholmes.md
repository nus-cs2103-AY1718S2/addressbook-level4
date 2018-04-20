# Kyholmes-test
###### \src\test\java\seedu\address\logic\commands\AddAppointmentCommandTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.DateTime;

public class AddAppointmentCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullObject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAppointmentCommand(null, null);
    }

    @Test
    public void execute_unfilteredList_addSuccessful() throws Exception {
        Index testIndex = INDEX_FIRST_PERSON;
        DateTime testDateTime = ParserUtil.parseDateTime("8/8/2108 1400");
        AddAppointmentCommand command = prepareCommand(testIndex, testDateTime);
        CommandResult commandResult = command.execute();
        assertEquals(AddAppointmentCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_filteredList_addSuccessful() throws Exception {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Index testIndex = INDEX_FIRST_PERSON;
        DateTime testDateTime = ParserUtil.parseDateTime("8/8/2108 1400");
        AddAppointmentCommand command = prepareCommand(testIndex, testDateTime);
        CommandResult commandResult = command.execute();
        assertEquals(AddAppointmentCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_unfilteredListDuplicateAppointment_throwsCommandException() throws Exception {
        Index testIndex = INDEX_FIRST_PERSON;
        DateTime testDateTime = ParserUtil.parseDateTime("8/8/2108 1400");
        model.addPatientAppointment(model.getPatientFromListByIndex(testIndex), testDateTime);
        AddAppointmentCommand command = prepareCommand(testIndex, testDateTime);
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);
        command.execute();
    }

    @Test
    public void execute_unfilteredListDuplicateAppointmentMadeByOtherPatient_throwsCommandException() throws Exception {
        Index testIndex = INDEX_FIRST_PERSON;
        DateTime testDateTime = ParserUtil.parseDateTime("8/8/2108 1400");
        model.addPatientAppointment(model.getPatientFromListByIndex(INDEX_SECOND_PERSON), testDateTime);
        AddAppointmentCommand command = prepareCommand(testIndex, testDateTime);
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);
        command.execute();
    }

    @Test
    public void execute_filteredListDuplicateAppointment_throwsCommandException() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index testIndex = INDEX_FIRST_PERSON;
        DateTime testDateTime = ParserUtil.parseDateTime("8/8/2108 1400");
        model.addPatientAppointment(model.getPatientFromListByIndex(testIndex), testDateTime);
        AddAppointmentCommand command = prepareCommand(testIndex, testDateTime);
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);
        command.execute();
    }

    @Test
    public void execute_filteredListDuplicateAppointmentMadeByOtherPatient_throwsCommandException() throws Exception {
        Index testIndex = INDEX_FIRST_PERSON;
        DateTime testDateTime = ParserUtil.parseDateTime("8/8/2108 1400");
        model.addPatientAppointment(model.getPatientFromListByIndex(INDEX_SECOND_PERSON), testDateTime);
        AddAppointmentCommand command = prepareCommand(testIndex, testDateTime);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);
        command.execute();
    }

    @Test
    public void execute_unfilteredListInvalidIndex_throwsCommandException() throws Exception {
        Index testIndex = ParserUtil.parseIndex(model.getFilteredPersonList().size() + 1 + "");
        DateTime testDateTime = ParserUtil.parseDateTime("8/8/2108 1400");
        AddAppointmentCommand command = prepareCommand(testIndex, testDateTime);
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        command.execute();
    }

    @Test
    public void execute_filteredListInvalidIndex_throwsCommandException() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index testIndex = INDEX_SECOND_PERSON;
        DateTime testDateTime = ParserUtil.parseDateTime("8/8/2108 1400");
        AddAppointmentCommand command = prepareCommand(testIndex, testDateTime);
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        command.execute();
    }

    @Test
    public void equals() throws IllegalValueException {
        Index firstIndex = ParserUtil.parseIndex("1");
        Index secondIndex = ParserUtil.parseIndex("2");

        DateTime firstDateTime = ParserUtil.parseDateTime("1/1/2108 1100");
        DateTime secondDateTime = ParserUtil.parseDateTime("2/1/2108 1100");

        AddAppointmentCommand addAppointmentFirstIndexFirstDateTimeCommand =
                new AddAppointmentCommand(firstIndex, firstDateTime);

        AddAppointmentCommand addAppointmentFirstIndexSecondDateTimeCommand =
                new AddAppointmentCommand(firstIndex, secondDateTime);

        AddAppointmentCommand addAppointmentSecondIndexFirstDateTimeCommand =
                new AddAppointmentCommand(secondIndex, firstDateTime);

        AddAppointmentCommand addAppointmentSecondIndexSecondDateTimeCommand =
                new AddAppointmentCommand(secondIndex, secondDateTime);

        //same object -> return true
        assertTrue(addAppointmentFirstIndexFirstDateTimeCommand
                .equals(addAppointmentFirstIndexFirstDateTimeCommand));

        //same values -> returns true
        AddAppointmentCommand addAppointmentFirstIndexFirstDateTimeCommandCopy =
                new AddAppointmentCommand(firstIndex, firstDateTime);
        assertTrue(addAppointmentFirstIndexFirstDateTimeCommand
                .equals(addAppointmentFirstIndexFirstDateTimeCommandCopy));

        //different types -> returns false
        assertFalse(addAppointmentFirstIndexFirstDateTimeCommand.equals(1));

        //null -> returns false
        assertFalse(addAppointmentFirstIndexFirstDateTimeCommand.equals(null));

        //different pattern -> returns false
        assertFalse(addAppointmentFirstIndexFirstDateTimeCommand
                .equals(addAppointmentFirstIndexSecondDateTimeCommand));
        assertFalse(addAppointmentFirstIndexFirstDateTimeCommand
                .equals(addAppointmentSecondIndexFirstDateTimeCommand));
        assertFalse(addAppointmentFirstIndexFirstDateTimeCommand
                .equals(addAppointmentSecondIndexSecondDateTimeCommand));
    }

    private AddAppointmentCommand prepareCommand(Index index, DateTime dateTime) {
        AddAppointmentCommand command = new AddAppointmentCommand(index, dateTime);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \src\test\java\seedu\address\logic\commands\AddPatientQueueCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
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
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.TypicalPatients;

public class AddPatientQueueCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullObject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPatientQueueCommand(null);
    }

    @Test
    public void equals() throws IllegalValueException {
        Index firstIndex = ParserUtil.parseIndex(INDEX_FIRST_PERSON.getOneBased() + "");
        Index secondIndex = ParserUtil.parseIndex(INDEX_SECOND_PERSON.getOneBased() + "");

        AddPatientQueueCommand addQueueFirstCommand = new AddPatientQueueCommand(firstIndex);
        AddPatientQueueCommand addQueueSecondCommand = new AddPatientQueueCommand(secondIndex);

        // same object -> returns true
        assertTrue(addQueueFirstCommand.equals(addQueueFirstCommand));

        // same values -> returns true
        AddPatientQueueCommand addQueueFirstCommandCopy = new AddPatientQueueCommand(firstIndex);
        assertTrue(addQueueFirstCommand.equals(addQueueFirstCommandCopy));

        // different types -> returns false
        assertFalse(addQueueFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addQueueFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(addQueueFirstCommand.equals(addQueueSecondCommand));
    }

    @Test
    public void execute_validIndexUnfilteredList_addSuccessful() throws Exception {
        AddPatientQueueCommand command = prepareCommand(INDEX_SECOND_PERSON.getOneBased() + "");
        CommandResult commandResult = command.execute();
        assertEquals(String.format(AddPatientQueueCommand.MESSAGE_SUCCESS, TypicalPatients.BENSON.getName()),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        String outOfBoundIndexString = model.getFilteredPersonList().size() + 1 + "";
        AddPatientQueueCommand command = prepareCommand(outOfBoundIndexString);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexUnfilteredListDuplicatePatient_throwsCommandException() throws Exception {
        prepareForDuplicatePatient();
        AddPatientQueueCommand duplicateCommand = prepareCommand(INDEX_SECOND_PERSON.getOneBased() + "");
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddPatientQueueCommand.MESSAGE_DUPLICATE_PERSON);
        duplicateCommand.execute();
    }

    @Test
    public void execute_validIndexFilteredList_addSuccessful() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Patient patientToAddQueue = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddPatientQueueCommand command = prepareCommand(INDEX_FIRST_PERSON.getOneBased() + "");
        String expectedMessage = String.format(AddPatientQueueCommand.MESSAGE_SUCCESS,
                patientToAddQueue.getName().fullName);
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());
        expectedModel.addPatientToQueue(INDEX_FIRST_PERSON);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getImdb().getPersonList().size());
        AddPatientQueueCommand command = prepareCommand(INDEX_SECOND_PERSON.getOneBased() + "");
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        AddPatientQueueCommand command = prepareCommand(INDEX_FIRST_PERSON.getOneBased() + "");
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());

        //addPatientQueue -> first patient added into queue
        command.execute();
        undoRedoStack.push(command);

        //undo -> reverts imdb back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> same first patient added into queue
        expectedModel.addPatientToQueue(INDEX_FIRST_PERSON);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        String outOfBoundIndexString = model.getFilteredPersonList().size() + 1 + "";
        AddPatientQueueCommand command = prepareCommand(outOfBoundIndexString);

        //execute failed -> addPatientQueueCommand not pushed into undoRedoStack
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Adds a patient from a filtered list by its index (after filtering).
     * 2. Undo the add patient into queue operation.
     * 3. Verify that the index of the previously added patient in the unfiltered list is different from the index at
     * the filtered list.
     * 4. Redo the add patient into queue operation. This ensures {@code RedoCommand} adds the patient into queue
     * regardless of indexing.
     */
    @Test
    public void executeUdoRedo_validIndexFilteredList_samePersonAdded() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        AddPatientQueueCommand command = prepareCommand(INDEX_FIRST_PERSON.getOneBased() + "");
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Patient patientToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        //addPatientQueue -> adds second patient in unfiltered patient list into queue /
        //first patient in filtered patient list
        command.execute();
        undoRedoStack.push(command);

        //undo -> reverts imdb back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.addPatientToQueue(INDEX_FIRST_PERSON);
        assertNotEquals(patientToAdd, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        //redo -> adds same second patient in unfiltered patient list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code AddPatientQueueCommand}.
     */
    private AddPatientQueueCommand prepareCommand(String userInput) throws IllegalValueException {
        AddPatientQueueCommand command =
                new AddPatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    private void prepareForDuplicatePatient() throws Exception {
        AddPatientQueueCommand duplicateCommand = prepareCommand(INDEX_SECOND_PERSON.getOneBased() + "");
        duplicateCommand.execute();
    }
}
```
###### \src\test\java\seedu\address\logic\commands\DeleteAppointmentCommandTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DeleteAppointmentCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.DateTime;

public class DeleteAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws IllegalValueException {
        Index firstIndex = ParserUtil.parseIndex("1");
        Index secondIndex = ParserUtil.parseIndex("2");

        DateTime firstDateTime = ParserUtil.parseDateTime("1/1/2018 1100");
        DateTime secondDateTime = ParserUtil.parseDateTime("2/1/2018 1100");

        DeleteAppointmentCommand deleteAppointmentFirstIndexFirstDateTimeCommand =
                new DeleteAppointmentCommand(firstIndex, firstDateTime);

        DeleteAppointmentCommand deleteAppointmentFirstIndexSecondDateTimeCommand =
                new DeleteAppointmentCommand(firstIndex, secondDateTime);

        DeleteAppointmentCommand deleteAppointmentSecondIndexFirstDateTimeCommand =
                new DeleteAppointmentCommand(secondIndex, firstDateTime);

        DeleteAppointmentCommand deleteAppointmentSecondIndexSecondDateTimeCommand =
                new DeleteAppointmentCommand(secondIndex, secondDateTime);

        //same object -> return true
        assertTrue(deleteAppointmentFirstIndexFirstDateTimeCommand
                .equals(deleteAppointmentFirstIndexFirstDateTimeCommand));

        //same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstIndexFirstDateTimeCommandCopy =
                new DeleteAppointmentCommand(firstIndex, firstDateTime);
        assertTrue(deleteAppointmentFirstIndexFirstDateTimeCommand
                .equals(deleteAppointmentFirstIndexFirstDateTimeCommandCopy));

        //different types -> returns false
        assertFalse(deleteAppointmentFirstIndexFirstDateTimeCommand.equals(1));

        //null -> returns false
        assertFalse(deleteAppointmentFirstIndexFirstDateTimeCommand.equals(null));

        //different pattern -> returns false
        assertFalse(deleteAppointmentFirstIndexFirstDateTimeCommand
                .equals(deleteAppointmentFirstIndexSecondDateTimeCommand));
        assertFalse(deleteAppointmentFirstIndexFirstDateTimeCommand
                .equals(deleteAppointmentSecondIndexFirstDateTimeCommand));
        assertFalse(deleteAppointmentFirstIndexFirstDateTimeCommand
                .equals(deleteAppointmentSecondIndexSecondDateTimeCommand));
    }

    @Test
    public void execute_appointmentExistUnfilteredList_deleteSuccessful() throws Exception {
        model.addPatientAppointment(model.getPatientFromListByIndex(INDEX_FIRST_PERSON),
                ParserUtil.parseDateTime("15/5/2018 1600"));
        DeleteAppointmentCommand command = prepareCommand("1 15/5/2018 1600");
        CommandResult commandResult = command.execute();
        assertEquals(DeleteAppointmentCommand.MESSAGE_DELETE_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_appointmentExistFilteredList_deleteSuccessful() throws Exception {
        model.addPatientAppointment(model.getPatientFromListByIndex(INDEX_FIRST_PERSON),
                ParserUtil.parseDateTime("15/5/2018 1600"));
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        DeleteAppointmentCommand command = prepareCommand("1 15/5/2018 1600");
        CommandResult commandResult = command.execute();
        assertEquals(DeleteAppointmentCommand.MESSAGE_DELETE_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_appointmentNotExist_throwsCommandException() throws Exception {
        DeleteAppointmentCommand command = prepareCommand("2 15/5/2018 1600");
        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteAppointmentCommand.MESSAGE_APPOINTMENT_CANNOT_BE_FOUND);
        command.execute();
    }

    @Test
    public void execute_indexInvalidUnfilteredList_throwsCommandException() throws Exception {
        String indexString = model.getFilteredPersonList().size() + 1 + "";
        DeleteAppointmentCommand command = prepareCommand(indexString + " 15/5/2018 1600");
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        command.execute();
    }

    @Test
    public void execute_indexInvalidFilteredList_throwsCommandException() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        DeleteAppointmentCommand command = prepareCommand("2 15/5/2018 1600");
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        command.execute();
    }

    private DeleteAppointmentCommand prepareCommand(String userInput) throws ParseException {
        DeleteAppointmentCommand command = new DeleteAppointmentCommandParser().parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \src\test\java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void execute_validIndexUnfilteredListPatientInQueue_throwsCommandException() throws Exception {
        model.addPatientToQueue(INDEX_SECOND_PERSON);
        Patient patientToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_SECOND_PERSON);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_PERSONS_EXIST_IN_QUEUE,
                patientToDelete.getName().fullName));
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredListPatientInQueue_faiure() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        model.addPatientToQueue(INDEX_SECOND_PERSON);
        Patient patientToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_SECOND_PERSON);

        //execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_PERSONS_EXIST_IN_QUEUE,
                patientToDelete.getName().fullName));

        //no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

```
###### \src\test\java\seedu\address\logic\commands\RemovePatientQueueCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.TypicalPatients;

public class RemovePatientQueueCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_emptyQueue_throwsCommandException() throws CommandException {
        RemovePatientQueueCommand removeEmptyQueueCommand = prepareEmptyQueueCommand();
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_QUEUE_EMPTY);
        removeEmptyQueueCommand.execute();
    }

    @Test
    public void execute_patientExist_removeSuccessful() throws CommandException, IllegalValueException {
        RemovePatientQueueCommand command = prepareCommand();
        CommandResult commandResult = command.execute();
        assertEquals(String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                TypicalPatients.ALICE.getName().fullName), commandResult.feedbackToUser);
    }

    @Test
    public void execute_patientExist_removeByIndexSuccessful() throws CommandException,
            IllegalValueException {
        RemovePatientQueueCommand command = prepareCommandMorePatient(INDEX_SECOND_PERSON.getOneBased() + "");
        CommandResult commandResult = command.execute();
        assertEquals(String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                TypicalPatients.BENSON.getName().fullName), commandResult.feedbackToUser);
    }

    @Test
    public void execute_emptyQueueRemoveByIndex_throwsCommandException() throws IllegalValueException,
            CommandException {
        RemovePatientQueueCommand command = prepareCommandEmptyQueueIndex(INDEX_SECOND_PERSON.getOneBased()
                + "");
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_QUEUE_EMPTY);
        command.execute();
    }

    @Test
    public void execute_patientNotExistInQueue_throwsCommandException() throws CommandException,
            IllegalValueException {
        RemovePatientQueueCommand command = prepareCommandMorePatientNotExist(INDEX_SECOND_PERSON.getOneBased()
                + "");
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_PERSON_NOT_FOUND_QUEUE);
        command.execute();
    }

    @Test
    public void execute_removeByInvalidIndex_throwsCommandException() throws Exception {
        String outOfBoundIndexString = model.getFilteredPersonList().size() + 1 + "";
        RemovePatientQueueCommand command = prepareCommandMorePatient(outOfBoundIndexString);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_removeByValidIndexFilteredList_success() throws Exception {
        Patient patientToRemove = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        RemovePatientQueueCommand command = prepareCommandMorePatient(INDEX_SECOND_PERSON.getOneBased() + "");
        String expectedMessage = String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                patientToRemove.getName().fullName);
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());
        expectedModel.removePatientFromQueueByIndex(INDEX_SECOND_PERSON);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_removeWithoutIndex_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePatientQueueCommand command = prepareCommand();
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());

        //remove -> first patient removed from queue
        command.execute();
        undoRedoStack.push(command);

        //undo -> reverts imdb back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> same first patient removed again
        expectedModel.removePatientFromQueue();
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_removeWithIndex_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePatientQueueCommand command = prepareCommandMorePatient(INDEX_SECOND_PERSON.getOneBased() + "");
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());

        //remove -> second patient removed
        command.execute();
        undoRedoStack.push(command);

        //undo -> reverts imdb back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> same second patient deleted again
        expectedModel.removePatientFromQueueByIndex(INDEX_SECOND_PERSON);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_removeWithValidIndexFilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePatientQueueCommand command = prepareCommandMorePatient(INDEX_THIRD_PERSON.getOneBased() + "");
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        //removePatientQueue -> remove third patient in unfiltered patient from queue
        command.execute();
        undoRedoStack.push(command);

        //undo -> reverts imdb back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> remove same third patient in unfiltered patient list from queue
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_emptyQueueRemoveWithoutIndex_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePatientQueueCommand command = prepareEmptyQueueCommand();

        //execution failed -> remove command not pushed into undoRedoStack
        assertCommandFailure(command, model, RemovePatientQueueCommand.MESSAGE_QUEUE_EMPTY);

        //no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_removeWithInvalidIndex_failure() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        String outOfBoundIndexString = model.getFilteredPersonList().size() + 1 + "";
        RemovePatientQueueCommand command = prepareCommandMorePatient(outOfBoundIndexString);

        //execution failed -> command not pushed into undoRedoStack
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_removeWithValidIndexPatientNotInQueue_failure() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePatientQueueCommand command = prepareCommandMorePatientNotExist(INDEX_SECOND_PERSON.getOneBased()
                + "");

        //execution failed -> command not pushed into undoRedoStack
        assertCommandFailure(command, model, RemovePatientQueueCommand.MESSAGE_PERSON_NOT_FOUND_QUEUE);

        //no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    private RemovePatientQueueCommand prepareEmptyQueueCommand() {
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private RemovePatientQueueCommand prepareCommandEmptyQueueIndex(String userInput) throws IllegalValueException {
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommand() throws IllegalValueException {
        model.addPatientToQueue(INDEX_FIRST_PERSON);
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommandMorePatient(String userInput) throws IllegalValueException {
        model.addPatientToQueue(INDEX_FIRST_PERSON);
        model.addPatientToQueue(INDEX_SECOND_PERSON);
        model.addPatientToQueue(INDEX_THIRD_PERSON);
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommandMorePatientNotExist(String userInput) throws IllegalValueException {
        model.addPatientToQueue(INDEX_FIRST_PERSON);
        model.addPatientToQueue(INDEX_THIRD_PERSON);
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void equals() {
        RemovePatientQueueCommand removePatientQueueFirstCommand = new RemovePatientQueueCommand();

        //same object -> returns true
        assertTrue(removePatientQueueFirstCommand.equals(removePatientQueueFirstCommand));

        //different types -> return false
        assertFalse(removePatientQueueFirstCommand.equals(1));

        //null -> returns false
        assertFalse(removePatientQueueFirstCommand.equals(null));
    }
}
```
###### \src\test\java\seedu\address\logic\commands\ViewAppointmentCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowCalendarViewRequestEvent;
import seedu.address.commons.events.ui.ShowPatientAppointmentRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ViewAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFilteredViewAppointmentWithoutIndex_success() throws CommandException {
        ViewAppointmentCommand command = new ViewAppointmentCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertExecutionSuccessWithoutIndex(command);
    }

    @Test
    public void execute_listIsFilteredViewAppointmentWithoutIndex_success() throws CommandException {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        ViewAppointmentCommand command = new ViewAppointmentCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertExecutionSuccessWithoutIndex(command);
    }

    @Test
    public void execute_listIsNotFilteredViewAppointmentWithValidIndex_success() {
        assertExecutionSuccessWithIndex(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_listIsFilteredViewAppointmentWithValidIndex_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        assertExecutionSuccessWithIndex(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_listIsNotFilteredViewAppointmentWithInvalidIndex_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_listIsFilteredViewAppointmentWithInvalidIndex_failure() {
        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        assertExecutionFailure(INDEX_THIRD_PERSON, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws IllegalValueException {
        Index firstIndex = ParserUtil.parseIndex(INDEX_FIRST_PERSON.getOneBased() + "");
        Index secondIndex = ParserUtil.parseIndex(INDEX_SECOND_PERSON.getOneBased() + "");

        ViewAppointmentCommand viewAppointmentFirstCommand = new ViewAppointmentCommand(firstIndex);
        ViewAppointmentCommand viewAppointmentSecondCommand = new ViewAppointmentCommand(secondIndex);

        //same object -> returns true
        assertTrue(viewAppointmentFirstCommand.equals(viewAppointmentFirstCommand));

        //same values -> returns true
        ViewAppointmentCommand viewAppointmentFirstCommandCopy = new ViewAppointmentCommand(firstIndex);
        assertTrue(viewAppointmentFirstCommand.equals(viewAppointmentFirstCommandCopy));

        //null -> returns false
        assertFalse(viewAppointmentFirstCommand.equals(null));

        //different patient -> returns false
        assertFalse(viewAppointmentFirstCommand.equals(viewAppointmentSecondCommand));
    }

    /**
     * Executes a {@code ViewAppointmentCommand} without index as parameter and checks that
     * {@code ShowCalendarViewRequest} carry the correct appointment entry list
     */
    private void assertExecutionSuccessWithoutIndex(ViewAppointmentCommand command) throws CommandException {
        try {
            CommandResult commandResult = command.execute();
            assertEquals(ViewAppointmentCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new CommandException("Execution of command should not fail");
        }

        ShowCalendarViewRequestEvent lastEvent = (ShowCalendarViewRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();

        assertEquals(model.getAppointmentEntryList(), lastEvent.appointmentEntries);
    }

    /**
     * Executes a {@code ViewAppointmentCommand} with the given {@code index} and checks that
     * {@code ShowPatientAppointmentRequestEvent} is raised with the patient appointments
     */
    private void assertExecutionSuccessWithIndex(Index index) {
        ViewAppointmentCommand command = prepareCommand(index);
        Patient targetPatient = model.getPatientFromListByIndex(index);
        try {
            CommandResult commandResult = command.execute();
            assertEquals(String.format(command.MESSAGE_SUCCESS_PATIENT, targetPatient.getName().fullName),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail", ce);
        }
        ShowPatientAppointmentRequestEvent lastEvent = (ShowPatientAppointmentRequestEvent) eventsCollectorRule
                .eventsCollector.getMostRecent();
        assertEquals(targetPatient, lastEvent.data);
    }

    /**
     * Executes a {@code ViewAppointmentCommand} with the given {@code index}, and checks a {@code CommandException} is
     * thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ViewAppointmentCommand command = prepareCommand(index);

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }

    }

    /**
     * Parses {@code userInput} into a {@code ViewAppointmentCommand}.
     */
    private ViewAppointmentCommand prepareCommand(Index targetIndex) {
        ViewAppointmentCommand command = new ViewAppointmentCommand(targetIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \src\test\java\seedu\address\logic\parser\AddAppointmentCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAppointmentCommand;

public class AddAppointmentCommandParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTime_throwParseException() {
        assertParseFailure(parser, "1 2/4/2108", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingDate_throwParseException() {
        assertParseFailure(parser, "1 1200", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwParseException() {
        assertParseFailure(parser, "2/4/2108 1030", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwParseException() {
        assertParseFailure(parser, "alex 2/4/2108 1030", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDate_throwParseException() {
        assertParseFailure(parser, "1 2/April/2108 1030", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTime_throwParseException() {
        assertParseFailure(parser, "1 2/4/2108 10:30am", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_dateTimeArgSwap_throwParseException() {
        assertParseFailure(parser, "1 1030 2/4/2108", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAddAppointmentCommand() throws IllegalValueException {
        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(INDEX_FIRST_PERSON,
                ParserUtil.parseDateTime("2/4/2108 1030"));
        assertParseSuccess(parser, "1 2/4/2108 1030", expectedCommand);
        assertParseSuccess(parser, "\n 1 2/4/2108 1030 \n", expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\logic\parser\AddPatientQueueCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPatientQueueCommand;

public class AddPatientQueueCommandParserTest {
    private AddPatientQueueCommandParser parser = new AddPatientQueueCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPatientQueueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsString_throwsParseException() {
        assertParseFailure(parser, "alice", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPatientQueueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsNegativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPatientQueueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAddPatientQueueCommand() throws IllegalValueException {
        AddPatientQueueCommand expectedCommand = new AddPatientQueueCommand(ParserUtil.parseIndex("1"));
        assertParseSuccess(parser, "1", expectedCommand);

        assertParseSuccess(parser, "\n 1 \n", expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\logic\parser\DeleteAppointmentCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteAppointmentCommand;

public class DeleteAppointmentCommandParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_empty_throwParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTime_throwParseException() {
        assertParseFailure(parser, "1 14/3/2018", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingDate_throwParseException() {
        assertParseFailure(parser, "1 1200", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwParseException() {
        assertParseFailure(parser, "14/3/2018 1200", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwParseException() {
        assertParseFailure(parser, "alex 14/3/2018 1200", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDate_throwParseException() {
        assertParseFailure(parser, "1 14/three/2018 1200", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTime_throwParseException() {
        assertParseFailure(parser, "1 14/3/2018 12pm", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_dateTimeArgSwap_throwParseException() {
        assertParseFailure(parser, "1 1200 14/3/2018", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnDeleteAppointmentCommand() throws IllegalValueException {
        DeleteAppointmentCommand expectedCommand = new DeleteAppointmentCommand(
                ParserUtil.parseIndex("1"), ParserUtil.parseDateTime("14/3/2018 1200"));
        assertParseSuccess(parser, "1 14/3/2018 1200", expectedCommand);
        assertParseSuccess(parser, "\n 1 14/3/2018 1200 \n", expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\logic\parser\ImdbParserTest.java
``` java
    @Test
    public void parseCommand_viewAppointment() throws Exception {
        LoginManager.authenticate("bob", "password456");
        ViewAppointmentCommand command = (ViewAppointmentCommand) parser.parseCommand(
                ViewAppointmentCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ViewAppointmentCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_viewAppointmentCommandAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        ViewAppointmentCommand command = (ViewAppointmentCommand) parser.parseCommand(
                ViewAppointmentCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ViewAppointmentCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteAppointment() throws Exception {
        LoginManager.authenticate("bob", "password456");
        String indexString = "1";
        String dateTimeString = "16/4/2018 1400";
        DeleteAppointmentCommand command = (DeleteAppointmentCommand) parser.parseCommand(
                DeleteAppointmentCommand.COMMAND_WORD + " " + indexString + " " + dateTimeString);
        assertEquals(new DeleteAppointmentCommand(ParserUtil.parseIndex("1"),
                ParserUtil.parseDateTime(dateTimeString)), command);
    }

    @Test
    public void parseCommand_deleteAppointmentCommandAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        String indexString = "1";
        String dateTimeString = "16/4/2018 1400";
        DeleteAppointmentCommand command = (DeleteAppointmentCommand) parser.parseCommand(
                DeleteAppointmentCommand.COMMAND_ALIAS + " " + indexString + " " + dateTimeString);
        assertEquals(new DeleteAppointmentCommand(ParserUtil.parseIndex("1"),
                ParserUtil.parseDateTime(dateTimeString)), command);
    }

    @Test
    public void parseCommand_addAppointment() throws Exception {
        LoginManager.authenticate("bob", "password456");
        String indexString = "1";
        String dateTimeString = "16/4/2108 1400";
        AddAppointmentCommand command = (AddAppointmentCommand) parser.parseCommand(
                AddAppointmentCommand.COMMAND_WORD + " " + indexString + " " + dateTimeString);
        assertEquals(new AddAppointmentCommand(ParserUtil.parseIndex("1"),
                ParserUtil.parseDateTime(dateTimeString)), command);
    }

    @Test
    public void parseCommand_addAppointmentCommandAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        String indexString = "1";
        String dateTimeString = "16/4/2108 1400";
        AddAppointmentCommand command = (AddAppointmentCommand) parser.parseCommand(
                AddAppointmentCommand.COMMAND_ALIAS + " " + indexString + " " + dateTimeString);
        assertEquals(new AddAppointmentCommand(ParserUtil.parseIndex("1"),
                ParserUtil.parseDateTime(dateTimeString)), command);
    }

    @Test
    public void parseCommand_addPatientQueue() throws Exception {
        LoginManager.authenticate("bob", "password456");
        AddPatientQueueCommand command = (AddPatientQueueCommand) parser.parseCommand(
                AddPatientQueueCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new AddPatientQueueCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_addPatientQueueAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        AddPatientQueueCommand command = (AddPatientQueueCommand) parser.parseCommand(
                AddPatientQueueCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new AddPatientQueueCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_removePatientQueue_emptyArgs() throws Exception {
        LoginManager.authenticate("bob", "password456");
        assertTrue(parser.parseCommand(RemovePatientQueueCommand.COMMAND_WORD) instanceof RemovePatientQueueCommand);
        assertTrue(parser.parseCommand(
                RemovePatientQueueCommand.COMMAND_WORD + " ") instanceof RemovePatientQueueCommand);
    }

    @Test
    public void parseCommand_removePatientQueue_byIndex() throws Exception {
        RemovePatientQueueCommand command = (RemovePatientQueueCommand) parser.parseCommand(
                RemovePatientQueueCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new RemovePatientQueueCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_removePatientQueueAlias_emptyArgs() throws Exception {
        LoginManager.authenticate("bob", "password456");
        assertTrue(parser.parseCommand(RemovePatientQueueCommand.COMMAND_ALIAS) instanceof RemovePatientQueueCommand);
        assertTrue(parser.parseCommand(
                RemovePatientQueueCommand.COMMAND_ALIAS + " ") instanceof RemovePatientQueueCommand);
    }

    @Test
    public void parseCommand_removePatientQueueAlias_byIndex() throws Exception {
        RemovePatientQueueCommand command = (RemovePatientQueueCommand) parser.parseCommand(
                RemovePatientQueueCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new RemovePatientQueueCommand(INDEX_FIRST_PERSON), command);
    }

```
###### \src\test\java\seedu\address\logic\parser\RemovePatientQueueCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.RemovePatientQueueCommand;

public class RemovePatientQueueCommandParserTest {
    private RemovePatientQueueCommandParser parser = new RemovePatientQueueCommandParser();

    @Test
    public void parse_validArgs_returnsRemovePatientQueueCommand() {
        assertParseSuccess(parser, "1", new RemovePatientQueueCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgsNegativeValue_throwsParserException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovePatientQueueCommand.MESSAGE_USAGE_INDEX));
    }

    @Test
    public void parse_invalidArgsAlphaValue_throwsParserException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovePatientQueueCommand.MESSAGE_USAGE_INDEX));
    }
}
```
###### \src\test\java\seedu\address\logic\parser\ViewAppointmentCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ViewAppointmentCommand;

public class ViewAppointmentCommandParserTest {

    private ViewAppointmentCommandParser parser = new ViewAppointmentCommandParser();

    @Test
    public void parse_invalidArgsString_throwsParseException() {
        assertParseFailure(parser, "alice", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAppointmentCommand.MESSAGE_USAGE_PATIENT_WITH_INDEX));
    }

    @Test
    public void parse_invalidArgsNegativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAppointmentCommand.MESSAGE_USAGE_PATIENT_WITH_INDEX));
    }

    @Test
    public void parse_validArgs_returnsViewAppointmentCommand() {
        ViewAppointmentCommand expectedCommand = new ViewAppointmentCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", expectedCommand);
        assertParseSuccess(parser, "\n 1 \n", expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\model\appointment\AppointmentEntryTest.java
``` java
package seedu.address.model.appointment;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AppointmentEntryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AppointmentEntry(null, null));
    }
}
```
###### \src\test\java\seedu\address\model\appointment\AppointmentTest.java
``` java
package seedu.address.model.appointment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AppointmentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null));
    }

    @Test
    public void test_getAppointmentDateTimeString() {
        String date = "3/4/2017";
        String time = "2217";
        String dateTimeString = "3/4/2017 2217";

        Appointment toTestAppt = new Appointment(dateTimeString);

        assertEquals(toTestAppt.getAppointmentDateTimeString(), date + " " + time);
    }
}
```
###### \src\test\java\seedu\address\model\appointment\DateTimeTest.java
``` java
package seedu.address.model.appointment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

public class DateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void test_toString() {
        String date = "3/4/2017";
        String time = "2217";
        String dateTimeString = "3/4/2017 2217";

        DateTime toTest = new DateTime(dateTimeString);

        assertEquals(toTest.toString(), date + " " + time);
    }

    @Test
    public void test_isBefore_valid() throws ParseException {
        String dateTimeString = "3/4/2017 2217";

        assertTrue(DateTime.isBefore(dateTimeString));
    }

    @Test
    public void test_isBefore_invalid() throws ParseException {
        String dateTimeString = "3/4/2107 2217";

        assertFalse(DateTime.isBefore(dateTimeString));
    }

    @Test
    public void test_isAfterOrEqual_valid() throws ParseException {
        String dateTimeString = "3/4/2107 2217";

        assertTrue(DateTime.isAfterOrEqual(dateTimeString));
    }

    @Test
    public void test_isAfterOrEqual_invalid() throws ParseException {
        String dateTimeString = "3/4/2017 2217";

        assertFalse(DateTime.isAfterOrEqual(dateTimeString));
    }

    @Test
    public void test_isValidDateTime_valid() {
        String dateTimeString = "3/4/2017 2217";

        assertTrue(DateTime.isValidDateTime(dateTimeString));
    }

    @Test
    public void test_isValidDateTime_invalid() {
        String dateTimeString = "3 April 2017 2217";

        assertFalse(DateTime.isValidDateTime(dateTimeString));
    }
}
```
###### \src\test\java\seedu\address\model\ImdbTest.java
``` java
    @Test
    public void getVisitingQueue_modifyQueue_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        imdb.getUniquePatientQueue().remove(0);
    }

    @Test
    public void getAppointmentEntryList_modifyList_throwsUnsupportedOperationException() {
        AppointmentEntry entry = new AppointmentEntry(new Appointment("3/4/2017 1030"),
                "test");
        thrown.expect(UnsupportedOperationException.class);
        imdb.getAppointmentEntryList().add(entry);
    }

    @Test
    public void addPatientAppointment_imdbUpdate() throws Exception {
        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();
        DateTime toAddDateTime = ParserUtil.parseDateTime("2/4/2108 1500");
        imdbWithAmyAndBob.addAppointment(AMY, toAddDateTime);
        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void deletePatientAppointment_imdbUpdate() throws Exception {
        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();
        DateTime targetDateTime = ParserUtil.parseDateTime("2/4/2108 1500");
        imdbWithAmyAndBob.addAppointment(AMY, targetDateTime);
        assertEquals(imdbWithAmyAndBob, expectedImdb);
        imdbWithAmyAndBob.deletePatientAppointment(AMY, new Appointment(targetDateTime.toString()));
        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void addPatientToQueue_queueUpdate() throws DuplicatePatientException {
        imdbWithAmyAndBob.addPatientToQueue(1);
        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();

        expectedImdb.addPatientToQueue(1);

        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void addPatientToQueue_duplicateIndex() throws DuplicatePatientException {
        imdbWithAmyAndBob.addPatientToQueue(1);

        thrown.expect(DuplicatePatientException.class);

        imdbWithAmyAndBob.addPatientToQueue(1);
    }

    @Test
    public void removePatientFromQueue_queueUpdate() throws DuplicatePatientException, PatientNotFoundException {
        imdbWithAmyAndBob.addPatientToQueue(1);
        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();

        expectedImdb.addPatientToQueue(1);

        imdbWithAmyAndBob.removePatientFromQueue();
        expectedImdb.removePatientFromQueue();

        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void removePatientFromQueueByIndex_queueUpdate() throws DuplicatePatientException, PatientNotFoundException {
        imdbWithAmyAndBob.addPatientToQueue(2);
        imdbWithAmyAndBob.addPatientToQueue(1);
        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();
        expectedImdb.addPatientToQueue(2);
        expectedImdb.addPatientToQueue(1);
        imdbWithAmyAndBob.removePatientFromQueueByIndex(1);
        expectedImdb.removePatientFromQueueByIndex(1);
        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void removePatientByIndex_notInQueue() throws DuplicatePatientException, PatientNotFoundException {
        imdbWithAmyAndBob.addPatientToQueue(2);
        thrown.expect(PatientNotFoundException.class);
        imdbWithAmyAndBob.removePatientFromQueueByIndex(1);
    }

    @Test
    public void removePatientFromQueue_emptyQueue() throws PatientNotFoundException {
        thrown.expect(PatientNotFoundException.class);
        imdbWithAmyAndBob.removePatientFromQueue();
    }

    @Test
    public void removePatientByIndex_emptyQueue() throws PatientNotFoundException {
        thrown.expect(PatientNotFoundException.class);
        imdbWithAmyAndBob.removePatientFromQueueByIndex(2);
    }

```
###### \src\test\java\seedu\address\model\ImdbTest.java
``` java
        @Override
        public ObservableList<AppointmentEntry> getAppointmentEntryList() {
            return appointments;
        }

        @Override
        public ObservableList<Patient> getUniquePatientQueue() {
            return null;
        }

        @Override
        public ObservableList<Integer> getUniquePatientQueueNo() {
            return visitingQueue;
        }
    }

}
```
###### \src\test\java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void getVisitingQueue_modifyQueue_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getVisitingQueue().remove(0);
    }

    @Test
    public void getPatientAppointmentEntry_modifyAppointment_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getAppointmentEntryList().add(new AppointmentEntry(
                new Appointment("8/5/2018 1030"), "Alice"));
    }

```
###### \src\test\java\seedu\address\model\UniqueAppointmentListTest.java
``` java
package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;

public class UniqueAppointmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UniqueAppointmentList listToTest = new UniqueAppointmentList();

    @Test
    public void execute_addAppointment_addSuccessful() throws Exception {
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        Appointment appointment = new Appointment("3/4/2018 1130");
        listToTest.add(appointment);
        anotherList.add(appointment);
        assertEquals(anotherList, listToTest);
    }

    @Test
    public void execute_removeAppointment_removeSuccessful() throws Exception {
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        Appointment appointment = new Appointment("3/4/2018 1130");
        listToTest.add(appointment);
        anotherList.add(appointment);
        listToTest.remove(appointment);
        anotherList.remove(appointment);
        assertEquals(listToTest, anotherList);
    }

    @Test
    public void execute_duplicateAppointment_throwDUplicateException() throws Exception {
        Appointment appointment = new Appointment("3/4/2018 1130");
        listToTest.add(appointment);
        thrown.expect(UniqueAppointmentList.DuplicatedAppointmentException.class);
        listToTest.add(appointment);
    }

    @Test
    public void execute_setAppointmentList_setSucessful() {
        Set<Appointment> appointments = new HashSet<>(Arrays.asList(new Appointment("3/4/2018 1130")));
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        listToTest.setAppointment(appointments);
        anotherList.setAppointment(appointments);
        assertEquals(listToTest, anotherList);
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAppointmentList uniqueAppointmentList = new UniqueAppointmentList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAppointmentList.asObservableList().remove(0);
    }
}
```
###### \src\test\java\seedu\address\model\UniquePatientVisitingQueueTest.java
``` java
package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

public class UniquePatientVisitingQueueTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UniquePatientVisitingQueue queueToTest = new UniquePatientVisitingQueue();

    @Test
    public void execute_addPatient_addSuccessful() throws Exception {
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(1);
        queueToTest.add(1);
        assertEquals(anotherQueue, queueToTest);
    }

    @Test
    public void execute_removePatient_removeSuccessful() throws Exception {
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(1);
        queueToTest.add(1);
        queueToTest.removePatient();
        anotherQueue.removePatient();
        assertEquals(anotherQueue, queueToTest);
    }

    @Test
    public void execute_removePatientByIndex_removeSuccessful() throws Exception {
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(2);
        anotherQueue.add(4);
        anotherQueue.add(1);
        queueToTest.add(2);
        queueToTest.add(4);
        queueToTest.add(1);
        anotherQueue.removePatient(4);
        queueToTest.removePatient(4);
        assertEquals(anotherQueue, queueToTest);
    }

    @Test
    public void execute_removeByIndexPatientNotInTheQueue_throwsPatientNotFoundException() throws Exception {
        queueToTest.add(4);
        queueToTest.add(2);
        thrown.expect(PatientNotFoundException.class);
        queueToTest.removePatient(3);
    }

    @Test
    public void execute_duplicatePatient_throwsDuplicateException() throws Exception {
        queueToTest.add(1);

        thrown.expect(DuplicatePatientException.class);

        queueToTest.add(1);
    }

    @Test
    public void execute_removeEmptyQueue_throwsPatientNotFoundException() throws Exception {
        thrown.expect(PatientNotFoundException.class);
        queueToTest.removePatient();
    }

    @Test
    public void execute_removeByIndexEmptyQueue_throwsPatientNotFoundException() throws Exception {
        thrown.expect(PatientNotFoundException.class);
        queueToTest.removePatient(3);
    }

    @Test
    public void execute_setVisitingQueue_setSuccessful() {
        Set<Integer> queueNo = new HashSet<>(Arrays.asList(3, 1, 2));
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        queueToTest.setVisitingQueue(queueNo);
        anotherQueue.setVisitingQueue(queueNo);
        assertEquals(queueToTest, anotherQueue);
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePatientVisitingQueue uniquePatientQueue = new UniquePatientVisitingQueue();
        thrown.expect(UnsupportedOperationException.class);
        uniquePatientQueue.asObservableList().remove(0);
    }
}
```
