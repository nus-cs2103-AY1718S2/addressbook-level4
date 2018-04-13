//@@author ewaldhew
package seedu.address.logic.conditions;

import static seedu.address.logic.parser.TokenType.NUM;
import static seedu.address.logic.parser.TokenType.PREFIX_HELD;

import java.util.function.BiPredicate;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.TokenType;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;

/**
 * Represents a predicate that evaluates to true when the amount held of a {@Coin} is either greater than or less than
 * (depending on the amount comparator) the amount specified.
 */
public class AmountHeldChangeCondition extends AmountChangeCondition {

    public static final TokenType PREFIX = PREFIX_HELD;
    public static final TokenType PARAMETER_TYPE = NUM;

    public AmountHeldChangeCondition(Amount amount,
                                     BiPredicate<Amount, Amount> amountComparator,
                                     CompareMode compareMode) {
        super(amount, amountComparator, compareMode);
    }

    @Override
    public boolean test(Coin coin) {
        switch (compareMode) {
        case RISE:
            return amountComparator.test(coin.getChangeFromPrev().getCurrentAmountHeld(), amount);
        case FALL:
            return amountComparator.test(coin.getChangeToPrev().getCurrentAmountHeld(), amount);
        default:
            LogsCenter.getLogger(this.getClass()).warning("Invalid compare mode!");
            return false;
        }
    }
}
