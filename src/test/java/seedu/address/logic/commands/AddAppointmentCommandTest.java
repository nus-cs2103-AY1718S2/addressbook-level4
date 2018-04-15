//@@author Kyholmes-test
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
