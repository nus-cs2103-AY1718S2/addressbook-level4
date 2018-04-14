//@@author ewaldhew
package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;
import static seedu.address.testutil.TypicalRules.getTypicalRuleBook;
import static seedu.address.testutil.TypicalTargets.INDEX_FIRST_COIN;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.CoinChangedEvent;
import seedu.address.commons.events.model.RuleBookChangedEvent;
import seedu.address.commons.events.ui.ShowNotificationRequestEvent;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RuleBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.testutil.TestUtil;
import seedu.address.ui.testutil.EventsCollectorRule;

public class RuleCheckerTest {

    private static final RuleBook emptyRuleBook = new RuleBook();
    private static final RuleBook testRuleBook = getTypicalRuleBook();
    private static final Model model = new ModelManager(getTypicalCoinBook(), new UserPrefs());


    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private RuleChecker ruleChecker;
    private CoinChangedEvent event;

    @Before
    public void setUp() {
        Coin firstCoin = TestUtil.getCoin(model, INDEX_FIRST_COIN);
        Coin editedCoin = new Coin(firstCoin);
        editedCoin.addTotalAmountBought(new Amount("10"));
        event = new CoinChangedEvent(INDEX_FIRST_COIN, firstCoin, editedCoin);
    }

    @Test
    public void handleCoinChangedEvent_emptyRules_doesNotPost() {
        ruleChecker = new RuleChecker(emptyRuleBook);

        EventsCenter.getInstance().post(event);
        assertFalse(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowNotificationRequestEvent);
    }

    @Test
    public void updateRuleBook_validRuleTypeMatches_postsShowNotifEvent() {
        ruleChecker = new RuleChecker(emptyRuleBook);

        EventsCenter.getInstance().post(new RuleBookChangedEvent(testRuleBook));

        EventsCenter.getInstance().post(event);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowNotificationRequestEvent);

        ShowNotificationRequestEvent snre =
                (ShowNotificationRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(snre.codeString, event.data.getCode().toString());
        assertEquals(snre.targetIndex, INDEX_FIRST_COIN);
    }
}
