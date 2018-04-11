//@@author Articho28

package seedu.address.logic.commands;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static junit.framework.TestCase.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.GuiUnitTest;




public class MapCommandTest extends GuiUnitTest {

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;
    private MapCommand mapCommand;
    private Model model;
    private Model expectedModel;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        mapCommand = new MapCommand();
        mapCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void executes_mapCommandRecognized() {
        assertCommandSuccess(mapCommand, model, MapCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test public void executes_displaysMap() throws MalformedURLException {
        CommandResult commandResult = mapCommand.execute();

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(new URL(BrowserPanel.ATM_SEARCH_PAGE_URL), browserPanelHandle.getLoadedUrl());
    }


}
