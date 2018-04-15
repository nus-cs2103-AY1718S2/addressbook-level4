package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;
import static seedu.address.testutil.TypicalCoins.ALIS;
import static seedu.address.testutil.TypicalCoins.BTCZ;
import static seedu.address.testutil.TypicalRules.CAS;
import static seedu.address.testutil.TypicalRules.GEO;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.coin.NameContainsKeywordsPredicate;
import seedu.address.model.rule.exceptions.DuplicateRuleException;
import seedu.address.model.rule.exceptions.RuleNotFoundException;
import seedu.address.testutil.CoinBookBuilder;
import seedu.address.testutil.TypicalRules;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredCoinList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredCoinList().remove(0);
    }

    @Test
    public void getRuleList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getRuleList().remove(0);
    }

    @Test
    public void manageRules() throws Exception {
        Model model = new ModelManager();
        model.addRule(TypicalRules.ALIS);

        model.deleteRule(TypicalRules.ALIS);
        assertEquals(model.getRuleList().size(), 0);

        model.addRule(TypicalRules.ALIS);
        model.updateRule(TypicalRules.ALIS, GEO);
        assertEquals(model.getRuleList().size(), 1);
        assertTrue(model.getRuleList().get(0).equals(GEO));

        thrown.expect(RuleNotFoundException.class);
        model.deleteRule(TypicalRules.ALIS);

        thrown.expect(DuplicateRuleException.class);
        model.addRule(TypicalRules.ALIS);

        thrown.expect(RuleNotFoundException.class);
        model.updateRule(TypicalRules.ALIS, CAS);

        thrown.expect(DuplicateRuleException.class);
        model.updateRule(GEO, GEO);
    }

    @Test
    public void equals() {
        CoinBook addressBook = new CoinBookBuilder().withCoin(ALIS).withCoin(BTCZ).build();
        CoinBook differentCoinBook = new CoinBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCoinBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALIS.getCode().fullName.split("\\s+");
        modelManager.updateFilteredCoinList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCoinBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
