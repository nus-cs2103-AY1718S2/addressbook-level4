package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
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
}
