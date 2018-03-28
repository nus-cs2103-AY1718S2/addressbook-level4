package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.RatingSortCommand.MESSAGE_RATING_SORT_SUCCESS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class RatingSortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        RatingSortCommand command = prepareCommand(RatingSortCommand.SortOrder.DESC);
        String expectedMessage = String.format(MESSAGE_RATING_SORT_SUCCESS, "descending");

        assertCommandSuccess(command, expectedMessage, Arrays.asList(
                BENSON, ALICE, GEORGE, FIONA, ELLE, DANIEL, CARL));
    }

    @Test
    public void isValidSortOrder() {
        assertTrue(RatingSortCommand.isValidSortOrder("asc"));
        assertTrue(RatingSortCommand.isValidSortOrder("desc"));

        assertFalse(RatingSortCommand.isValidSortOrder(""));
        assertFalse(RatingSortCommand.isValidSortOrder("ascc"));

    }

    @Test
    public void equals() {
        final RatingSortCommand standardCommand = prepareCommand(RatingSortCommand.SortOrder.DESC);
        // same values -> returns true
        RatingSortCommand commandWithSameValues = prepareCommand(RatingSortCommand.SortOrder.DESC);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different sort order -> returns false
        assertFalse(standardCommand.equals(prepareCommand(RatingSortCommand.SortOrder.ASC)));
    }

    /**
     * Returns a {@code RatingSortCommand}.
     */
    private RatingSortCommand prepareCommand(RatingSortCommand.SortOrder sortOrder) {
        RatingSortCommand ratingSortCommand = new RatingSortCommand(sortOrder);
        ratingSortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ratingSortCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(RatingSortCommand command, String expectedMessage, List<Person> expectedList)
            throws Exception {
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
    }
}
