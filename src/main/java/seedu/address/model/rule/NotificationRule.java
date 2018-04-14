//@@author ewaldhew
package seedu.address.model.rule;

import seedu.address.logic.commands.SpawnNotificationCommand;
import seedu.address.logic.parser.NotifyCommandParser;
import seedu.address.model.coin.Coin;

/**
 * Represents a rule trigger for spawning notifications.
 * The target object type is Coins.
 */
public class NotificationRule extends Rule<Coin> {

    private static final ActionParser<Coin> parseAction = SpawnNotificationCommand::new;
    private static final ConditionParser<Coin> parseCondition = NotifyCommandParser::parseNotifyCondition;

    public NotificationRule(String value) {
        super(value, RuleType.NOTIFICATION, parseAction, parseCondition);
    }

}
