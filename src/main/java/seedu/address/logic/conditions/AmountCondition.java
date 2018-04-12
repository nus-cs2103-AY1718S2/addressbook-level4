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

    /**
     * Indicates whether to compare absolute or change
     */
    public enum CompareMode {
        EQUALS,
        RISE,
        FALL
    }

    protected BiPredicate<Amount, Amount> amountComparator;
    protected Amount amount;
    protected CompareMode compareMode;

    public AmountCondition(Amount amount, BiPredicate<Amount, Amount> amountComparator, CompareMode compareMode) {
        this.amount = amount;
        this.amountComparator = amountComparator;
        this.compareMode = compareMode;
    }

    public abstract boolean test(Coin coin);
}
