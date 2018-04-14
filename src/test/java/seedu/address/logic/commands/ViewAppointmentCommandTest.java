//@@author Kyholmes-test
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sun.glass.ui.View;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

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

//    @Test
//    public void execute_listIsNotFilteredViewAppointmentWithoutIndex_showSameList() {
//        assertCommandSuccess(viewAppointmentCommand, model, ViewAppointmentCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void execute_listIsFilteredViewAppointmentWithoutIndex_showsSameList() {
//        showPersonAtIndex(model, INDEX_SECOND_PERSON);
//        showPersonAtIndex(expectedModel, INDEX_SECOND_PERSON);
//        assertCommandSuccess(viewAppointmentCommand, model, ViewAppointmentCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void execute_listIsNotFilteredViewAppointmentWithIndex_showSameList() {
//        show
//    }

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
     * Parses {@code userInput} into a {@code ViewAppointmentCommand}.
     */
    private ViewAppointmentCommand prepareCommand(Index targetIndex) {
        ViewAppointmentCommand command = new ViewAppointmentCommand(targetIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
