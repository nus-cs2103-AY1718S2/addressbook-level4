package seedu.address.model.alias;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALIAS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_CLEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_CLEAR_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_LIST_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_MAP1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_NUMBER;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.testutil.AliasBuilder;
import seedu.address.testutil.Assert;

//@@author jingyinno
public class AliasTest {

    private Alias clear;
    private Alias add;
    private Alias addClone;
    private Alias map;

    @Before
    public void setUp() {
        AliasBuilder builder = new AliasBuilder();
        clear = builder.withCommand(ClearCommand.COMMAND_WORD).withAlias(VALID_ALIAS_CLEAR).build();
        add = builder.withCommand(AddCommand.COMMAND_WORD).withAlias(VALID_ALIAS_ADD).build();
        addClone = builder.withCommand(AddCommand.COMMAND_WORD).withAlias(VALID_ALIAS_ADD).build();
        map = builder.withCommand(MapCommand.COMMAND_WORD).withAlias(VALID_ALIAS_MAP1).build();
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Alias(null, null));
    }

    @Test
    public void constructor_invalidAlias_throwsIllegalArgumentException() {
        String invalidAlias = INVALID_ALIAS;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Alias(VALID_ALIAS_LIST_COMMAND, invalidAlias));
    }

    @Test
    public void isValidAliasName() {
        // null alias
        Assert.assertThrows(NullPointerException.class, () -> new Alias(null, null));

        // invalid alias
        assertFalse(Alias.isValidAliasParameter("")); // empty string
        assertFalse(Alias.isValidAliasParameter(" ")); // spaces only
        assertFalse(Alias.isValidAliasParameter(INVALID_ALIAS)); // only non-alphanumeric characters

        // valid alias
        assertTrue(Alias.isValidAliasParameter(VALID_ALIAS_ADD));
        assertTrue(Alias.isValidAliasParameter(VALID_ALIAS_NUMBER));
    }

    @Test
    public void equals() {
        assertEquals(clear, new Alias(VALID_ALIAS_CLEAR_COMMAND, VALID_ALIAS_CLEAR));
    }

    @Test
    public void getCommand_validAliases_success() {
        assertEquals(add.getCommand(), AddCommand.COMMAND_WORD);
        assertEquals(map.getCommand(), MapCommand.COMMAND_WORD);
    }

    @Test
    public void getAlias_validAliases_success() {
        assertEquals(add.getAlias(), VALID_ALIAS_ADD);
        assertEquals(map.getAlias(), VALID_ALIAS_MAP1);
    }

    @Test
    public void hashCode_equals() {
        assertEquals(add.hashCode(), addClone.hashCode());
        assertNotEquals(add.hashCode(), clear.hashCode());
    }

    @Test
    public void toString_equals() {
        String expectedString = String.format("[%s]", add.getAlias());
        assertEquals(add.toString(), expectedString);
    }
}
