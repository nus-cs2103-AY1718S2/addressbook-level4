package seedu.address.logic.conditions;

import static seedu.address.logic.parser.TokenType.NUM;
import static seedu.address.logic.parser.TokenType.PREFIX_PRICE;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import seedu.address.logic.parser.TokenType;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.Price;

//@@author Eldon-Chung
/**
 * Represents a predicate that evaluates to true when the price of a {@Coin} is either greater than or less than
 * (depending on the amount comparator) the amount specified.
 */
public class PriceCondition implements Predicate<Coin> {

    public static final TokenType PREFIX = PREFIX_PRICE;
    public static final TokenType PARAMETER_TYPE = NUM;

    private BiPredicate<Price, Price> priceComparator;
    private Price price;

    public PriceCondition(Price price, BiPredicate<Price, Price> priceComparator) {
        this.price = price;
        this.priceComparator = priceComparator;
    }

    @Override
    public boolean test(Coin coin) {
        return priceComparator.test(coin.getPrice(), price);
    }
}
