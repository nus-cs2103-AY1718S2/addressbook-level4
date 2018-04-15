package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.AliasListHandle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.events.ui.AliasListEvent;
import seedu.address.ui.testutil.GuiTestAssert;

//@@author jingyinno
public class AliasListTest extends GuiUnitTest {
    private AliasList aliasList;
    private AliasListHandle aliasListHandle;

    @Before
    public void setUp() {
        aliasList = new AliasList();
        uiPartRule.setUiPart(aliasList);
        aliasListHandle = new AliasListHandle(getChildNode(aliasList.getRoot(), aliasListHandle.ALIAS_LIST_ID));
    }

    @Test
    public void checkTable() {

        /* Case: add two aliases in the first row and one alias in the second row */
        String[][] expected = new String[][] {{"add1", "alias1"}, {"add2"}};

        // Init alias list and post event
        ArrayList<ArrayList<String>> expectedList = GuiTestAssert.populateExpectedAliases(expected);
        ObservableList<ArrayList<String>> obsExpected = FXCollections.observableArrayList(expectedList);
        AliasListEvent aliasListEventStub = new AliasListEvent(obsExpected);

        aliasList.init(obsExpected);
        aliasList.setStyle();
        postNow(aliasListEventStub);
        guiRobot.pauseForHuman();

        // Assert content of the table
        GuiTestAssert.assertTableContent(aliasListHandle.getTables(), expected);

        /* Case: add one alias in the first row */
        expected = new String[][] {{"add1"}};
        expectedList = GuiTestAssert.populateExpectedAliases(expected);

        // Init alias list and post event
        obsExpected = FXCollections.observableArrayList(expectedList);
        aliasListEventStub = new AliasListEvent(obsExpected);

        aliasList.init(obsExpected);
        postNow(aliasListEventStub);
        guiRobot.pauseForHuman();

        // Assert content of the table
        GuiTestAssert.assertTableContent(aliasListHandle.getTables(), expected);

        /* Case: add three aliases in the first row */
        expected = new String[][] {{"add1", "alias1", "birthday1"}};
        expectedList = GuiTestAssert.populateExpectedAliases(expected);

        // Init alias list and post event
        obsExpected = FXCollections.observableArrayList(expectedList);
        aliasListEventStub = new AliasListEvent(obsExpected);

        aliasList.init(obsExpected);
        postNow(aliasListEventStub);
        guiRobot.pauseForHuman();

        // Assert content of the table
        GuiTestAssert.assertTableContent(aliasListHandle.getTables(), expected);
    }
}
