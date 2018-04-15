package seedu.address.ui.testutil;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonDetailsCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.model.alias.Alias;
import seedu.address.model.person.Person;
import seedu.address.ui.PersonCard;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final String LABEL_DEFAULT_STYLE = "label";

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
        expectedCard.getTags().forEach(tag ->
                assertEquals(expectedCard.getTagStyleClasses(tag), actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    //@@author yeggasd
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDetailsDisplaysPerson(Person expectedPerson, PersonDetailsCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
        assertTrue(actualCard.getTimeTable() != null);
    }
    //@@author

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    //@@author yeggasd
    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertTagsEqual(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, PersonCard.getColorStyleFor(tag)),
                        actualCard.getTagStyleClasses(tag)));
    }
    //@@author

    //@@author jingyinno
    /**
     * Asserts that the content in {@code aliasListTable} matches all the string in {@code expected}
     */
    public static void assertTableContent(ObservableList<TableColumn> aliasListTable, String[][] expected) {
        ArrayList<ArrayList<String>> expectedList = populateExpectedAliases(expected);
        for (int i = 0; i < expectedList.size(); i++) {
            for (int j = 0; j < expectedList.get(i).size(); j++) {
                TableColumn column = aliasListTable.get(j);

                // Current Row value at column
                assertEquals(expectedList.get(i).get(j), column.getCellObservableValue(i).getValue());
            }
        }
    }

    /**
     * Asserts that the content in {@code aliasListTable} matches all the string in {@code expected}
     */
    public static void assertTableContent(ObservableList<TableColumn> aliasListTable, Alias[][] expected) {
        ArrayList<ArrayList<String>> expectedList = populateExpectedAliases(expected);
        for (int i = 0; i < expectedList.size(); i++) {
            for (int j = 0; j < expectedList.get(i).size(); j++) {
                TableColumn column = aliasListTable.get(j);

                // Current Row value at column
                assertEquals(expectedList.get(i).get(j), column.getCellObservableValue(i).getValue());
            }
        }
    }

    /**
     * Helper method to populate expectedTable with unused empty Alias
     */
    public static ArrayList<ArrayList<String>> populateExpectedAliases(String[][] expected) {
        String emptyAlias = "";
        ArrayList<ArrayList<String>> expectedList = new ArrayList<>();
        for (String[] inner : expected) {
            ArrayList<String> innerList = new ArrayList<>();
            // Add expected alias for command
            for (String alias : inner) {
                innerList.add(alias);
            }
            // Generate empty alias ("") for no alias command
            while (innerList.size() < AliasCommand.getCommands().size()) {
                innerList.add(emptyAlias);
            }
            expectedList.add(innerList);
        }
        return expectedList;
    }

    /**
     * Helper method to populate expectedTable with unused empty Alias
     */
    public static ArrayList<ArrayList<String>> populateExpectedAliases(Alias[][] expected) {
        String emptyAlias = "";
        ArrayList<ArrayList<String>> expectedList = new ArrayList<>();
        for (Alias[] inner : expected) {
            ArrayList<String> innerList = setExpectedAliases(emptyAlias, inner);
            expectedList.add(innerList);
        }
        return expectedList;
    }

    /**
     * Helper method to set inner expected aliases
     */
    private static ArrayList<String> setExpectedAliases(String emptyAlias, Alias[] inner) {
        ArrayList<String> innerList = createNewInnerList(emptyAlias);

        // reset expected alias for command
        for (Alias alias : inner) {
            int index = AliasCommand.getCommands().indexOf(alias.getCommand());
            innerList.set(index, alias.getAlias());
        }
        return innerList;
    }

    /**
     * Helper method to generate new inner list
     */
    private static ArrayList<String> createNewInnerList(String emptyAlias) {
        // Generate empty alias ("") for inner list
        ArrayList<String> innerList = new ArrayList<>();
        for (int i = 0; i < AliasCommand.getCommands().size(); i++) {
            innerList.add(emptyAlias);
        }
        return innerList;
    }
    //@@author
}
