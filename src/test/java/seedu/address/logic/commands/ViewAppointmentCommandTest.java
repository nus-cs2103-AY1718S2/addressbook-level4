//@@author Kyholmes-test
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import javax.swing.text.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    public void equals() throws IllegalValueException {
        Index firstIndex = ParserUtil.parseIndex(INDEX_FIRST_PERSON.getOneBased() + "");
        Index secondIndex = ParserUtil.parseIndex(INDEX_SECOND_PERSON.getOneBased() + "");

        ViewAppointmentCommand viewAppointmentFirstCommand = new ViewAppointmentCommand(firstIndex);
        ViewAppointmentCommand viewAppointmentSecondCommand = new ViewAppointmentCommand(secondIndex);

        //same object -> returns true
        assertTrue(viewAppointmentFirstCommand.equals(viewAppointmentFirstCommand));

        //same values -> returns true
        ViewAppointmentCommand viewAppointmentFirstCommandCopy = new ViewAppointmentCommand(firstIndex);
        assertTrue(viewAppointmentFirstCommand.equals(viewAppointmentFirstCommand));

        //null -> returns false
        assertFalse(viewAppointmentFirstCommand.equals(null));

        //different patient -> returns false
        assertFalse(viewAppointmentFirstCommand.equals(viewAppointmentSecondCommand));
    }

//    @Test
//    public void execute_patientNotExist_noPersonFound() throws Exception {
//        ViewAppointmentCommand command = prepareCommand(INDEX_THIRD_PERSON);
//        thrown.expect(CommandException.class);
//        thrown.expectMessage(ViewAppointmentCommand.MESSAGE_PERSON_NOT_FOUND);
//
//        command.execute();
//    }

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
     * Executes a {@code ViewAppointmentCommand} with index as parameter and checks that
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
     * Parses {@code userInput} into a {@code ViewAppointmentCommand}.
     */
    private ViewAppointmentCommand prepareCommand(Index targetIndex) {
        ViewAppointmentCommand command = new ViewAppointmentCommand(targetIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
