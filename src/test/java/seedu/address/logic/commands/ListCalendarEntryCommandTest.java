package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCalendarEntryCommand.
 */
//@@author SuxianAlicia
public class ListCalendarEntryCommandTest {
    private Model model;
    private Model expectedModel;
    private ListCalendarEntryCommand listCalendarEntryCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalCalendarManagerWithEntries(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

        listCalendarEntryCommand = new ListCalendarEntryCommand();
        listCalendarEntryCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCalendarEntryCommand, model, ListCalendarEntryCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
