# Eldon-Chung
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
public class FindCommandTest {

    private Model model;

    @Before
    public void setupModel() {
        this.model = new ModelManager();
    }

    @Test
    public void execute_nullCondition_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_COINS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand((coin) -> false);
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_findTags() throws DuplicateCoinException {
        String expectedMessage = String.format(MESSAGE_COINS_LISTED_OVERVIEW, 4);
        Predicate<Coin> tagCondition = new TagCondition(new Tag(STRING_ONE_STRING));
        FindCommand command = prepareCommand(tagCondition);
        model.addCoin(COIN_0);
        model.addCoin(COIN_1);
        model.addCoin(COIN_2);
        model.addCoin(COIN_3);
        model.addCoin(COIN_4);
        model.addCoin(COIN_5);
        model.addCoin(COIN_6);
        model.addCoin(COIN_7);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(COIN_4, COIN_5, COIN_6, COIN_7));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(Predicate<Coin> coinCondition) {
        FindCommand command =
                new FindCommand(coinCondition);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<Coin> expectedList) {
        CoinBook expectedAddressBook = new CoinBook(model.getCoinBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredCoinList());
        assertEquals(expectedAddressBook, model.getCoinBook());
    }

}
```
###### \java\seedu\address\logic\parser\BuyCommandParserTest.java
``` java
public class BuyCommandParserTest {
    private static final String INDEX_AS_STRING = "1";
    private static final String INT_AS_STRING = "50";
    private static final String FLOAT_AS_STRING = "50.01";
    private static final String INVALID_VALUE_AS_STRING = "asd";

    private BuyCommandParser parser = new BuyCommandParser();

    private BuyCommand constructBuyCommand(String indexAsString, String valueAsString) throws IllegalValueException {
        return new BuyCommand(new CommandTarget(ParserUtil.parseIndex(indexAsString)),
                ParserUtil.parseAmount(valueAsString));
    }

    /**
     * Appends strings together with a space in between each of them.
     */
    private String buildCommandString(String... strings) {
        StringBuilder commandStringBuilder = new StringBuilder();
        for (String str : strings) {
            commandStringBuilder.append(String.format(" %s", str));
        }
        return commandStringBuilder.toString().trim();
    }

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String commandString = buildCommandString(INDEX_AS_STRING, PREFIX_AMOUNT.toString(), INT_AS_STRING);
        Command command = constructBuyCommand(INDEX_AS_STRING, INT_AS_STRING);
        assertParseSuccess(parser, commandString, command);
        commandString = buildCommandString(INDEX_AS_STRING, PREFIX_AMOUNT.toString(), FLOAT_AS_STRING);
        command = constructBuyCommand(INDEX_AS_STRING, FLOAT_AS_STRING);
        assertParseSuccess(parser, commandString, command);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE);

        //missing amount prefix
        String commandString = buildCommandString(INDEX_AS_STRING, INT_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        //missing actual amount after prefix
        commandString = buildCommandString(INDEX_AS_STRING, PREFIX_AMOUNT.toString());
        assertParseFailure(parser, commandString, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE);

        // invalid prefix
        String commandString = buildCommandString(INDEX_AS_STRING, PREFIX_NAME.toString(), INDEX_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        // invalid value
        commandString = buildCommandString(INDEX_AS_STRING, PREFIX_NAME.toString(), INVALID_VALUE_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        // empty preamble
        commandString = buildCommandString(PREFIX_NAME.toString(), INT_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\ConditionGeneratorTest.java
``` java
public class ConditionGeneratorTest {

    private static final Token INVALID_TAG_NAME = new Token(TokenType.STRING, "invalid-name");
    private static final Amount DECIMAL_OFFSET = new Amount("1.0");
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

    private static ConditionGenerator initGenerator(Token... tokens) {
        return new ConditionGenerator(new TokenStack(new ArrayList<Token>(Arrays.asList(tokens))));
    }

}
```
###### \java\seedu\address\logic\parser\ConditionSemanticParserTest.java
``` java
public class ConditionSemanticParserTest {

    private ConditionSemanticParser conditionSemanticParser;
    private TokenStack tokenStack;


    private static ConditionSemanticParser initParser(Token... tokens) {
        return new ConditionSemanticParser(new TokenStack(new ArrayList<Token>(Arrays.asList(tokens))));
    }

    @Test
    public void parse_returnsTrueOnValidInput() throws Exception {
        conditionSemanticParser = initParser(PREFIX_PRICE_TOKEN, GREATER_TOKEN, NUM_TOKEN);
        assertTrue(conditionSemanticParser.parse());
        conditionSemanticParser = initParser(PREFIX_PRICE_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        assertTrue(conditionSemanticParser.parse());
        conditionSemanticParser = initParser(PREFIX_CODE_TOKEN, STRING_ONE_TOKEN);
        assertTrue(conditionSemanticParser.parse());
    }

    @Test
    public void parse_returnsFalseOnInvalidInput() throws Exception {
        conditionSemanticParser = initParser(PREFIX_PRICE_TOKEN, NUM_TOKEN);
        assertFalse(conditionSemanticParser.parse());
        conditionSemanticParser = initParser(PREFIX_PRICE_TOKEN, GREATER_TOKEN, STRING_ONE_TOKEN);
        assertFalse(conditionSemanticParser.parse());
        conditionSemanticParser = initParser(PREFIX_CODE_TOKEN, STRING_ONE_TOKEN);
        assertTrue(conditionSemanticParser.parse());
    }

}
```
###### \java\seedu\address\logic\parser\ConditionSyntaxParserTest.java
``` java
public class ConditionSyntaxParserTest {

    /*
    * Do not use the test tokens from ParserUtil because we want to assert that
    * the ConditionSyntaxParser ONLY uses at the TokenType of each token.
    */
    private static final Token BINARYBOOL = new Token(TokenType.BINARYBOOL, "");
    private static final Token UNARYBOOL = new Token(TokenType.UNARYBOOL, "");
    private static final Token LEFTPARENTHESES = new Token(TokenType.LEFTPARENTHESES, "");
    private static final Token RIGHTPARENTHESES = new Token(TokenType.RIGHTPARENTHESES, "");
    private static final Token COMPARATOR = new Token(TokenType.COMPARATOR, "");
    private static final Token PREFIXAMOUNT = new Token(TokenType.PREFIX_AMOUNT, "");
    private static final Token NUM = new Token(TokenType.NUM, "");
    private static final Token STRING = new Token(TokenType.STRING, "");
    private static final Token SLASH = new Token(TokenType.SLASH, "");
    private static final Token WHITESPACE = new Token(TokenType.WHITESPACE, "");
    private static final Token NEWLINE = new Token(TokenType.NEWLINE, "");
    private static final Token ELSE = new Token(TokenType.ELSE, "");
    private static final Token EOF = new Token(TokenType.EOF, "");

    private ConditionSyntaxParser conditionSyntaxParser;
    private TokenStack tokenStack;


    private static ConditionSyntaxParser initParser(Token... tokens) {
        return new ConditionSyntaxParser(new TokenStack(new ArrayList<Token>(Arrays.asList(tokens))));
    }

    @Test
    public void parseCond() {
        conditionSyntaxParser = initParser(PREFIXAMOUNT, STRING, EOF);
        assertTrue(conditionSyntaxParser.cond());
        conditionSyntaxParser = initParser(PREFIXAMOUNT, COMPARATOR, NUM, EOF);
        assertTrue(conditionSyntaxParser.cond());
        conditionSyntaxParser = initParser(STRING, EOF);
        assertFalse(conditionSyntaxParser.cond());
    }

    @Test
    public void term() {
        conditionSyntaxParser = initParser(PREFIXAMOUNT, STRING, EOF);
        assertTrue(conditionSyntaxParser.term());
        conditionSyntaxParser = initParser(UNARYBOOL, PREFIXAMOUNT, STRING, EOF);
        assertTrue(conditionSyntaxParser.term());
        conditionSyntaxParser = initParser(LEFTPARENTHESES, PREFIXAMOUNT,
                STRING, RIGHTPARENTHESES, EOF);
        assertTrue(conditionSyntaxParser.term());
        conditionSyntaxParser = initParser(LEFTPARENTHESES, STRING, EOF);
        assertFalse(conditionSyntaxParser.term());
    }

    @Test
    public void expression() {
        conditionSyntaxParser = initParser(PREFIXAMOUNT, STRING, EOF);
        assertTrue(conditionSyntaxParser.expression());
        conditionSyntaxParser = initParser(UNARYBOOL, PREFIXAMOUNT, STRING, EOF);
        assertTrue(conditionSyntaxParser.expression());
        conditionSyntaxParser = initParser(PREFIXAMOUNT, STRING, BINARYBOOL, PREFIXAMOUNT, STRING, EOF);
        assertTrue(conditionSyntaxParser.expression());
        conditionSyntaxParser = initParser(PREFIXAMOUNT, STRING, BINARYBOOL, LEFTPARENTHESES,
                PREFIXAMOUNT, STRING, BINARYBOOL, UNARYBOOL, PREFIXAMOUNT, STRING,
                RIGHTPARENTHESES, EOF);
        assertTrue(conditionSyntaxParser.expression());
        conditionSyntaxParser = initParser(PREFIXAMOUNT, STRING, BINARYBOOL, EOF);
        assertFalse(conditionSyntaxParser.expression());
    }

    @Test
    public void parse_returnsTrueOnValidInputs() {
        conditionSyntaxParser = initParser(PREFIXAMOUNT, STRING, PREFIXAMOUNT, EOF);
        assertFalse(conditionSyntaxParser.parse());
        conditionSyntaxParser = initParser(PREFIXAMOUNT, STRING, BINARYBOOL, LEFTPARENTHESES,
                PREFIXAMOUNT, STRING, BINARYBOOL, UNARYBOOL, PREFIXAMOUNT, STRING,
                RIGHTPARENTHESES, RIGHTPARENTHESES, EOF);
        assertFalse(conditionSyntaxParser.parse());
        conditionSyntaxParser = initParser(LEFTPARENTHESES, PREFIXAMOUNT, STRING, BINARYBOOL,
                PREFIXAMOUNT, COMPARATOR, NUM, RIGHTPARENTHESES, BINARYBOOL, PREFIXAMOUNT,
                COMPARATOR, NUM, EOF);
    }

    @Test
    public void parse_returnsFalseOnEmptyInput() {
        conditionSyntaxParser = initParser(EOF);
        assertFalse(conditionSyntaxParser.parse());
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseCommand_validArgument_returnsPredicate() throws Exception {
        TokenStack tokenStack = initTokenStack(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, AND_TOKEN, NOT_TOKEN,
                LEFT_PAREN_TOKEN, PREFIX_TAG_TOKEN, STRING_TWO_TOKEN, OR_TOKEN, PREFIX_TAG_TOKEN,
                STRING_THREE_TOKEN, RIGHT_PAREN_TOKEN, EOF_TOKEN);
        Predicate<Coin> condition = ParserUtil.parseCondition(tokenStack);

        assertFalse(condition.test(COIN_0));
        assertFalse(condition.test(COIN_1));
        assertFalse(condition.test(COIN_2));
        assertFalse(condition.test(COIN_3));
        assertTrue(condition.test(COIN_4));
        assertFalse(condition.test(COIN_5));
        assertFalse(condition.test(COIN_6));
        assertFalse(condition.test(COIN_7));
    }

    @Test
    public void parseCondition_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseCondition(null);
    }

    @Test
    public void parseCondition_invalidArgumentSyntax_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        TokenStack tokenStack = initTokenStack(LEFT_PAREN_TOKEN, PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, EOF_TOKEN);
        ParserUtil.parseCondition(tokenStack);
    }

    @Test
    public void parseCondition_invalidArgumentSemantics_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        TokenStack tokenStack = initTokenStack(LEFT_PAREN_TOKEN, PREFIX_TAG_TOKEN, NUM_TOKEN, EOF_TOKEN);
        ParserUtil.parseCondition(tokenStack);
    }

    private static TokenStack initTokenStack(Token... tokens) {
        return new TokenStack(new ArrayList<Token>(Arrays.asList(tokens)));
    }
```
###### \java\seedu\address\logic\parser\TokenStackTest.java
``` java
public class TokenStackTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TokenStack initTokenStack(Token... tokens) {
        return new TokenStack(new ArrayList<Token>(Arrays.asList(tokens)));
    }

    @Test
    public void tokenStackConstructorRemovesWhiteSpace() {
        TokenStack tokenStack = initTokenStack(STRING_ONE_TOKEN, NUM_TOKEN);
        assertEquals(Arrays.asList(STRING_ONE_TOKEN, NUM_TOKEN), tokenStack.getTokenList());
        tokenStack = initTokenStack(STRING_ONE_TOKEN, WHITESPACE_TOKEN, NUM_TOKEN);
        assertEquals(Arrays.asList(STRING_ONE_TOKEN, NUM_TOKEN), tokenStack.getTokenList());
    }

    @Test
    public void matchAndPopTokenType_returnsTrueFalse() throws Exception {
        TokenStack tokenStack;
        tokenStack = initTokenStack(STRING_ONE_TOKEN);
        assertTrue(tokenStack.matchAndPopTokenType(STRING));
        tokenStack = initTokenStack(STRING_ONE_TOKEN, NUM_TOKEN);
        assertTrue(tokenStack.matchAndPopTokenType(STRING));
        assertTrue(tokenStack.matchAndPopTokenType(NUM));
        tokenStack = initTokenStack(STRING_ONE_TOKEN);
        assertFalse(tokenStack.matchAndPopTokenType(NUM));
    }

    @Test
    public void matchAndPopTokenType_throwsEmptyStackExceptionOnEmptyStack() throws Exception {
        TokenStack tokenStack;
        thrown.expect(EmptyStackException.class);
        tokenStack = initTokenStack();
        tokenStack.matchAndPopTokenType(STRING);
    }

    @Test
    public void matchAndPopTokenType_throwsEmptyStackExceptionOnNonEmptyStack() throws Exception {
        TokenStack tokenStack;
        thrown.expect(EmptyStackException.class);
        tokenStack = initTokenStack(STRING_ONE_TOKEN);
        tokenStack.matchAndPopTokenType(STRING);
        tokenStack.matchAndPopTokenType(STRING);
    }

    @Test
    public void matchTokenType_returnsTrueFalse() throws Exception {
        TokenStack tokenStack;
        tokenStack = initTokenStack(STRING_ONE_TOKEN);
        assertTrue(tokenStack.matchTokenType(STRING));
        tokenStack = initTokenStack(STRING_ONE_TOKEN, NUM_TOKEN);
        assertTrue(tokenStack.matchTokenType(STRING));
        assertTrue(tokenStack.matchTokenType(STRING));
    }

    @Test
    public void matchTokenType_throwsEmptyStackExceptionOnEmptyStack() throws Exception {
        TokenStack tokenStack;
        thrown.expect(EmptyStackException.class);
        tokenStack = initTokenStack();
        tokenStack.matchTokenType(STRING);
    }

    @Test
    public void popToken() throws Exception {
        TokenStack tokenStack = initTokenStack(STRING_ONE_TOKEN);
        assertEquals(STRING_ONE_TOKEN, tokenStack.popToken());
        assertTrue(tokenStack.isEmpty());
    }

    @Test
    public void popToken_throwsEmptyStackExceptionOnEmptyStack() throws Exception {
        thrown.expect(EmptyStackException.class);
        TokenStack tokenStack = initTokenStack();
        tokenStack.popToken();
    }

    @Test
    public void peekToken() throws Exception {
        TokenStack tokenStack = initTokenStack(STRING_ONE_TOKEN);
        assertEquals(STRING_ONE_TOKEN, tokenStack.peekToken());
        assertFalse(tokenStack.isEmpty());
    }

    @Test
    public void peekToken_throwsEmptyStackExceptionOnEmptyStack() throws Exception {
        thrown.expect(EmptyStackException.class);
        TokenStack tokenStack = initTokenStack();
        tokenStack.peekToken();
    }

    @Test
    public void resetStack() throws Exception {
        TokenStack tokenStack = initTokenStack(STRING_ONE_TOKEN, NUM_TOKEN, STRING_ONE_TOKEN,
                STRING_ONE_TOKEN, NUM_TOKEN);
        assertTrue(tokenStack.matchAndPopTokenType(STRING));
        assertTrue(tokenStack.matchAndPopTokenType(NUM));
        assertTrue(tokenStack.matchAndPopTokenType(STRING));
        assertTrue(tokenStack.matchAndPopTokenType(STRING));
        assertTrue(tokenStack.matchAndPopTokenType(NUM));
        assertTrue(tokenStack.isEmpty());
        tokenStack.resetStack();
        assertTrue(tokenStack.matchAndPopTokenType(STRING));
        assertTrue(tokenStack.matchAndPopTokenType(NUM));
        assertTrue(tokenStack.matchAndPopTokenType(STRING));
        assertTrue(tokenStack.matchAndPopTokenType(STRING));
        assertTrue(tokenStack.matchAndPopTokenType(NUM));
        assertTrue(tokenStack.isEmpty());
    }

    @Test
    public void isEmpty() throws Exception {
        TokenStack tokenStack = initTokenStack();
        assertTrue(tokenStack.isEmpty());
        tokenStack = initTokenStack(STRING_ONE_TOKEN);
        tokenStack.popToken();
        assertTrue(tokenStack.isEmpty());
    }

    @Test
    public void getLastExpectedType() throws Exception {
        TokenStack tokenStack = initTokenStack(STRING_ONE_TOKEN);
        assertNull(tokenStack.getLastExpectedType());
        tokenStack.matchAndPopTokenType(STRING);
        assertEquals(STRING, tokenStack.getLastExpectedType());
        tokenStack = initTokenStack(STRING_ONE_TOKEN, NUM_TOKEN);
        tokenStack.matchAndPopTokenType(NUM);
        assertEquals(NUM, tokenStack.getLastExpectedType());
    }

    @Test
    public void getLastExpectedType_returnsNullOnEmptyStack() throws Exception {
        TokenStack tokenStack = initTokenStack();
        assertNull(tokenStack.getLastExpectedType());
    }

    @Test
    public void getActualType() throws Exception {
        TokenStack tokenStack = initTokenStack(STRING_ONE_TOKEN);
        assertEquals(STRING, tokenStack.getActualType());
    }

    @Test
    public void getActualType_throwsEmptyStackException() throws Exception {
        thrown.expect(EmptyStackException.class);
        TokenStack tokenStack = initTokenStack();
        tokenStack.getActualType();
    }

}
```
###### \java\seedu\address\testutil\CoinBuilder.java
``` java
    /**
     * Sets the {@code price} of the {@code Coin} that we are building.
     */
    public CoinBuilder withPrice(Price price) {
        this.price = price;
        return this;
    }

    /**
     * Sets the {@code totalAmountSold} of the {@code Coin} that we are building.
     */
    public CoinBuilder withAmountSold(Amount amountSold) {
        this.amountSold = amountSold;
        return this;
    }

    /**
     * Sets the {@code totalAmountSold} of the {@code Coin} that we are building.
     */
    public CoinBuilder withAmountBought(Amount amountBought) {
        this.amountBought = amountBought;
        return this;
    }

    /**
     * @return a {@Coin} with the set code, tags, and amount sold and bought.
     */
    public Coin build() {
        Coin coin = new Coin(code, tags);
        coin = new Coin(coin, price);
        coin.addTotalAmountBought(amountBought);
        coin.addTotalAmountSold(amountSold);
        return coin;
    }
```
###### \java\seedu\address\testutil\TestUtil.java
``` java
    /**
     * Tokens and strings for ArgumentTokenizer, Parsing and TokenStack testing.
     */
    public static final String AND_STRING = " AND ";
    public static final Token AND_TOKEN = new Token(TokenType.BINARYBOOL, AND_STRING);
    public static final String OR_STRING = " OR ";
    public static final Token OR_TOKEN = new Token(TokenType.BINARYBOOL, OR_STRING);
    public static final String NOT_STRING = "NOT ";
    public static final Token NOT_TOKEN = new Token(TokenType.UNARYBOOL, NOT_STRING);
    public static final String LEFT_PAREN_STRING = "(";
    public static final Token LEFT_PAREN_TOKEN = new Token(TokenType.LEFTPARENTHESES, LEFT_PAREN_STRING);
    public static final String RIGHT_PAREN_STRING = ")";
    public static final Token RIGHT_PAREN_TOKEN = new Token(TokenType.RIGHTPARENTHESES, RIGHT_PAREN_STRING);
    public static final String GREATER_STRING = ">";
    public static final Token GREATER_TOKEN = new Token(TokenType.COMPARATOR, GREATER_STRING);
    public static final String LESS_STRING = "<";
    public static final Token LESS_TOKEN = new Token(TokenType.COMPARATOR, LESS_STRING);
    public static final String EQUALS_STRING = "=";
    public static final Token EQUALS_TOKEN = new Token(TokenType.COMPARATOR, EQUALS_STRING);
    public static final String PREFIX_AMOUNT_STRING = "a/";
    public static final Token PREFIX_AMOUNT_TOKEN = new Token(TokenType.PREFIX_AMOUNT, PREFIX_AMOUNT_STRING);
    public static final String PREFIX_BOUGHT_STRING = "b/";
    public static final Token PREFIX_BOUGHT_TOKEN = new Token(TokenType.PREFIX_BOUGHT, PREFIX_BOUGHT_STRING);
    public static final String PREFIX_CODE_STRING = "c/";
    public static final Token PREFIX_CODE_TOKEN = new Token(TokenType.PREFIX_CODE, PREFIX_CODE_STRING);
    public static final String PREFIX_HELD_STRING = "h/";
    public static final Token PREFIX_HELD_TOKEN = new Token(TokenType.PREFIX_HELD, PREFIX_HELD_STRING);
    public static final String PREFIX_MADE_STRING = "m/";
    public static final Token PREFIX_MADE_TOKEN = new Token(TokenType.PREFIX_MADE, PREFIX_MADE_STRING);
    public static final String PREFIX_NAME_STRING = "n/";
    public static final Token PREFIX_NAME_TOKEN = new Token(TokenType.PREFIX_NAME, PREFIX_NAME_STRING);
    public static final String PREFIX_PRICE_STRING = "p/";
    public static final Token PREFIX_PRICE_TOKEN = new Token(TokenType.PREFIX_PRICE, PREFIX_PRICE_STRING);
    public static final String PREFIX_SOLD_STRING = "s/";
    public static final Token PREFIX_SOLD_TOKEN = new Token(TokenType.PREFIX_SOLD, PREFIX_SOLD_STRING);
    public static final String PREFIX_TAG_STRING = "t/";
    public static final Token PREFIX_TAG_TOKEN = new Token(TokenType.PREFIX_TAG, PREFIX_TAG_STRING);
    public static final String PREFIX_WORTH_STRING = "w/";
    public static final Token PREFIX_WORTH_TOKEN = new Token(TokenType.PREFIX_WORTH, PREFIX_WORTH_STRING);
    public static final String NUM_STRING = "999";
    public static final Token NUM_TOKEN = new Token(TokenType.NUM, NUM_STRING);
    public static final String DECIMAL_STRING = "9.99";
    public static final Token DECIMAL_TOKEN = new Token(TokenType.DECIMAL, DECIMAL_STRING);
    public static final String STRING_ONE_STRING = "TESTINGONE";
    public static final Token STRING_ONE_TOKEN = new Token(TokenType.STRING, STRING_ONE_STRING);
    public static final String STRING_TWO_STRING = "TESTINGTWO";
    public static final Token STRING_TWO_TOKEN = new Token(TokenType.STRING, STRING_TWO_STRING);
    public static final String STRING_THREE_STRING = "TESTINGTHREE";
    public static final Token STRING_THREE_TOKEN = new Token(TokenType.STRING, STRING_THREE_STRING);
    public static final String SLASH_STRING = "/";
    public static final Token SLASH_TOKEN = new Token(TokenType.SLASH, SLASH_STRING);
    public static final Token EOF_TOKEN = new Token(TokenType.EOF, "");
    public static final Token WHITESPACE_TOKEN = new Token(TokenType.WHITESPACE, " ");

    /**
     * Tokens with all possible combinations of 3 tags, for truth table testing when evaluating composed tag conditions.
     */

    public static final Coin COIN_0 = new CoinBuilder().withName("COIN ZERO")
            .withTags().build();
    public static final Coin COIN_1 = new CoinBuilder().withName("COIN ONE")
            .withTags(STRING_THREE_STRING).build();
    public static final Coin COIN_2 = new CoinBuilder().withName("COIN TWO")
            .withTags(STRING_TWO_STRING).build();
    public static final Coin COIN_3 = new CoinBuilder().withName("COIN THREE")
            .withTags(STRING_TWO_STRING, STRING_THREE_STRING).build();
    public static final Coin COIN_4 = new CoinBuilder().withName("COIN FOUR")
            .withTags(STRING_ONE_STRING).build();
    public static final Coin COIN_5 = new CoinBuilder().withName("COIN FIVE")
            .withTags(STRING_ONE_STRING, STRING_THREE_STRING).build();
    public static final Coin COIN_6 = new CoinBuilder().withName("COIN SIX")
            .withTags(STRING_ONE_STRING, STRING_TWO_STRING).build();
    public static final Coin COIN_7 = new CoinBuilder().withName("COIN SEVEN")
            .withTags(STRING_ONE_STRING, STRING_TWO_STRING, STRING_THREE_STRING).build();
```
