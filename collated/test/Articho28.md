# Articho28
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(BrowserPanel.ATM_SEARCH_PAGE_URL);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(BrowserPanel.ADDRESS_SEARCH_PAGE_URL
                +  BOB.getAddress().value.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
```
###### /java/seedu/address/logic/commands/SearchTagCommandTest.java
``` java

package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

public class SearchTagCommandTest {

    private Model model;
    private Model expectedModelSingleInput;
    private Model expectedModelMultipleInput;
    private SearchTagCommand searchTagCommandSingleTagInput;
    private SearchTagCommand searchTagCommandMultipleTagsInput;
    private Set<Tag> multipleTagsAsInput;
    private Set<Tag> singleTagAsInput;



    @Before
    public void setUp() {

        singleTagAsInput = new HashSet<>();
        singleTagAsInput.add(new Tag("friends"));

        multipleTagsAsInput = new HashSet<>();
        multipleTagsAsInput.add(new Tag("friends"));
        multipleTagsAsInput.add(new Tag("classmates"));
        multipleTagsAsInput.add(new Tag("colleagues"));

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModelSingleInput = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelSingleInput.updateFilteredPersonList(SearchTagCommand.personHasTags(singleTagAsInput));
        expectedModelMultipleInput = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelMultipleInput.updateFilteredPersonList(SearchTagCommand.personHasTags(multipleTagsAsInput));

        searchTagCommandSingleTagInput = new SearchTagCommand(singleTagAsInput);
        searchTagCommandMultipleTagsInput = new SearchTagCommand((multipleTagsAsInput));
        searchTagCommandSingleTagInput.setData(model, new CommandHistory(), new UndoRedoStack());
        searchTagCommandMultipleTagsInput.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void showsAllContactWithFriendsTag() {
        int result = expectedModelSingleInput.getFilteredPersonList().size();
        assertCommandSuccess(searchTagCommandSingleTagInput, model, SearchTagCommand.MESSAGE_SUCCESS
                + "\n"
                + SearchTagCommand.formatTagsFeedback(singleTagAsInput)
                + "\n"
                + Command.getMessageForPersonListShownSummary(result), expectedModelSingleInput);
    }

    @Test
    public void showsNoContactsWithMultipleTags() {
        int result = expectedModelMultipleInput.getFilteredPersonList().size();
        assertCommandSuccess(searchTagCommandMultipleTagsInput, model, SearchTagCommand.MESSAGE_FAILURE
                + "\n"
                + SearchTagCommand.formatTagsFeedback(multipleTagsAsInput), expectedModelMultipleInput);
    }

}
```
###### /java/seedu/address/logic/commands/MapCommandTest.java
``` java

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
```
###### /java/seedu/address/logic/commands/BalanceCommandTest.java
``` java

package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
public class BalanceCommandTest {

    private Model model;
    private Model expectedModel;
    private BalanceCommand balanceCommand;
    private double balance;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        balanceCommand = new BalanceCommand();
        balanceCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        balance = balanceCommand.getBalanceFromTravelBanker();
    }

    @Test
    public void executes_getsOverallBalanceSuccess() {
        assertCommandSuccess(balanceCommand, model,  BalanceCommand.MESSAGE_SUCCESS
                + "\n" + "Your balance is "
                + BalanceCommand.getFormatTwoDecimalPlaces().format(balance)
                + ".", expectedModel);
    }




}
```
