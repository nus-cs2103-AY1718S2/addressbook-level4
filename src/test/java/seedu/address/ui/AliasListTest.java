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
        ArrayList<ArrayList<String>> expected = populateExpectedAliases(new String[][] {{"add1", "alias1"}, {"add2"}});

        // Init alias list and post event
        ObservableList<ArrayList<String>> obsExpected = FXCollections.observableArrayList(expected);
        aliasListEventStub = new AliasListEvent(obsExpected);

        aliasList.init(obsExpected);
        postNow(aliasListEventStub);
        guiRobot.pauseForHuman();

        // Assert content of the table
        assertTableContent(aliasListHandle.getTables(), expected);

        // Populate expectedAliases
        expected = populateExpectedAliases(new String[][] {{"add1"}});

        // Init alias list and post event
        obsExpected = FXCollections.observableArrayList(expected);
        aliasListEventStub = new AliasListEvent(obsExpected);

        aliasList.init(obsExpected);
        postNow(aliasListEventStub);
        guiRobot.pauseForHuman();

        // Assert content of the table
        assertTableContent(aliasListHandle.getTables(), expected);
    }

    private ArrayList<ArrayList<String>> populateExpectedAliases(String[][] expected) {
        ArrayList<ArrayList<String>> expectedList = new ArrayList<>();
        for (String[] inner : expected) {
            ArrayList<String> innerList = new ArrayList<>();
            // Add expected alias for command
            for (String alias : inner) {
                innerList.add(alias);
            }
            // Generate empty alias ("") for no alias command
            while(innerList.size() < AliasCommand.getCommands().size()) {
                innerList.add("");
            }
            expectedList.add(innerList);
        }
        return expectedList;
    }

    private void assertTableContent(ObservableList<TableColumn> aliasListTable, ArrayList<ArrayList<String>> expected) {
        System.out.println(expected);
        for (int i = 0; i < expected.size(); i ++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                System.out.println("Current Row : " + i + ", Current column " + j);
                TableColumn column = aliasListTable.get(j);

                // Current Row value at column
                System.out.println(column.getCellObservableValue(i).getValue());
                System.out.println("Expected value : |" + expected.get(i).get(j) + "|");
                System.out.println("Table value : |" + column.getCellObservableValue(i).getValue() + "|");
                assertEquals(expected.get(i).get(j), column.getCellObservableValue(i).getValue());
            }
        }

    }
}
