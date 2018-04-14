//@@author ewaldhew
package seedu.address.logic;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CoinChangedEvent;
import seedu.address.commons.events.model.RuleBookChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.RuleBook;
import seedu.address.model.rule.NotificationRule;
import seedu.address.model.rule.Rule;

/**
 * Receives events to check against the rule book triggers.
 */
public class RuleChecker {

    private static final Logger logger = LogsCenter.getLogger(RuleChecker.class);
    private static final String MESSAGE_PROCESS_RULE = "Found %1$s";

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
                assert(r instanceof NotificationRule);
                checkAndFire(r, cce.data);
                break;

            default:
                throw new RuntimeException("Unexpected code path!");
            }
        }
    }

    /**
     * Checks the trigger condition against the provided object, then
     * executes the command tied to it if it matches
     *
     * @param rule containing condition to check with
     * @param data to check against
     * @return Whether the command was successful.
     */
    private static <T> boolean checkAndFire(Rule<T> rule, T data) {
        if (!rule.condition.test(data)) {
            return false;
        }

        try {
            rule.action.setExtraData(data);
            rule.action.execute();
            logger.info(String.format(Rule.MESSAGE_FIRED, rule, data));
            return true;
        } catch (CommandException e) {
            logger.warning(e.getMessage());
            return false;
        }
    }
}
