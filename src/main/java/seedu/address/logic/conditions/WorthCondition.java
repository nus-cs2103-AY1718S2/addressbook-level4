package seedu.address.logic.conditions;

import static seedu.address.logic.parser.TokenType.PREFIX_WORTH;

import java.util.function.BiPredicate;

import seedu.address.logic.parser.TokenType;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;

//@@author Eldon-Chung
/**
 * Represents a predicate that evaluates to true when the worth of a {@Coin} is either greater than or less than
 * (depending on the amount comparator) the amount specified.
 */
public class WorthCondition extends AmountCondition  {

    public static final TokenType PREFIX = PREFIX_WORTH;

    public WorthCondition(Amount amount, BiPredicate<Amount, Amount> amountComparator) {
        super(amount, amountComparator);
    }

    @Override
    public boolean test(Coin coin) {
        return amountComparator.test(coin.getDollarsWorth(), amount);
    }
}
