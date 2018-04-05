package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListOrderCommand.
 */
//@@author SuxianAlicia
public class ListOrderCommandTest {
    private Model model;
    private Model expectedModel;
    private ListOrderCommand listOrderCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithOrders(),  new CalendarManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

        listOrderCommand = new ListOrderCommand();
        listOrderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listOrderCommand, model, ListOrderCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
