package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.CHRISTMAS;
import static seedu.address.testutil.TypicalEvents.CNY;
import static seedu.address.testutil.TypicalEvents.MOVIE;
import static seedu.address.testutil.TypicalEvents.REUNION;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvent;

//@@author x3tsunayh

public class SortEventCommandTest {
    private Model model;
    private Model expectedModel;

    private String firstParameter;
    private String secondParameter;
    private String thirdParameter;

    @Before
    public void setUp() {
        // all possible parameters declared here
        firstParameter = "TITLE";
        secondParameter = "LOCATION";
        thirdParameter = "DATETIME";

        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel =
                new ModelManager(model.getAddressBook(), model.getEventBook(), model.getTaskBook(), new UserPrefs());

    }

    @Test
    public void execute_listIsOrdered_showsEverything() {
        SortEventCommand command = prepareCommand(firstParameter);
        assertCommandSuccess(command, model, SortEventCommand.MESSAGE_SORT_SUCCESS
                + firstParameter, expectedModel);
    }

    @Test
    public void execute_emptyParameter_listNotSorted() throws CommandException {
        SortEventCommand command = prepareCommand("");
        assertSortSuccess(command, SortEventCommand.MESSAGE_SORT_WRONG_PARAMETER,
                Arrays.asList(CHRISTMAS, CNY, MOVIE, REUNION));
    }

    @Test
    public void execute_whitespaceParameter_listNotSorted() throws CommandException {
        SortEventCommand command = prepareCommand(" ");
        assertSortSuccess(command, SortEventCommand.MESSAGE_SORT_WRONG_PARAMETER,
                Arrays.asList(CHRISTMAS, CNY, MOVIE, REUNION));
    }

    @Test
    public void execute_titleParameter_listSorted() throws CommandException {
        SortEventCommand command = prepareCommand(firstParameter);
        assertSortSuccess(command, SortEventCommand.MESSAGE_SORT_SUCCESS + firstParameter,
                Arrays.asList(CHRISTMAS, CNY, MOVIE, REUNION));
    }


    @Test
    public void execute_locationParameter_listSorted() throws CommandException {
        SortEventCommand command = prepareCommand(secondParameter);
        assertSortSuccess(command, SortEventCommand.MESSAGE_SORT_SUCCESS + secondParameter,
                Arrays.asList(CHRISTMAS, CNY, MOVIE, REUNION));
    }

    @Test
    public void execute_datetimeParameter_listSorted() throws CommandException {
        SortEventCommand command = prepareCommand(thirdParameter);
        assertSortSuccess(command, SortEventCommand.MESSAGE_SORT_SUCCESS + thirdParameter,
                // list order is backwards, from latest event to oldest event
                Arrays.asList(REUNION, MOVIE, CNY, CHRISTMAS));
    }

    @Test
    public void equals() {
        final SortEventCommand standardCommand = new SortEventCommand(firstParameter);

        // same values -> returns true
        SortEventCommand commandWithSameValues = new SortEventCommand("TITLE");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different parameter -> returns false
        assertFalse(standardCommand.equals(new SortEventCommand(secondParameter)));
        assertFalse(standardCommand.equals(new SortEventCommand(thirdParameter)));

    }

    /**
     * Generates a new OrderCommand
     */
    private SortEventCommand prepareCommand(String parameter) {
        SortEventCommand sortEventCommand = new SortEventCommand(parameter);
        sortEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortEventCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code expectedList}<br>
     */
    private void assertSortSuccess(SortEventCommand command, String expectedMessage,
                                   List<ReadOnlyEvent> expectedList) throws CommandException {
        CommandResult commandResult = command.executeUndoableCommand();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredEventList());
    }

}
