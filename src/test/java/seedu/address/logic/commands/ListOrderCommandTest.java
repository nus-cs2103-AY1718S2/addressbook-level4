package seedu.address.logic.commands;
//@@author SuxianAlicia
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.DisplayOrderListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListOrderCommand.
 */
public class ListOrderCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

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
    public void execute() {
        assertCommandSuccess(listOrderCommand, model, ListOrderCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplayOrderListEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
