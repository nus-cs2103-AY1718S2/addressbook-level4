package seedu.address.model.alias;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CommandTestUtil.*;

public class AliasTest {

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
        assertFalse(Alias.isValidAliasName("")); // empty string
        assertFalse(Alias.isValidAliasName(" ")); // spaces only
        assertFalse(Alias.isValidAliasName(INVALID_ALIAS)); // only non-alphanumeric characters

        // valid alias
        assertTrue(Alias.isValidAliasName(VALID_ALIAS_ADD));
        assertTrue(Alias.isValidUnaliasName(VALID_ALIAS_NUMBER));
    }

    @Test
    public void isValidUnaliasName() {
        // null unalias
        Assert.assertThrows(NullPointerException.class, () -> new Alias(null, null));

        // invalid unalias
        assertFalse(Alias.isValidUnaliasName("")); // empty string
        assertFalse(Alias.isValidUnaliasName(" ")); // spaces only
        assertFalse(Alias.isValidUnaliasName(INVALID_ALIAS)); // only non-alphanumeric characters

        // valid unalias
        assertTrue(Alias.isValidUnaliasName(VALID_ALIAS_ADD));
        assertTrue(Alias.isValidUnaliasName(VALID_ALIAS_NUMBER));
    }

    @Test
    public void equals() {
        Alias clear = new Alias(VALID_ALIAS_CLEAR_COMMAND, VALID_ALIAS_CLEAR);
        assertEquals(clear, new Alias(VALID_ALIAS_CLEAR_COMMAND, VALID_ALIAS_CLEAR));
    }
}
