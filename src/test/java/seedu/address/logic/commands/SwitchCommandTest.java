package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.SortedClients.getSortedByGradeAddressBook;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBookNew;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.events.ui.ClientListSwitchEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;
import seedu.address.ui.util.ListPanelController;

//@@author olimhc
public class SwitchCommandTest {
    private static ListPanelController listPanelController;
    private Model model;
    private Model expectedModel;
    private SwitchCommand switchCommand;
    private EventsCollectorRule eventsCollectorRule;

    private final String expectedSwitchToClosedListMessage = String.format(SwitchCommand.MESSAGE_SUCCESS
            + SwitchCommand.MESSAGE_CLOSED_DISPLAY_LIST);
    private final String expectedSwitchToActiveListMessage = String.format(SwitchCommand.MESSAGE_SUCCESS
            + SwitchCommand.MESSAGE_ACTIVE_DISPLAY_LIST);

    @Before
    public void setUp() {
        eventsCollectorRule = new EventsCollectorRule();
        model = new ModelManager(getTypicalAddressBookNew(), new UserPrefs());
        expectedModel =  new ModelManager(getSortedByGradeAddressBook(), new UserPrefs());
        switchCommand = new SwitchCommand();
        switchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listPanelController = ListPanelController.getInstance();
        listPanelController.setDefault();
    }

    /**
     *Ensure display is at active client list after this class test.
     */
    @AfterClass
    public static void tearDownAfterClass() {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            listPanelController.switchDisplay();
        }
    }

    /**
     * Asserts that the list is successfully switched.
     */
    @Test
    public void execute_switch_success() {
        CommandResult commandResult = switchCommand.execute();
        assertEquals(expectedSwitchToClosedListMessage, commandResult.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ClientListSwitchEvent);

        commandResult = switchCommand.execute();
        assertEquals(expectedSwitchToActiveListMessage, commandResult.feedbackToUser);
    }
}
