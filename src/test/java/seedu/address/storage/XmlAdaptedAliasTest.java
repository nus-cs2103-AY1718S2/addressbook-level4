package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalAliases.ADD;
import static seedu.address.testutil.TypicalAliases.DELETE;
import static seedu.address.testutil.TypicalAliases.UNKNOWN;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.Alias;
import seedu.address.testutil.AliasBuilder;
import seedu.address.testutil.Assert;

public class XmlAdaptedAliasTest {
    private static final String ILLEGAL_COMMAND_WORD = "add%";
    private static final String ILLEGAL_ALIAS_WORD = "alias$";

    private static final String VALID_COMMAND = ADD.getCommand();
    private static final String VALID_ALIAS = ADD.getAlias();

    @Test
    public void toModelType_validAliasDetails_returnsAlias() throws Exception {
        XmlAdaptedAlias alias = new XmlAdaptedAlias(ADD);
        assertEquals(ADD, alias.toModelType());
    }

    @Test
    public void toModelType_invalidCommand_returnsAlias() throws Exception {
        //parser should have already handled invalid command values --> invalid command will pass here
        XmlAdaptedAlias alias = new XmlAdaptedAlias(UNKNOWN);
        assertEquals(UNKNOWN, alias.toModelType());
    }

    @Test
    public void toModelType_invalidCommand_throwsIllegalValueException() {
        XmlAdaptedAlias alias = new XmlAdaptedAlias(ILLEGAL_COMMAND_WORD, VALID_ALIAS);
        String expectedMessage = Alias.MESSAGE_ALIAS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, alias::toModelType);
    }

    @Test
    public void toModelType_invalidAlias_throwsIllegalValueException() {
        XmlAdaptedAlias alias = new XmlAdaptedAlias(VALID_COMMAND, ILLEGAL_ALIAS_WORD);
        String expectedMessage = Alias.MESSAGE_ALIAS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, alias::toModelType);
    }

    @Test
    public void toModelType_nullCommand_throwsIllegalValueException() {
        XmlAdaptedAlias alias = new XmlAdaptedAlias(null, VALID_ALIAS);
        String expectedMessage = Alias.MESSAGE_ALIAS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, alias::toModelType);
    }

    @Test
    public void toModelType_nullAlias_throwsIllegalValueException() {
        XmlAdaptedAlias alias = new XmlAdaptedAlias(VALID_COMMAND, null);
        String expectedMessage = Alias.MESSAGE_ALIAS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, alias::toModelType);
    }

    @Test
    public void equals() throws Exception {
        XmlAdaptedAlias add = new XmlAdaptedAlias(ADD);
        XmlAdaptedAlias addCopy = new XmlAdaptedAlias(ADD);
        XmlAdaptedAlias delete = new XmlAdaptedAlias(DELETE);
        Alias aliasObject = new AliasBuilder().build();

        // same object -> returns true
        assertTrue(add.equals(add));

        // same values -> returns true
        assertTrue(add.equals(addCopy));

        // null -> returns false
        assertFalse(add == null);

        // different values -> returns false
        assertFalse(add.equals(delete));

        // different object -> returns false
        assertFalse(add.equals(aliasObject));
    }
}
