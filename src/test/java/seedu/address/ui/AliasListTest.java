package seedu.address.ui;

import guitests.guihandles.AliasListHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.events.ui.AliasListEvent;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.model.alias.Alias;
import seedu.address.ui.testutil.GuiTestAssert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

//@@author jingyinno
public class AliasListTest extends GuiUnitTest {
    private List<Alias> aliasListStub;
    private ObservableList<ArrayList<String>> aliasObservableListStub;
    private AliasList aliasList;
    private AliasListEvent aliasListEventStub;
    private AliasListHandle aliasListHandle;

    @Before
    public void setup() {
        aliasList = new AliasList();
        uiPartRule.setUiPart(aliasList);
        aliasListHandle = new AliasListHandle(getChildNode(aliasList.getRoot(), aliasListHandle.ALIAS_LIST_ID));
    }

    @Test
    public void checkTable() {
        // Populate expectedAliases
        String[][] expected = new String[][] {{"add1", "alias1"}, {"add2"}};

        // Init alias list and post event
        ArrayList<ArrayList<String>> expectedList = GuiTestAssert.populateExpectedAliases(expected);
        ObservableList<ArrayList<String>> obsExpected = FXCollections.observableArrayList(expectedList);
        aliasListEventStub = new AliasListEvent(obsExpected);

        aliasList.init(obsExpected);
        postNow(aliasListEventStub);
        guiRobot.pauseForHuman();

        // Assert content of the table
        GuiTestAssert.assertTableContent(aliasListHandle.getTables(), expected);

        // Populate expectedAliases
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
    }
}
