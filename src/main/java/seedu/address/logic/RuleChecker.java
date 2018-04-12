//@@author ewaldhew
package seedu.address.logic;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CoinChangedEvent;
import seedu.address.commons.events.model.RuleBookChangedEvent;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.RuleBook;
import seedu.address.model.rule.NotificationRule;
import seedu.address.model.rule.Rule;

/**
 * Receives events to check against the rule book triggers.
 */
public class RuleChecker {
    private static final Logger logger = LogsCenter.getLogger(RuleChecker.class);
    private final RuleBook rules;

    public RuleChecker(ReadOnlyRuleBook rules) {
        this.rules = new RuleBook(rules);
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    public void handleRuleBookChangedEvent(RuleBookChangedEvent rbce) {
        logger.info(LogsCenter.getEventHandlingLogMessage(rbce, "Reloading rule book..."));
        this.rules.resetData(rbce.data);
    }

    @Subscribe
    public void handleCoinChangedEvent(CoinChangedEvent cce) {
        for (Rule r : rules.getRuleList()) {
            switch (r.type) {
            case NOTIFICATION:
                NotificationRule nRule = (NotificationRule) r;
                nRule.checkAndFire(cce.newCoin);
                break;
            default:
                throw new RuntimeException("Unexpected code path!");
            }
        }
    }

}
