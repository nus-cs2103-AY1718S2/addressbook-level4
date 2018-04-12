package seedu.address.logic.parser;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.conditions.AmountHeldCondition;
import seedu.address.logic.conditions.CodeCondition;
import seedu.address.logic.conditions.CurrentPriceCondition;
import seedu.address.logic.conditions.DollarsBoughtCondition;
import seedu.address.logic.conditions.DollarsSoldCondition;
import seedu.address.logic.conditions.MadeCondition;
import seedu.address.logic.conditions.TagCondition;
import seedu.address.logic.conditions.WorthCondition;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.model.tag.Tag;

/**
 * Generates the predicate based ont he tokenized boolean logic statements to verify correctness.
 */
//@@author Eldon-Chung
public class ConditionGenerator {

    private TokenStack tokenStack;

    public ConditionGenerator(TokenStack tokenStack) {
        this.tokenStack = tokenStack;
        this.tokenStack.resetStack();
    }

    /**
     * @return Generates a predicate on {@code Coin} objects based on the argument represented by the token stack.
     * @throws IllegalValueException
     */
    public Predicate<Coin> generate() throws IllegalValueException {
        return expression();
    }

    /**
     * @return Generates a predicate on {@code Coin} objects based on the current EXPRESSION. (see DeveloperGuide.adoc)
     * @throws IllegalValueException
     */
    Predicate<Coin> expression() throws IllegalValueException {
        Predicate<Coin> condition = term();
        while (tokenStack.matchTokenType(TokenType.BINARYBOOL)) {
            Token operatorToken = tokenStack.popToken();
            Predicate<Coin> secondCondition = term();
            switch (operatorToken.getPattern()) {
            case " AND ":
                condition = condition.and(secondCondition);
                break;
            case " OR ":
                condition = condition.or(secondCondition);
                break;
            default:
                break;
            }
        }
        return condition;
    }

    /**
     * @return Generates a predicate on {@code Coin} objects based on the current TERM. (see DeveloperGuide.adoc)
     * @throws IllegalValueException
     */
    Predicate<Coin> term() throws IllegalValueException {
        Predicate<Coin> condition;
        if (tokenStack.matchAndPopTokenType(TokenType.LEFTPARENTHESES)) {
            condition = expression();
            tokenStack.matchAndPopTokenType(TokenType.RIGHTPARENTHESES);
            return condition;
        } else if (tokenStack.matchAndPopTokenType(TokenType.UNARYBOOL)) {
            return term().negate();
        }
        return cond();
    }

    /**
     * @return Generates a predicate on {@code Coin} objects based on the current COND. (see DeveloperGuide.adoc)
     * @throws IllegalValueException
     */
    Predicate<Coin> cond() throws IllegalValueException {
        return getPredicateFromPrefix(tokenStack.popToken().getType());
    }

    /**
     * @param type
     * @return a base predicate based on the prefix that is currently at the top of the stack.
     * @throws IllegalValueException
     */
    Predicate<Coin> getPredicateFromPrefix(TokenType type) throws IllegalValueException {
        BiPredicate<Amount, Amount> amountComparator;
        Amount specifiedAmount;
        switch (type) {
        case PREFIX_HELD:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new AmountHeldCondition(specifiedAmount, amountComparator);

        case PREFIX_SOLD:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new DollarsSoldCondition(specifiedAmount, amountComparator);

        case PREFIX_BOUGHT:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new DollarsBoughtCondition(specifiedAmount, amountComparator);

        case PREFIX_MADE:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new MadeCondition(specifiedAmount, amountComparator);

        case PREFIX_PRICE:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new CurrentPriceCondition(specifiedAmount, amountComparator);

        case PREFIX_WORTH:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new WorthCondition(specifiedAmount, amountComparator);

        case PREFIX_CODE:
            return new CodeCondition(tokenStack.popToken().getPattern());

        case PREFIX_TAG:
            Tag tag = ParserUtil.parseTag(tokenStack.popToken().getPattern());
            return new TagCondition(tag);

        default:
            assert false;
            return null;
        }
    }

    private static BiPredicate<Amount, Amount> getAmountComparatorFromToken(Token token) {
        switch (token.getPattern()) {
        case "=":
            return (amount1, amount2) -> (amount1.compareTo(amount2) == 0);
        case ">":
            return (amount1, amount2) -> (amount1.compareTo(amount2) > 0);
        case "<":
            return (amount1, amount2) -> (amount1.compareTo(amount2) < 0);
        default:
            return null;
        }
    }
}
