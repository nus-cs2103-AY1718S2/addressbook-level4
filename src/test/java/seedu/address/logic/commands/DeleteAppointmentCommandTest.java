//@@author Kyholmes-test
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class DeleteAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws IllegalValueException {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));

        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        Index firstIndex = ParserUtil.parseIndex("1");

        DeleteAppointmentCommand deleteAppointmentFirstCommand =
                new DeleteAppointmentCommand(firstPredicate, firstIndex);

        DeleteAppointmentCommand deleteAppointmentSecondCommand =
                new DeleteAppointmentCommand(secondPredicate, firstIndex);

        //same object -> return true
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommand));

        //same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstCommandCopy =
                new DeleteAppointmentCommand(firstPredicate, firstIndex);
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommandCopy));

        //different types -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(1));

        //null -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(null));

        //different pattern -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() throws Exception {
        DeleteAppointmentCommand command = prepareCommand("  2");
        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteAppointmentCommand.MESSAGE_PERSON_NOT_FOUND);

        command.execute();
    }

    @Test
    public void execute_patientExist_deleteSuccessful() throws Exception {
        DeleteAppointmentCommand command = prepareCommand("fiona 1");
        CommandResult commandResult = command.execute();
        assertEquals(DeleteAppointmentCommand.MESSAGE_DELETE_SUCCESS, commandResult.feedbackToUser);
    }

    private DeleteAppointmentCommand prepareCommand(String userInput) throws ParseException {
        DeleteAppointmentCommand command = new DeleteAppointmentCommandParser().parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
