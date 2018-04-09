//@@author Kyholmes-test
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
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
        Index firstIndex = ParserUtil.parseIndex("1");
        Index secondIndex = ParserUtil.parseIndex("2");

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
    public void execute_patientExist_addSuccessful() throws Exception {
        AddPatientQueueCommand command = prepareCommand("6");
        CommandResult commandResult = command.execute();
        assertEquals(String.format(AddPatientQueueCommand.MESSAGE_SUCCESS, TypicalPatients.FIONA.getName()),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() throws Exception {
        prepareForDuplicatePatient();
        AddPatientQueueCommand duplicateCommand = prepareCommand("6");
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddPatientQueueCommand.MESSAGE_DUPLICATE_PERSON);
        duplicateCommand.execute();
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
        AddPatientQueueCommand duplicateCommand = prepareCommand("6");
        duplicateCommand.execute();
    }
}
