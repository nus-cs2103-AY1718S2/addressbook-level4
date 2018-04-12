package seedu.address.logic.conditions;

import static seedu.address.logic.parser.TokenType.PREFIX_CODE;
import static seedu.address.logic.parser.TokenType.STRING;

import java.util.function.Predicate;

import seedu.address.logic.parser.TokenType;
import seedu.address.model.coin.Coin;

//@@author Eldon-Chung
/**
 * Represents a predicate that evaluates to true when a {@Coin} contains the {@Code} specified.
 */
public class CodeCondition implements Predicate<Coin> {

    public static final TokenType PREFIX = PREFIX_CODE;
    public static final TokenType PARAMETER_TYPE = STRING;

    private String substring;

    public CodeCondition(String substring) {
        this.substring = substring;
    }

    @Override
    public boolean test(Coin coin) {
        return coin.getCode().toString().toUpperCase().contains(substring.toUpperCase());
    }
}
