//@@author Kyholmes-test
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
