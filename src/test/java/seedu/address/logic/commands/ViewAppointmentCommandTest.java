//@@author Kyholmes-test
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class ViewAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private ViewAppointmentCommand viewAppointmentCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getImdb(), new UserPrefs());
        viewAppointmentCommand = new ViewAppointmentCommand();
        viewAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showSameList() {
        assertCommandSuccess(viewAppointmentCommand, model, ViewAppointmentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        ViewAppointmentCommand viewAppointmentFirstCommand = new ViewAppointmentCommand(firstPredicate);
        ViewAppointmentCommand viewAppointmentSecondCommand = new ViewAppointmentCommand(secondPredicate);

        //same object -> returns true
        assertTrue(viewAppointmentFirstCommand.equals(viewAppointmentFirstCommand));

        //same values -> returns true
        ViewAppointmentCommand viewAppointmentFirstCommandCopy = new ViewAppointmentCommand(firstPredicate);
        assertTrue(viewAppointmentFirstCommand.equals(viewAppointmentFirstCommand));

        //null -> returns false
        assertFalse(viewAppointmentFirstCommand.equals(null));

        //different patient -> returns false
        assertFalse(viewAppointmentFirstCommand.equals(viewAppointmentSecondCommand));
    }

    @Test
    public void execute_patientNotExist_noPersonFound() throws Exception {
        ViewAppointmentCommand command = prepareCommand("a");
        thrown.expect(CommandException.class);
        thrown.expectMessage(ViewAppointmentCommand.MESSAGE_PERSON_NOT_FOUND);

        command.execute();
    }

    /**
     * Parses {@code userInput} into a {@code ViewAppointmentCommand}.
     */
    private ViewAppointmentCommand prepareCommand(String userInput) {
        ViewAppointmentCommand command = new ViewAppointmentCommand(new NameContainsKeywordsPredicate(
                Arrays.asList(userInput.split("\\s"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
