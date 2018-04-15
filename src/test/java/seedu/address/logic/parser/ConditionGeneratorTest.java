package seedu.address.logic.parser;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TestUtil.AND_TOKEN;
import static seedu.address.testutil.TestUtil.COIN_0;
import static seedu.address.testutil.TestUtil.COIN_1;
import static seedu.address.testutil.TestUtil.COIN_2;
import static seedu.address.testutil.TestUtil.COIN_3;
import static seedu.address.testutil.TestUtil.COIN_4;
import static seedu.address.testutil.TestUtil.COIN_5;
import static seedu.address.testutil.TestUtil.COIN_6;
import static seedu.address.testutil.TestUtil.COIN_7;
import static seedu.address.testutil.TestUtil.DECIMAL_STRING;
import static seedu.address.testutil.TestUtil.DECIMAL_TOKEN;
import static seedu.address.testutil.TestUtil.EOF_TOKEN;
import static seedu.address.testutil.TestUtil.GREATER_TOKEN;
import static seedu.address.testutil.TestUtil.LEFT_PAREN_TOKEN;
import static seedu.address.testutil.TestUtil.LESS_TOKEN;
import static seedu.address.testutil.TestUtil.NOT_TOKEN;
import static seedu.address.testutil.TestUtil.OR_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_BOUGHT_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_CODE_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_HELD_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_MADE_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_PRICE_FALL_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_PRICE_RISE_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_PRICE_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_SOLD_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_TAG_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_WORTH_FALL_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_WORTH_RISE_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_WORTH_TOKEN;
import static seedu.address.testutil.TestUtil.RIGHT_PAREN_TOKEN;
import static seedu.address.testutil.TestUtil.STRING_ONE_STRING;
import static seedu.address.testutil.TestUtil.STRING_ONE_TOKEN;
import static seedu.address.testutil.TestUtil.STRING_THREE_STRING;
import static seedu.address.testutil.TestUtil.STRING_THREE_TOKEN;
import static seedu.address.testutil.TestUtil.STRING_TWO_STRING;
import static seedu.address.testutil.TestUtil.STRING_TWO_TOKEN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.Price;
import seedu.address.testutil.CoinBuilder;

//@@author Eldon-Chung
public class ConditionGeneratorTest {

    private static final Token INVALID_TAG_NAME = new Token(TokenType.STRING, "invalid-name");
    private static final Amount DECIMAL_OFFSET = new Amount("1.0");
    private static final Price ZERO_PRICE = new Price();
    private static final Price NEW_PRICE = new Price();
    private static Amount greaterAmount;
    private static Amount lesserAmount;
    private static Price greaterPrice;
    private static Price lesserPrice;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void initializeTestValues() throws IllegalValueException {
        // Load the offset values to test numerical conditions
        greaterAmount = Amount.getSum(ParserUtil.parseAmount(DECIMAL_STRING), DECIMAL_OFFSET);
        lesserAmount = Amount.getDiff(ParserUtil.parseAmount(DECIMAL_STRING), DECIMAL_OFFSET);
        greaterPrice = new Price();
        greaterPrice.setCurrent(Amount.getSum(ParserUtil.parseAmount(DECIMAL_STRING), DECIMAL_OFFSET));
        lesserPrice = new Price();
        lesserPrice.setCurrent(Amount.getDiff(ParserUtil.parseAmount(DECIMAL_STRING), DECIMAL_OFFSET));
        ZERO_PRICE.setCurrent(new Amount("0"));
        NEW_PRICE.setCurrent(new Amount("2.0"));
    }


    @Test
    public void cond_generatesAmountHeldCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_HELD_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(greaterAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(lesserAmount).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesCodeCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_CODE_TOKEN, STRING_ONE_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withName(STRING_ONE_STRING).build();
        Coin failCoin = new CoinBuilder().withName(STRING_TWO_STRING).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesDollarsBoughtCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_BOUGHT_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(greaterAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(lesserAmount).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesDollarsSoldCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_SOLD_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(greaterAmount)
                .withAmountSold(greaterAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(greaterAmount)
                .withAmountSold(lesserAmount).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesMadeCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_MADE_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(greaterAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(greaterAmount).build();

        // Increase the price on the pass coin to generate some profit
        passCoin = new Coin(passCoin, NEW_PRICE);

        passCoin.addTotalAmountSold(greaterAmount);

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesPriceCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_PRICE_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withPrice(greaterPrice).build();
        Coin failCoin = new CoinBuilder().withPrice(lesserPrice).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesTagCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withTags(STRING_ONE_STRING).build();
        Coin failCoin = new CoinBuilder().withTags(STRING_TWO_STRING).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesWorthCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_WORTH_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(greaterAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(lesserAmount).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void term_generatesConditionWithParentheses() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(LEFT_PAREN_TOKEN, PREFIX_CODE_TOKEN,
                STRING_ONE_TOKEN, RIGHT_PAREN_TOKEN);
        Predicate<Coin> condition = conditionGenerator.term();
        Coin passCoin = new CoinBuilder().withName(STRING_ONE_STRING).build();
        Coin failCoin = new CoinBuilder().withName(STRING_TWO_STRING).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void term_generatesConditionWithNegation() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(NOT_TOKEN, PREFIX_CODE_TOKEN,
                STRING_ONE_TOKEN);
        Predicate<Coin> condition = conditionGenerator.term();
        Coin passCoin = new CoinBuilder().withName(STRING_TWO_STRING).build();
        Coin failCoin = new CoinBuilder().withName(STRING_ONE_STRING).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void expression_generationConditionWithConjunction() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, AND_TOKEN,
                PREFIX_TAG_TOKEN, STRING_TWO_TOKEN, EOF_TOKEN);
        Predicate<Coin> condition = conditionGenerator.expression();

        // Test to see if the conjunction operation commutes
        Coin passCoin1 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_TWO_STRING).build();
        Coin passCoin2 = new CoinBuilder().withTags(STRING_TWO_STRING, STRING_ONE_STRING).build();

        Coin failCoin1 = new CoinBuilder().withTags(STRING_ONE_STRING).build();
        Coin failCoin2 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_THREE_STRING).build();
        Coin failCoin3 = new CoinBuilder().withTags(STRING_TWO_STRING, STRING_THREE_STRING).build();

        assertTrue(condition.test(passCoin1));
        assertTrue(condition.test(passCoin2));

        assertFalse(condition.test(failCoin1));
        assertFalse(condition.test(failCoin2));
        assertFalse(condition.test(failCoin3));
    }

    @Test
    public void expression_generationConditionWithDisjunction() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, OR_TOKEN,
                PREFIX_TAG_TOKEN, STRING_TWO_TOKEN, EOF_TOKEN);
        Predicate<Coin> condition = conditionGenerator.expression();

        Coin passCoin1 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_TWO_STRING).build();
        Coin passCoin2 = new CoinBuilder().withTags(STRING_TWO_STRING, STRING_ONE_STRING).build();
        Coin passCoin3 = new CoinBuilder().withTags(STRING_ONE_STRING).build();
        Coin passCoin4 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_THREE_STRING).build();
        Coin passCoin5 = new CoinBuilder().withTags(STRING_TWO_STRING, STRING_THREE_STRING).build();

        Coin failCoin = new CoinBuilder().withTags(STRING_THREE_STRING).build();

        assertTrue(condition.test(passCoin1));
        assertTrue(condition.test(passCoin2));
        assertTrue(condition.test(passCoin3));
        assertTrue(condition.test(passCoin4));
        assertTrue(condition.test(passCoin5));

        assertFalse(condition.test(failCoin));
    }

    @Test
    public void generate_correctOrderOfPrecedenceWithoutParentheses() throws Exception {
        // Asserts the condition evaluation is done from left to right.
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, OR_TOKEN,
                PREFIX_TAG_TOKEN, STRING_TWO_TOKEN, AND_TOKEN, PREFIX_TAG_TOKEN, STRING_THREE_TOKEN, EOF_TOKEN);
        Predicate<Coin> condition = conditionGenerator.generate();

        assertFalse(condition.test(COIN_0));
        assertFalse(condition.test(COIN_1));
        assertFalse(condition.test(COIN_2));
        assertTrue(condition.test(COIN_3));
        assertFalse(condition.test(COIN_4));
        assertTrue(condition.test(COIN_5));
        assertFalse(condition.test(COIN_6));
        assertTrue(condition.test(COIN_7));
    }

    @Test
    public void generate_correctOrderOfPrecedenceWithParentheses() throws Exception {
        // Asserts the conditions in parentheses are prioritized.
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, OR_TOKEN,
                LEFT_PAREN_TOKEN, PREFIX_TAG_TOKEN, STRING_TWO_TOKEN, AND_TOKEN, PREFIX_TAG_TOKEN, STRING_THREE_TOKEN,
                RIGHT_PAREN_TOKEN, EOF_TOKEN);
        Predicate<Coin> condition = conditionGenerator.generate();

        assertFalse(condition.test(COIN_0));
        assertFalse(condition.test(COIN_1));
        assertFalse(condition.test(COIN_2));
        assertTrue(condition.test(COIN_3));
        assertTrue(condition.test(COIN_4));
        assertTrue(condition.test(COIN_5));
        assertTrue(condition.test(COIN_6));
        assertTrue(condition.test(COIN_7));
    }

    @Test
    public void getPredicateFromPrefix_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, INVALID_TAG_NAME);
        conditionGenerator.generate();
    }

    //@@author ewaldhew
    @Test
    public void cond_generatesPriceRiseCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_PRICE_RISE_TOKEN, LESS_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withPrice(ZERO_PRICE).build();
        Coin failCoin = new CoinBuilder().withPrice(ZERO_PRICE).build();

        passCoin = new Coin(passCoin, lesserPrice);
        failCoin = new Coin(failCoin, greaterPrice);

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesWorthRiseCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_WORTH_RISE_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().build();
        Coin failCoin = new CoinBuilder().build();

        passCoin.addTotalAmountBought(greaterAmount);
        failCoin.addTotalAmountBought(lesserAmount);

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesPriceFallCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_PRICE_FALL_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withPrice(greaterPrice).build();
        Coin failCoin = new CoinBuilder().withPrice(lesserPrice).build();

        passCoin = new Coin(passCoin, ZERO_PRICE);
        failCoin = new Coin(failCoin, ZERO_PRICE);

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesWorthFallCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_WORTH_FALL_TOKEN, LESS_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(lesserAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(greaterAmount).build();

        passCoin = new Coin(passCoin, ZERO_PRICE);
        failCoin = new Coin(failCoin, ZERO_PRICE);

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }
    //@@author

    private static ConditionGenerator initGenerator(Token... tokens) {
        return new ConditionGenerator(new TokenStack(new ArrayList<Token>(Arrays.asList(tokens))));
    }

}
