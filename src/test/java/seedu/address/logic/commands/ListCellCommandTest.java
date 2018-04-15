//@@author sarahgoh97
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ListCellCommand.MESSAGE_LISTCELL_SUCCESS;
import static seedu.address.logic.commands.ListCellCommand.MESSAGE_NON_EXISTENT_CELL;
import static seedu.address.testutil.TypicalCells.FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.FULL_CELL;
import static seedu.address.testutil.TypicalCells.INVALID_FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.INVALID_SECOND_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.LAST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AddressContainsCellPredicate;
import seedu.address.model.person.Person;

public class ListCellCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AddressContainsCellPredicate firstPredicate = new AddressContainsCellPredicate(FIRST_CELL_ADDRESS);
        AddressContainsCellPredicate secondPredicate = new AddressContainsCellPredicate(LAST_CELL_ADDRESS);

        ListCellCommand lcFirstCommand = new ListCellCommand(firstPredicate, FIRST_CELL_ADDRESS);
        ListCellCommand lcSecondCommand = new ListCellCommand(secondPredicate, LAST_CELL_ADDRESS);

        // same object -> returns true
        assertTrue(lcFirstCommand.equals(lcFirstCommand));

        // same values -> returns true
        ListCellCommand lcFirstCommandCopy = new ListCellCommand(firstPredicate, FIRST_CELL_ADDRESS);
        assertTrue(lcFirstCommand.equals(lcFirstCommandCopy));

        // different types -> returns false
        assertFalse(lcFirstCommand.equals(1));

        // null -> returns false
        assertFalse(lcFirstCommand.equals(null));

        // different cell address -> returns false
        assertFalse(lcFirstCommand.equals(lcSecondCommand));
    }

    @Test
    public void execute_validEmptyCellAddress_success() throws Exception {
        ListCellCommand listCellCommand = prepareCommand(FIRST_CELL_ADDRESS);
        String expectedMessage = String.format(MESSAGE_LISTCELL_SUCCESS, FIRST_CELL_ADDRESS);
        assertCommandSuccess(listCellCommand, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_validNonEmptyCellAddress_success() throws Exception {
        ListCellCommand listCellCommand = prepareCommand(FULL_CELL.getCellAddress());
        String expectedMessage = String.format(MESSAGE_LISTCELL_SUCCESS, FULL_CELL.getCellAddress());
        assertCommandSuccess(listCellCommand, expectedMessage, Arrays.asList(ELLE, GEORGE));
    }

    @Test
    public void execute_invalidFirstDigitCellAddress_failure() {
        ListCellCommand listCellCommand = prepareCommand(INVALID_FIRST_CELL_ADDRESS);
        assertCommandFailure(listCellCommand, model, String.format(MESSAGE_NON_EXISTENT_CELL,
                INVALID_FIRST_CELL_ADDRESS));
    }

    @Test
    public void execute_invalidSecondDigitCellAddress_failure() {
        ListCellCommand listCellCommand = prepareCommand(INVALID_SECOND_CELL_ADDRESS);
        assertCommandFailure(listCellCommand, model, String.format(MESSAGE_NON_EXISTENT_CELL,
                INVALID_SECOND_CELL_ADDRESS));
    }

    private ListCellCommand prepareCommand(String cellAddress) {
        ListCellCommand command = new ListCellCommand(new AddressContainsCellPredicate(cellAddress), cellAddress);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ListCellCommand command, String expectedMessage,
                                      List<Person> expectedList) throws Exception {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
