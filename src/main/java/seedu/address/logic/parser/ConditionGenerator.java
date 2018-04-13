package seedu.address.logic.parser;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.conditions.AmountChangeCondition.CompareMode;
import seedu.address.logic.conditions.AmountHeldChangeCondition;
import seedu.address.logic.conditions.AmountHeldCondition;
import seedu.address.logic.conditions.CodeCondition;
import seedu.address.logic.conditions.CurrentPriceChangeCondition;
import seedu.address.logic.conditions.CurrentPriceCondition;
import seedu.address.logic.conditions.DollarsBoughtChangeCondition;
import seedu.address.logic.conditions.DollarsBoughtCondition;
import seedu.address.logic.conditions.DollarsSoldChangeCondition;
import seedu.address.logic.conditions.DollarsSoldCondition;
import seedu.address.logic.conditions.MadeChangeCondition;
import seedu.address.logic.conditions.MadeCondition;
import seedu.address.logic.conditions.TagCondition;
import seedu.address.logic.conditions.WorthChangeCondition;
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
        CompareMode compareMode = getCompareModeFromType(type);
        switch (type) {
        case PREFIX_HELD:
        case PREFIX_HELD_RISE:
        case PREFIX_HELD_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            if (compareMode == null) {
                return new AmountHeldCondition(specifiedAmount, amountComparator);
            } else {
                return new AmountHeldChangeCondition(specifiedAmount, amountComparator, compareMode);
            }

        case PREFIX_SOLD:
        case PREFIX_SOLD_RISE:
        case PREFIX_SOLD_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            if (compareMode == null) {
                return new DollarsSoldCondition(specifiedAmount, amountComparator);
            } else {
                return new DollarsSoldChangeCondition(specifiedAmount, amountComparator, compareMode);
            }

        case PREFIX_BOUGHT:
        case PREFIX_BOUGHT_RISE:
        case PREFIX_BOUGHT_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            if (compareMode == null) {
                return new DollarsBoughtCondition(specifiedAmount, amountComparator);
            } else {
                return new DollarsBoughtChangeCondition(specifiedAmount, amountComparator, compareMode);
            }

        case PREFIX_MADE:
        case PREFIX_MADE_RISE:
        case PREFIX_MADE_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            if (compareMode == null) {
                return new MadeCondition(specifiedAmount, amountComparator);
            } else {
                return new MadeChangeCondition(specifiedAmount, amountComparator, compareMode);
            }

        case PREFIX_PRICE:
        case PREFIX_PRICE_RISE:
        case PREFIX_PRICE_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            if (compareMode == null) {
                return new CurrentPriceCondition(specifiedAmount, amountComparator);
            } else {
                return new CurrentPriceChangeCondition(specifiedAmount, amountComparator, compareMode);
            }

        case PREFIX_WORTH:
        case PREFIX_WORTH_RISE:
        case PREFIX_WORTH_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            if (compareMode == null) {
                return new WorthCondition(specifiedAmount, amountComparator);
            } else {
                return new WorthChangeCondition(specifiedAmount, amountComparator, compareMode);
            }

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

    private CompareMode getCompareModeFromType(TokenType type) {
        switch (type) {
        case PREFIX_HELD_RISE:
        case PREFIX_SOLD_RISE:
        case PREFIX_BOUGHT_RISE:
        case PREFIX_MADE_RISE:
        case PREFIX_PRICE_RISE:
        case PREFIX_WORTH_RISE:
            return CompareMode.RISE;

        case PREFIX_HELD_FALL:
        case PREFIX_SOLD_FALL:
        case PREFIX_BOUGHT_FALL:
        case PREFIX_MADE_FALL:
        case PREFIX_PRICE_FALL:
        case PREFIX_WORTH_FALL:
            return CompareMode.FALL;

        default:
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
