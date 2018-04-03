package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SORT_SUCCESS;
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

//@@author kexiaowen
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByName_sortSuccessful() throws Exception {
        SortCommand command = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.NAME);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "name", "descending");

        assertCommandSuccess(command, expectedMessage, Arrays.asList(
                GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
    }

    @Test
    public void execute_sortByRating_sortSuccessful() throws Exception {
        SortCommand command = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.RATING);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "rating", "descending");

        assertCommandSuccess(command, expectedMessage, Arrays.asList(
                BENSON, ALICE, GEORGE, FIONA, ELLE, DANIEL, CARL));
    }

    @Test
    public void execute_sortByGradePointAverage_sortSuccessful() throws Exception {
        SortCommand command = prepareCommand(SortCommand.SortOrder.ASC, SortCommand.SortField.GPA);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "gpa", "ascending");

        assertCommandSuccess(command, expectedMessage, Arrays.asList(
                GEORGE, DANIEL, ELLE, BENSON, FIONA, ALICE, CARL));
    }

    @Test
    public void isValidSortOrder() {
        assertTrue(SortCommand.isValidSortOrder("asc"));
        assertTrue(SortCommand.isValidSortOrder("desc"));

        assertFalse(SortCommand.isValidSortOrder(""));
        assertFalse(SortCommand.isValidSortOrder("ascc"));

    }

    @Test
    public void isValidSortField() {
        assertTrue(SortCommand.isValidSortField("gpa"));
        assertTrue(SortCommand.isValidSortField("name"));
        assertTrue(SortCommand.isValidSortField("rating"));

        assertFalse(SortCommand.isValidSortField(""));
        assertFalse(SortCommand.isValidSortField("gpaaaa"));
        assertFalse(SortCommand.isValidSortField("ratings"));
    }

    @Test
    public void equals() {
        final SortCommand standardCommand = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.GPA);
        // same values -> returns true
        SortCommand commandWithSameValues = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.GPA);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different sort order -> returns false
        assertFalse(standardCommand.equals(prepareCommand(SortCommand.SortOrder.ASC, SortCommand.SortField.GPA)));

        // different sort field -> returns false
        assertFalse(standardCommand.equals(prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.NAME)));
    }

    /**
     * Returns a {@code SortCommand}.
     */
    private SortCommand prepareCommand(SortCommand.SortOrder sortOrder, SortCommand.SortField sortField) {
        SortCommand sortCommand = new SortCommand(sortOrder, sortField);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SortCommand command, String expectedMessage, List<Person> expectedList)
            throws Exception {
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
    }
}
