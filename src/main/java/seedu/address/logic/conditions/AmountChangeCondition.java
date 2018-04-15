//@@author ewaldhew
package seedu.address.logic.conditions;

import java.util.function.BiPredicate;

import seedu.address.model.coin.Amount;

/**
 * Represents the predicates that evaluate two Amount objects. Is
 */
public abstract class AmountChangeCondition extends AmountCondition {

    /**
     * Indicates whether to compare absolute or change
     */
    public enum CompareMode {
        RISE,
        FALL
    }

    public final CompareMode compareMode;

    public AmountChangeCondition(Amount amount, BiPredicate<Amount, Amount> amountComparator, CompareMode compareMode) {
        super(amount, amountComparator);
        this.compareMode = compareMode;
    }
}
