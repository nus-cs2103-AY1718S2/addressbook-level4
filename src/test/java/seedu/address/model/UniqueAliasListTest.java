package seedu.address.model;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_HELP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_HELP_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.alias.Alias;
import seedu.address.model.alias.UniqueAliasList;
import seedu.address.model.alias.exceptions.AliasNotFoundException;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.testutil.AliasBuilder;

public class UniqueAliasListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAliasList.asObservableList().remove(0);
    }

    @Test
    public void addAlias_duplicateAlias_throwsDuplicateAliasException() throws DuplicateAliasException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        uniqueAliasList.resetHashmap();
        Alias validAlias = new AliasBuilder().build();
        uniqueAliasList.add(validAlias);

        thrown.expect(DuplicateAliasException.class);
        thrown.expectMessage(DuplicateAliasException.MESSAGE);

        uniqueAliasList.add(validAlias);
    }

    @Test
    public void addAlias_validAlias_success() throws DuplicateAliasException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        uniqueAliasList.resetHashmap();
        Alias validAlias = new AliasBuilder().build();
        uniqueAliasList.add(validAlias);
        assertEquals(Arrays.asList(validAlias), uniqueAliasList.getAliasObservableList());
    }

    @Test
    public void removeAlias_validAlias_success() throws DuplicateAliasException, AliasNotFoundException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        uniqueAliasList.resetHashmap();
        Alias validAlias = new AliasBuilder().build();
        uniqueAliasList.add(validAlias);
        uniqueAliasList.remove(validAlias.getAlias());

        UniqueAliasList expectedList = new UniqueAliasList();
        assertEquals(uniqueAliasList.getAliasObservableList(), expectedList.asObservableList());
    }

    @Test
    public void removeAlias_invalidAlias_failure() throws AliasNotFoundException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        Alias validAlias = new AliasBuilder().build();
        thrown.expect(AliasNotFoundException.class);
        uniqueAliasList.remove(validAlias.getAlias());
    }

    @Test
    public void getAliasCommand_validAlias_success() throws DuplicateAliasException, AliasNotFoundException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        uniqueAliasList.resetHashmap();
        Alias validAlias = new AliasBuilder().build();
        uniqueAliasList.add(validAlias);

        String command = uniqueAliasList.getCommandFromAlias(validAlias.getAlias());
        String expected = validAlias.getCommand();
        assertEquals(command, expected);
    }

    @Test
    public void importAlias_validAlias_success() throws DuplicateAliasException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        uniqueAliasList.resetHashmap();
        Alias validAlias = new AliasBuilder().build();
        uniqueAliasList.importAlias(validAlias);
        assertEquals(Arrays.asList(validAlias), uniqueAliasList.getAliasObservableList());
    }

    @Test
    public void setAlias_validAliasSet_success() throws DuplicateAliasException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        uniqueAliasList.resetHashmap();
        HashSet<Alias> toBeSet = new HashSet<Alias>();
        Alias help = new AliasBuilder().withCommand(VALID_ALIAS_HELP_COMMAND).withAlias(VALID_ALIAS_HELP).build();
        toBeSet.add(help);
        uniqueAliasList.add(help);

        uniqueAliasList.setAliases(toBeSet);
        ArrayList<Alias> expectedList = new ArrayList<Alias>(toBeSet);
        assertEquals(expectedList, uniqueAliasList.getAliasObservableList());
    }
}
