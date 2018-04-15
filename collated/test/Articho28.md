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
###### /java/seedu/address/logic/parser/SearchTagCommandParserTest.java
``` java

package seedu.address.logic.parser;

import static org.testng.Assert.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.SearchTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Tests that the SeachTagCommandParser manipulates the input properly.
 */
public class SearchTagCommandParserTest {

    public static final String TAG_NAME_1 = "friends";
    public static final String TAG_NAME_2 = "colleagues";

    private SearchTagCommandParser searchTagCommandParser = new SearchTagCommandParser();

    /**
     * Test for single input.
     * @throws ParseException
     */
    @Test
    public void validArgsSingleTagInputIsEqual() throws ParseException {
        Set<Tag> tagsToFind = new HashSet<>();
        tagsToFind.add(new Tag(TAG_NAME_1));
        SearchTagCommand searchTagCommand = searchTagCommandParser.parse( " " + PREFIX_TAG + TAG_NAME_1);
        assertEquals(searchTagCommand.getTagsToFind(), tagsToFind);
    }

    /**
     * Tests for multiple input.
     * @throws ParseException
     */
    @Test
    public void validArgsMultipleTagsAreEqual() throws ParseException {
        Set<Tag> multipleTagsToFind = new HashSet<>();
        multipleTagsToFind.add(new Tag(TAG_NAME_1));
        multipleTagsToFind.add(new Tag(TAG_NAME_2));
        SearchTagCommand searchTagCommand = searchTagCommandParser.parse(" "
                + PREFIX_TAG + TAG_NAME_1
                + " "
                + PREFIX_TAG + TAG_NAME_2);
        assertEquals(searchTagCommand.getTagsToFind(), multipleTagsToFind);

    }
    /**
     * Throws exception when no tags are provided.
     */
    @Test
    public void emptyTagsArgs() {
        assertParseFailure(searchTagCommandParser, "a",
                String.format(SearchTagCommandParser.MESSAGE_INVALID_COMMAND_NO_TAGS, SearchTagCommand.MESSAGE_USAGE));
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
###### /java/seedu/address/logic/commands/MinCommandTest.java
``` java

package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.testng.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.money.Money;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Tests the output of the MinCommand
 */

public class MinCommandTest {

    private Model expectedModelNoResultsFound;
    private Model expectedModelResultsFound;
    private int indexOfLowestBalance;
    private MinCommand minCommandNoResultsFound;
    private MinCommand minCommandResultsFound;
    private Model model;
    private Person actualPersonSelected;
    private Person expectedPersonSelected;


    @Before
    public void setUp() {
        expectedModelNoResultsFound = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModelResultsFound = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        indexOfLowestBalance = getIndexOfPersonWithLowestBalance(model.getAddressBook());

        minCommandNoResultsFound = new MinCommand();
        minCommandNoResultsFound.setData(model, new CommandHistory(), new UndoRedoStack());
        minCommandResultsFound = new MinCommand();
        minCommandResultsFound.setData(model, new CommandHistory(), new UndoRedoStack());

    }

    /**
     * Checks that it does nothing if all balances are positive.
     */
    @Test
    public void executeFindsNoResults() {
        if (indexOfLowestBalance == -1) {
            assertCommandSuccess(minCommandNoResultsFound, model,
                    MinCommand.MESSAGE_SUCCESS_NO_RESULT, expectedModelNoResultsFound);
        }
    }

    /**
     * Finds the same person and checks if their balance is the same.
     *
     * @throws DuplicatePersonException
     * @throws PersonNotFoundException
     */
    @Test
    public void executeFindsSamePerson() throws DuplicatePersonException, PersonNotFoundException {
        insertNegativeBalance(model, DANIEL, new Money("-10"));
        insertNegativeBalance(expectedModelResultsFound, DANIEL, new Money("-10"));
        indexOfLowestBalance = getIndexOfPersonWithLowestBalance(expectedModelResultsFound.getAddressBook());
        expectedPersonSelected = expectedModelResultsFound.getFilteredPersonList().get(indexOfLowestBalance);
        minCommandResultsFound.execute();
        actualPersonSelected = model.getFilteredPersonList().get(indexOfLowestBalance);
        assertExecutionSuccess(expectedPersonSelected, actualPersonSelected);
        assertTrue(actualPersonSelected.getMoney().value == expectedPersonSelected.getMoney().value);
    }

    //@@ author
    @Test
    public void executes_updatedBalanceAccordingly() throws Exception {
        model.getFilteredPersonList().get(0).setMoney(new Money("-100"));

        MinCommand minCommand = new MinCommand();
        minCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult commandResult = minCommand.execute();

        assertEquals(commandResult.feedbackToUser,
                MinCommand.MESSAGE_SUCCESS_FOUND + model.getFilteredPersonList().get(0).getName());
    }

```
###### /java/seedu/address/logic/commands/MinCommandTest.java
``` java
    /**
     * Gets the index number of the person with the lowest balance. Returns -1 if all balances are non-negative.
     *
     * @param addressBook
     * @return
     */
    public int getIndexOfPersonWithLowestBalance(ReadOnlyAddressBook addressBook) {
        List<Person> persons = addressBook.getPersonList();
        int index = 0;
        double lowestBalance = 0.0;
        for (Person p : persons) {
            double currentBalance = p.getMoney().balance;
            if (currentBalance < lowestBalance) {
                index = persons.indexOf(p);
                lowestBalance = currentBalance;
            }
        }
        if (lowestBalance == 0.0) {
            index = -1;
            return index;
        }
        return index;
    }

    /**
     * Inserts a person with a negative balance instead
     *
     * @param modelToChange
     * @param toReplace
     * @param money
     * @throws PersonNotFoundException
     * @throws DuplicatePersonException
     */

    public void insertNegativeBalance(Model modelToChange, Person toReplace, Money money)
            throws PersonNotFoundException, DuplicatePersonException {
        Person edited = new Person(toReplace.getName(),
                toReplace.getPhone(),
                toReplace.getEmail(),
                toReplace.getAddress(),
                money,
                toReplace.getTags(),
                toReplace.getItems());
        modelToChange.updatePerson(toReplace, edited);
    }

    /**
     * Checks if selected people are the same in both models.
     */
    static void assertExecutionSuccess(Person expectedSelection, Person actualSelection) {
        assertTrue(expectedSelection.equals(actualSelection));
    }
}
```
