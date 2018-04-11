package seedu.address.logic.conditions;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;

//@@author Eldon-Chung
/**
 * Represents the predicates that evaluate two Amount objects. Is
 */
public abstract class AmountCondition implements Predicate<Coin> {

    protected BiPredicate<Amount, Amount> amountComparator;
    protected Amount amount;

    public AmountCondition(Amount amount, BiPredicate<Amount, Amount> amountComparator) {
        this.amount = amount;
        this.amountComparator = amountComparator;
    }

    public abstract boolean test(Coin coin);
}
