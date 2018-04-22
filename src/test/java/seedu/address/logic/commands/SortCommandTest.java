package seedu.address.logic.commands;

//@@author Yoochard

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code LockCommand}.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        SortCommand firstSortCommand = new SortCommand("rate");
        SortCommand secondSortCommand = new SortCommand("name");

        // same object -> returns true
        assertTrue(firstSortCommand.equals(firstSortCommand));

        // same values -> returns true
        SortCommand secondSortCommandcopy = new SortCommand("name");
        assertTrue(secondSortCommand.equals(secondSortCommandcopy));

        // different types -> returns false
        assertFalse(secondSortCommand.equals(1));

        // null -> returns false
        assertFalse(firstSortCommand.equals(null));

        // different value -> returns false
        assertFalse(firstSortCommand.equals(secondSortCommand));
    }

    @Test
    public void sortSuccess() {
        SortCommand testSortCommand = new SortCommand("rate");
        testSortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = SortCommand.MESSAGE_SORT_EMPLOYEE_SUCCESS;
        try {
            CommandResult commandResult = testSortCommand.execute();
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }



}
