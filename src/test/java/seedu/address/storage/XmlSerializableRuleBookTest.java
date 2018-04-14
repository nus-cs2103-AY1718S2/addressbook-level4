package seedu.address.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRules.getTypicalRuleBook;

import org.junit.Test;

import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.RuleBook;

public class XmlSerializableRuleBookTest {

    private static final ReadOnlyRuleBook ruleBook = getTypicalRuleBook();

    @Test
    public void equals() {
        XmlSerializableRuleBook firstRuleBook = new XmlSerializableRuleBook(getTypicalRuleBook());
        XmlSerializableRuleBook secondRuleBook = new XmlSerializableRuleBook(new RuleBook());

        // same object -> returns true
        assertTrue(firstRuleBook.equals(firstRuleBook));

        // null -> returns false
        assertFalse(firstRuleBook.equals(null));

        // different types -> returns false
        assertFalse(firstRuleBook.equals(1));

        // different rulebook -> returns false
        assertFalse(firstRuleBook.equals(secondRuleBook));
    }
}
