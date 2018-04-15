package seedu.address.model;

import static org.junit.Assert.assertEquals;

import static seedu.address.testutil.TypicalAliases.ADD;
import static seedu.address.testutil.TypicalAliases.EDIT;
import static seedu.address.testutil.TypicalAliases.MAP_1;
import static seedu.address.testutil.TypicalAliases.MAP_2;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.UniqueAliasList;
import seedu.address.model.alias.exceptions.AliasNotFoundException;
import seedu.address.model.alias.exceptions.DuplicateAliasException;

//@@author jingyinno
public class UniqueAliasListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UniqueAliasList uniqueAliasList;

    @Before
    public void setUp() {
        uniqueAliasList = new UniqueAliasList();
    }

    @After
    public void clean() throws AliasNotFoundException {
        clearAliasList();
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueAliasList = new UniqueAliasList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAliasList.asObservableList().remove(0);
    }

    @Test
    public void addAlias_validAlias_success() throws DuplicateAliasException {
        Alias validAlias = ADD;
        uniqueAliasList.add(validAlias);
        assertEquals(Arrays.asList(validAlias), uniqueAliasList.getAliasObservableList());
    }

    @Test
    public void addAlias_validAlias_throwsDuplicateAliasException() throws DuplicateAliasException {
        Alias validAlias = ADD;
        uniqueAliasList.add(validAlias);
        thrown.expect(DuplicateAliasException.class);
        uniqueAliasList.add(validAlias);
    }

    @Test
    public void removeAlias_validAlias_success() throws DuplicateAliasException, AliasNotFoundException {
        Alias validAlias = ADD;
        uniqueAliasList.add(validAlias);
        uniqueAliasList.remove(validAlias.getAlias());

        UniqueAliasList expectedList = new UniqueAliasList();
        assertEquals(uniqueAliasList.getAliasObservableList(), expectedList.asObservableList());
    }

    @Test
    public void removeAlias_invalidAlias_failure() throws AliasNotFoundException {
        Alias validAlias = ADD;
        thrown.expect(AliasNotFoundException.class);
        uniqueAliasList.remove(validAlias.getAlias());
    }

    @Test
    public void getAliasCommand_validAlias_success() throws DuplicateAliasException {
        Alias validAlias = ADD;
        uniqueAliasList.add(validAlias);

        String command = uniqueAliasList.getCommandFromAlias(validAlias.getAlias());
        String expected = validAlias.getCommand();
        assertEquals(command, expected);
    }

    @Test
    public void importAlias_validAlias_success() {
        Alias validAlias = ADD;
        uniqueAliasList.importAlias(validAlias);
        assertEquals(Arrays.asList(validAlias), uniqueAliasList.getAliasObservableList());
    }

    @Test
    public void extractAliasMapping_noAliasAdded_success() {
        ArrayList<String> expectedList = new ArrayList<String>();
        assertEquals(expectedList, uniqueAliasList.extractAliasMapping());
    }

    @Test
    public void extractAliasMapping_validAliasAdded_success() throws DuplicateAliasException {
        uniqueAliasList.add(ADD);

        ArrayList<ArrayList<String>> expectedList = generateExpectedList(new Alias[][]{{ADD}});
        assertEquals(expectedList, uniqueAliasList.extractAliasMapping());
    }

    @Test
    public void extractAliasMapping_differentCommandAliases_success() throws DuplicateAliasException,
            AliasNotFoundException {
        uniqueAliasList.add(ADD);
        uniqueAliasList.add(EDIT);

        ArrayList<ArrayList<String>> expectedList = generateExpectedList(new Alias[][]{{ADD, EDIT}});
        assertEquals(expectedList, uniqueAliasList.extractAliasMapping());
    }

    @Test
    public void extractAliasMapping_sameCommandAliases_success() throws DuplicateAliasException {
        uniqueAliasList.add(MAP_1);
        uniqueAliasList.add(MAP_2);

        ArrayList<ArrayList<String>> expectedList = generateExpectedList(new Alias[][]{{MAP_1}, {MAP_2}});
        assertEquals(expectedList, uniqueAliasList.extractAliasMapping());
    }

    @Test
    public void extractAliasMapping_mixedCommandAliases_success() throws DuplicateAliasException {
        uniqueAliasList.add(ADD);
        uniqueAliasList.add(MAP_1);
        uniqueAliasList.add(MAP_2);

        ArrayList<ArrayList<String>> expectedList = generateExpectedList(new Alias[][]{{ADD, MAP_1}, {MAP_2}});
        assertEquals(expectedList, uniqueAliasList.extractAliasMapping());
    }

    @Test
    public void resetHashMap_success() throws DuplicateAliasException {
        uniqueAliasList.add(ADD);
        assertEquals(Arrays.asList(ADD), uniqueAliasList.getAliasObservableList());
        uniqueAliasList.resetHashmap();
        assertEquals(new ArrayList<Alias>(), uniqueAliasList.getAliasObservableList());
    }

    /**
     * Generates an expected list with the aliases in the testAliasList inserted at their correct positions.
     */
    private ArrayList<ArrayList<String>> generateExpectedList(Alias[][] testAliasList) {
        ArrayList<ArrayList<String>> expectedList = new ArrayList<ArrayList<String>>();
        for (Alias[] row : testAliasList) {
            ArrayList<String> innerList = populateEmptyAlias();
            insertAliasAtPositions(row, innerList);
            expectedList.add(innerList);
        }
        return expectedList;
    }

    /**
     * Inserts the alias command at their respective positions in the array.
     */
    private void insertAliasAtPositions(Alias[] testInnerAliasList, ArrayList<String> innerList) {
        for (Alias alias : testInnerAliasList) {
            innerList.set(AliasCommand.getCommands().indexOf(alias.getCommand()), alias.getAlias());
        }
    }

    /**
     * Removes all aliases in the list.
     *
     * @throws AliasNotFoundException if the Alias to add is not an existing Alias in the list.
     */
    private void clearAliasList() throws AliasNotFoundException {
        for (Alias alias : uniqueAliasList.getAliasObservableList()) {
            uniqueAliasList.remove(alias.getAlias());
        }
    }

    /**
     * Creates an empty arraylist of size number of commands.
     */
    private ArrayList<String> populateEmptyAlias() {
        ArrayList<String> emptyList = new ArrayList<String>();
        int size = AliasCommand.getCommands().size();
        for (int i = 0; i < size; i++) {
            emptyList.add("");
        }
        return emptyList;
    }
}
