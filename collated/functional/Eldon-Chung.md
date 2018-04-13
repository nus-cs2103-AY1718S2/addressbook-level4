# Eldon-Chung
###### \java\seedu\address\commons\core\CoinSubredditList.java
``` java
//Citation included here because .json files cannot be commented on.
//the list of subreddits CoinCodeToSubreddit.json was partially
//obtained from https://github.com/kendricktan/cryptoshitposting/blob/master/data/subreddits.json
public class CoinSubredditList {

    private static final Map<String, String> COIN_CODE_TO_SUBREDDIT_MAP = new HashMap<>();
    private static final String COIN_CODE_TO_SUBREDDIT_FILEPATH = "/coins/CoinCodeToSubreddit.json";
    private static final String REDDIT_URL = "https://www.reddit.com/r/";
    private static final String CODE_ATTRIBUTE_NAME = "code";
    private static final String SUBREDDIT_ATTRIBUTE_NAME = "subreddit";

    public static boolean isRecognized(Coin coin) {
        return COIN_CODE_TO_SUBREDDIT_MAP.containsKey(coin.getCode().toString());
    }

    /**
     * Obtains the subreddit url associated with the {@code coin}.
     * @param coin
     * @return the subreddit url associated with the {@code coin}
     */
    public static String getRedditUrl(Coin coin) {
        return REDDIT_URL + COIN_CODE_TO_SUBREDDIT_MAP.get(coin.getCode().toString());
    }

    /**
     * Initialises the CoinToSubredditList to store existing Coin subreddits
     * @throws FileNotFoundException if the file missing
     */
    public static void initialize() throws FileNotFoundException, URISyntaxException {
        InputStreamReader fileReader = new InputStreamReader(getCoinCodeToSubredditFilepath());
        JsonArray jsonArray = parseFileToJsonObj(fileReader);
        JsonElement codeString;
        JsonElement subredditName;

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            codeString = jsonObject.get(CODE_ATTRIBUTE_NAME);
            subredditName = jsonObject.get(SUBREDDIT_ATTRIBUTE_NAME);
            if (subredditName == null || subredditName.toString().equals("null")) {
                continue;
            }
            COIN_CODE_TO_SUBREDDIT_MAP.put(codeString.getAsString(), subredditName.getAsString());
        }
    }

    private static InputStream getCoinCodeToSubredditFilepath() throws URISyntaxException {
        return MainApp.class.getResourceAsStream(COIN_CODE_TO_SUBREDDIT_FILEPATH);
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public FindCommand(Predicate<Coin> coinCondition) {
        this.coinCondition = coinCondition;
    }

    @Override
    public boolean equals(Object other) {
        /*
         * Note: there isn't a good way to evaluate equality.
         * There are ways around it, but it is not clear whether those drastic measures are needed.
         * So we will always return false instead.
         */
        return false;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredCoinList(coinCondition);
        return new CommandResult(getMessageForCoinListShownSummary(model.getFilteredCoinList().size()));
    }
```
###### \java\seedu\address\logic\conditions\AmountCondition.java
``` java
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
```
###### \java\seedu\address\logic\conditions\AmountHeldCondition.java
``` java
/**
 * Represents a predicate that evaluates to true when the amount held of a {@Coin} is either greater than or less than
 * (depending on the amount comparator) the amount specified.
 */
public class AmountHeldCondition extends AmountCondition {

    public static final TokenType PREFIX = PREFIX_HELD;
    public static final TokenType PARAMETER_TYPE = NUM;

    public AmountHeldCondition(Amount amount, BiPredicate<Amount, Amount> amountComparator, CompareMode compareMode) {
        super(amount, amountComparator, compareMode);
    }

    @Override
    public boolean test(Coin coin) {
        return amountComparator.test(coin.getCurrentAmountHeld(), amount);
    }
}
```
###### \java\seedu\address\logic\conditions\CodeCondition.java
``` java
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
```
###### \java\seedu\address\logic\conditions\CurrentPriceCondition.java
``` java
/**
 * Represents a predicate that evaluates to true when the price of a {@Coin} is either greater than or less than
 * (depending on the amount comparator) the amount specified.
 */
public class CurrentPriceCondition extends AmountCondition {

    public static final TokenType PREFIX = PREFIX_PRICE;
    public static final TokenType PARAMETER_TYPE = NUM;


    public CurrentPriceCondition(Amount amount, BiPredicate<Amount, Amount> amountComparator, CompareMode compareMode) {
        super(amount, amountComparator, compareMode);
    }

    @Override
    public boolean test(Coin coin) {
        return amountComparator.test(new Amount(coin.getPrice().getCurrent()), amount);
    }
}
```
###### \java\seedu\address\logic\conditions\DollarsBoughtCondition.java
``` java
/**
 * Represents a predicate that evaluates to true when the amount bought of a {@Coin} is either greater than or less than
 * (depending on the amount comparator) the amount specified.
 */
public class DollarsBoughtCondition extends AmountCondition {

    public static final TokenType PREFIX = PREFIX_BOUGHT;
    public static final TokenType PARAMETER_TYPE = NUM;

    public DollarsBoughtCondition(Amount amount,
                                  BiPredicate<Amount, Amount> amountComparator,
                                  CompareMode compareMode) {
        super(amount, amountComparator, compareMode);
    }

    @Override
    public boolean test(Coin coin) {
        return amountComparator.test(coin.getTotalAmountBought(), amount);
    }
}
```
###### \java\seedu\address\logic\conditions\DollarsSoldCondition.java
``` java
/**
 * Represents a predicate that evaluates to true when the amount bought of a {@Coin} is either greater than or less than
 * (depending on the amount comparator) the amount specified.
 */
public class DollarsSoldCondition extends AmountCondition  {

    public static final TokenType PREFIX = PREFIX_SOLD;
    public static final TokenType PARAMETER_TYPE = NUM;

    public DollarsSoldCondition(Amount amount, BiPredicate<Amount, Amount> amountComparator, CompareMode compareMode) {
        super(amount, amountComparator, compareMode);
    }

    @Override
    public boolean test(Coin coin) {
        return amountComparator.test(coin.getTotalAmountSold(), amount);
    }
}
```
###### \java\seedu\address\logic\conditions\MadeCondition.java
``` java
/**
 * Represents a predicate that evaluates to true when the amount made (dollar profit) of a {@Coin} is either
 * greater than or less than (depending on the amount comparator) the amount specified.
 */
public class MadeCondition extends AmountCondition  {

    public static final TokenType PREFIX = PREFIX_MADE;
    public static final TokenType PARAMETER_TYPE = NUM;

    public MadeCondition(Amount amount, BiPredicate<Amount, Amount> amountComparator, CompareMode compareMode) {
        super(amount, amountComparator, compareMode);
    }

    @Override
    public boolean test(Coin coin) {
        return amountComparator.test(coin.getTotalProfit(), amount);
    }
}
```
###### \java\seedu\address\logic\conditions\TagCondition.java
``` java
/**
 * Represents a predicate that evaluates to true when a {@Coin} contains the {@tag} specified.
 */
public class TagCondition implements Predicate<Coin> {

    public static final TokenType PREFIX = PREFIX_TAG;
    public static final TokenType PARAMETER_TYPE = STRING;

    private Tag tag;

    public TagCondition(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Coin coin) {
        return coin.getTags().contains(tag);
    }
}
```
###### \java\seedu\address\logic\conditions\WorthCondition.java
``` java
/**
 * Represents a predicate that evaluates to true when the worth of a {@Coin} is either greater than or less than
 * (depending on the amount comparator) the amount specified.
 */
public class WorthCondition extends AmountCondition  {

    public static final TokenType PREFIX = PREFIX_WORTH;

    public WorthCondition(Amount amount, BiPredicate<Amount, Amount> amountComparator, CompareMode compareMode) {
        super(amount, amountComparator, compareMode);
    }

    @Override
    public boolean test(Coin coin) {
        return amountComparator.test(coin.getDollarsWorth(), amount);
    }
}
```
###### \java\seedu\address\logic\parser\ArgumentTokenizer.java
``` java
    private static final int STRING_BUILDER_OFFSET = 1;
    private static final String CAPTURING_GROUP_REGEX_PATTERN = "|(?<%s>%s)";
    private static final TokenType[] DEFAULT_TOKEN_TYPES = {
        TokenType.BINARYBOOL, TokenType.UNARYBOOL, TokenType.LEFTPARENTHESES, TokenType.RIGHTPARENTHESES,
        TokenType.COMPARATOR, TokenType.DECIMAL, TokenType.NUM, TokenType.STRING, TokenType.SLASH,
        TokenType.WHITESPACE, TokenType.NEWLINE, TokenType.ELSE
    };

    private static String getCapturingGroupRegexPatternFromTokenType(TokenType type) {
        return String.format(CAPTURING_GROUP_REGEX_PATTERN, type.typeName, type.regex);
    }

    private static String getDefaultRegexPatternString() {
        StringBuilder regexPatternBuffer = new StringBuilder();
        for (TokenType defaultTokenType : DEFAULT_TOKEN_TYPES) {
            regexPatternBuffer.append(getCapturingGroupRegexPatternFromTokenType(defaultTokenType));
        }
        return regexPatternBuffer.toString();
    }

    private static String getPatternString(TokenType... tokenTypes) {
        StringBuilder regexPatternBuffer = new StringBuilder();
        for (TokenType type : tokenTypes) {
            regexPatternBuffer.append(getCapturingGroupRegexPatternFromTokenType(type));
        }
        regexPatternBuffer.append(getDefaultRegexPatternString());
        return regexPatternBuffer.substring(STRING_BUILDER_OFFSET);
    }

    private static Pattern buildPatternFromTokenTypes(TokenType... tokenTypes) {
        return Pattern.compile(getPatternString(tokenTypes));
    }

    private static List<TokenType> getTokenTypeList(TokenType... tokenTypes) {
        ArrayList<TokenType> tokenTypeList = new ArrayList<TokenType>();
        tokenTypeList.addAll(Arrays.asList(tokenTypes));
        tokenTypeList.addAll(Arrays.asList(DEFAULT_TOKEN_TYPES));
        return tokenTypeList;
    }

    /**
     * Lexically analyses and tokenizes a string of arguments based on the {@code TokenType} specification.
     * @return a list of {@code Token} based on the argument string provided in reverse order.
     */
    private static List<Token> lex(String args, TokenType... prefixTokenTypes) {

        List<TokenType> typeList = getTokenTypeList(prefixTokenTypes);
        List<Token> tokenList = new ArrayList<Token>();
        Pattern pattern = buildPatternFromTokenTypes(prefixTokenTypes);
        Matcher m = pattern.matcher(args);
        while (m.find()) {
            for (TokenType type : typeList) {
                if (m.group(type.typeName) == null) {
                    continue;
                }
                tokenList.add(new Token(type, m.group(type.typeName)));
            }
        }
        // Add in an EOF type Token as a delimiter.
        tokenList.add(new Token(TokenType.EOF, ""));
        return tokenList;
    }
```
###### \java\seedu\address\logic\parser\ArgumentTokenizer.java
``` java
    private static String extractPreambleString(List<Token> tokenList, PrefixTokenPosition currentPrefixToken) {
        return extractArgumentsToString(tokenList,
                new PrefixTokenPosition(TokenType.STRING, -1),
                currentPrefixToken);
    }

    /**
     * Returns the trimmed value of the argument in the arguments string specified by {@code currentPrefixToken}.
     * The end position of the value is determined by {@code nextPrefixToken}.
     */
    private static String extractArgumentsToString(List<Token> tokenList, PrefixTokenPosition currentPrefixToken,
                                                  PrefixTokenPosition nextPrefixToken) {
        List<Token> subTokenList = tokenList.subList(currentPrefixToken.getStartPosition() + 1,
                nextPrefixToken.getStartPosition());
        return listOfTokensToString(subTokenList);
    }

    /**
     * Returns a String representation of a list of tokens with one space in between each token's string value.
     */
    private static String listOfTokensToString(List<Token> tokenList) {

        StringBuilder stringBuilder = new StringBuilder();
        for (Token token : tokenList) {
            stringBuilder.append(String.format("%s", token.getPattern()));
        }
        return stringBuilder.toString().trim();
    }
```
###### \java\seedu\address\logic\parser\ConditionGenerator.java
``` java
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
            return new AmountHeldCondition(specifiedAmount, amountComparator, compareMode);

        case PREFIX_SOLD:
        case PREFIX_SOLD_RISE:
        case PREFIX_SOLD_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new DollarsSoldCondition(specifiedAmount, amountComparator, compareMode);

        case PREFIX_BOUGHT:
        case PREFIX_BOUGHT_RISE:
        case PREFIX_BOUGHT_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new DollarsBoughtCondition(specifiedAmount, amountComparator, compareMode);

        case PREFIX_MADE:
        case PREFIX_MADE_RISE:
        case PREFIX_MADE_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new MadeCondition(specifiedAmount, amountComparator, compareMode);

        case PREFIX_PRICE:
        case PREFIX_PRICE_RISE:
        case PREFIX_PRICE_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new CurrentPriceCondition(specifiedAmount, amountComparator, compareMode);

        case PREFIX_WORTH:
        case PREFIX_WORTH_RISE:
        case PREFIX_WORTH_FALL:
            amountComparator = getAmountComparatorFromToken(tokenStack.popToken());
            specifiedAmount = ParserUtil.parseAmount(tokenStack.popToken().getPattern());
            return new WorthCondition(specifiedAmount, amountComparator, compareMode);

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
        case PREFIX_HELD:
        case PREFIX_SOLD:
        case PREFIX_BOUGHT:
        case PREFIX_MADE:
        case PREFIX_PRICE:
        case PREFIX_WORTH:
            return CompareMode.EQUALS;

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
```
###### \java\seedu\address\logic\parser\ConditionSemanticParser.java
``` java
/**
 * Parses tokenized boolean logic statements to verify correctness
 */
public class ConditionSemanticParser {

    private TokenStack tokenStack;

    public ConditionSemanticParser(TokenStack tokenStack) {
        this.tokenStack = tokenStack;
        this.tokenStack.resetStack();
    }

    public TokenType getExpectedType() {
        return this.tokenStack.getLastExpectedType();
    }

    public TokenType getActualType() {
        return this.tokenStack.getActualType();
    }

    /**
     * Parses the input as a token stack semantically.
     * @return true if the input is semantically valid.
     */
    public boolean parse() {
        while (!tokenStack.isEmpty()) {
            TokenType peekType = tokenStack.popToken().getType();
            if (TokenType.isPrefixType(peekType) && !hasCorrectParameterType(peekType)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks to see each the prefix type is followed by the appropriate parameter types.
     * @param type
     * @return true if the prefix type is followed by the appropriate parameter types.
     */
    private boolean hasCorrectParameterType(TokenType type) {
        switch (type) {
        case PREFIX_HELD:
        case PREFIX_HELD_RISE:
        case PREFIX_HELD_FALL:
        case PREFIX_SOLD:
        case PREFIX_SOLD_RISE:
        case PREFIX_SOLD_FALL:
        case PREFIX_BOUGHT:
        case PREFIX_BOUGHT_RISE:
        case PREFIX_BOUGHT_FALL:
        case PREFIX_MADE:
        case PREFIX_MADE_RISE:
        case PREFIX_MADE_FALL:
        case PREFIX_PRICE:
        case PREFIX_PRICE_RISE:
        case PREFIX_PRICE_FALL:
        case PREFIX_WORTH:
        case PREFIX_WORTH_RISE:
        case PREFIX_WORTH_FALL:
            return hasNumericalParameter();
        case PREFIX_CODE:
        case PREFIX_TAG:
            return hasStringParameter();
        default:
            return false;
        }
    }

    /**
     * Checks if the next two tokens are a comparator followed by a number.
     * @return true if the next two tokens are a comparator followed by a number.
     */
    private boolean hasNumericalParameter() {
        return tokenStack.matchAndPopTokenType(TokenType.COMPARATOR)
                && (tokenStack.matchAndPopTokenType(TokenType.NUM)
                || tokenStack.matchAndPopTokenType(TokenType.DECIMAL));

    }

    /**
     * Checks if the next next token is a string.
     * @return true if the next token is a string.
     */
    private boolean hasStringParameter() {
        return tokenStack.matchAndPopTokenType(TokenType.STRING);
    }


}
```
###### \java\seedu\address\logic\parser\ConditionSyntaxParser.java
``` java
public class ConditionSyntaxParser {

    private TokenStack tokenStack;

    public ConditionSyntaxParser(TokenStack tokenStack) {
        this.tokenStack = tokenStack;
        this.tokenStack.resetStack();
    }

    public TokenType getExpectedType() {
        return this.tokenStack.getLastExpectedType();
    }

    public TokenType getActualType() {
        return this.tokenStack.getActualType();
    }

    /**
     * Parses the token stack against the boolean logic grammar loaded into the tokenStack
     * @return correctness of the tokenStack
     */
    public boolean parse() {
        return expression()
                && tokenStack.matchAndPopTokenType(TokenType.EOF);
    }

    /**
     * Matches the tokenStack against the expression grammar rule
     * @return true if the tokenStack was correct
     */
    boolean expression() {
        if (!term()) {
            return false;
        }
        while (tokenStack.matchAndPopTokenType(TokenType.BINARYBOOL)) {
            if (!term()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Matches the tokenStack against the term grammar rule
     * @return true if the tokenStack was correct
     */
    boolean term() {

        if (tokenStack.matchAndPopTokenType(TokenType.LEFTPARENTHESES)) {
            if (!expression()) {
                return false;
            }
            return tokenStack.matchAndPopTokenType(TokenType.RIGHTPARENTHESES);
        } else if (tokenStack.matchAndPopTokenType(TokenType.UNARYBOOL)) {
            return term();
        }
        return cond();
    }

    /**
     * Matches the tokenStack against the cond grammar rule
     * @return true if the tokenStack was correct
     */
    boolean cond() {
        if (!isPrefix()) {
            return false;
        }

        tokenStack.matchAndPopTokenType(TokenType.COMPARATOR);

        return tokenStack.matchAndPopTokenType(TokenType.NUM)
                || tokenStack.matchAndPopTokenType(TokenType.STRING)
                || tokenStack.matchAndPopTokenType(TokenType.DECIMAL);
    }

    /**
     * Checks if the top of the tokenStack is currently a prefix TokenType.
     */
    private boolean isPrefix() {
        if (!tokenStack.isEmpty() && TokenType.isPrefixType(tokenStack.getActualType())) {
            tokenStack.popToken();
            return true;
        }
        // If it was not a PREFIX, we do this to set the last expected type to some PREFIX type.
        tokenStack.matchTokenType(TokenType.PREFIX_AMOUNT);
        return false;
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String condition} represented by a {@code TokenStack} into a {@code Predicate<Coin>}.
     * @param argumentTokenStack a {@code TokenStack} representing the tokenized argument.
     * @return a predicate representing the argument
     * @throws IllegalValueException if the given tag names or numbers as parameters are invalid
     *          and if the argument is either syntactically or semantically invalid.
     */
    public static Predicate<Coin> parseCondition(TokenStack argumentTokenStack)
            throws IllegalValueException {
        requireNonNull(argumentTokenStack);
        TokenType expectedTokenType;
        TokenType actualTokenType;

        ConditionSyntaxParser conditionSyntaxParser = new ConditionSyntaxParser(argumentTokenStack);
        if (!conditionSyntaxParser.parse()) {
            expectedTokenType = conditionSyntaxParser.getExpectedType();
            actualTokenType = conditionSyntaxParser.getActualType();
            logger.warning(String.format(MESSAGE_CONDITION_ARGUMENT_INVALID_SYNTAX, "Syntactic",
                    expectedTokenType.description, actualTokenType.description));
            throw new ParseException("command arguments invalid.");
        }

        ConditionSemanticParser conditionSemanticParser = new ConditionSemanticParser(argumentTokenStack);
        if (!conditionSemanticParser.parse()) {
            expectedTokenType = conditionSemanticParser.getExpectedType();
            actualTokenType = conditionSemanticParser.getActualType();
            logger.warning(String.format(MESSAGE_CONDITION_ARGUMENT_INVALID_SYNTAX, "Semantic",
                    expectedTokenType.description, actualTokenType.description));
            throw new ParseException("command arguments invalid.");
        }

        ConditionGenerator conditionGenerator = new ConditionGenerator(argumentTokenStack);
        return conditionGenerator.generate();
    }
    //author@@
}
```
###### \java\seedu\address\logic\parser\Token.java
``` java
package seedu.address.logic.parser;

/**
 * Represents the token type that that portions of the string can be grouped into.
 */
public class Token {
    private TokenType type;
    private String pattern;

    public Token(TokenType type, String pattern) {
        this.type = type;
        this.pattern = pattern;
    }

    public TokenType getType() {
        return this.type;
    }

    public boolean hasType(TokenType type) {
        return this.type == type;
    }

    public String getPattern() {
        return this.pattern;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", pattern, this.type.name());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Token)) {
            return false;
        }

        Token otherToken = (Token) other;
        return otherToken.getType().equals(this.type)
                && otherToken.getPattern().equals(this.pattern);

    }
}
```
###### \java\seedu\address\logic\parser\TokenStack.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.parser.TokenType.WHITESPACE;

import java.util.EmptyStackException;
import java.util.List;


/**
 * Represents stack of Token objects.
 */
public class TokenStack {
    private List<Token> tokenList;
    private TokenType lastExpectedType;
    private int tokenHeadIndex;

    public TokenStack(List<Token> tokenList) {
        tokenList.removeIf(t -> t.hasType(WHITESPACE));
        this.tokenList = tokenList;
        tokenHeadIndex = 0;
        lastExpectedType = null;
    }

    /**
     * Matches the type with the top token on the tokenList and pops it if they are they same.
     * @param type the TokenType to compare the top token with.
     * @return true if the types are the same, false if either the stack is expended or they are not the same type
     */
    public boolean matchAndPopTokenType(TokenType type) throws EmptyStackException {
        lastExpectedType = type;
        if (tokenHeadIndex >= tokenList.size()) {
            throw new EmptyStackException();
        }
        if (tokenList.get(tokenHeadIndex).getType() == type) {
            tokenHeadIndex++;
            return true;
        }
        return false;
    }

    /**
     * Matches the type with the top token on the tokenList.
     * @param type the TokenType to compare the top token with.
     * @return true if the types are the same.
     * @throws EmptyStackException if the stack is empty
     */
    public boolean matchTokenType(TokenType type) throws EmptyStackException {
        lastExpectedType = type;
        if (tokenHeadIndex >= tokenList.size()) {
            throw new EmptyStackException();
        }
        return tokenList.get(tokenHeadIndex).getType() == type;
    }

    /**
     * @return the {@code Token} at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public Token popToken() throws EmptyStackException {
        if (tokenHeadIndex >= tokenList.size()) {
            throw new EmptyStackException();
        }
        return tokenList.get(tokenHeadIndex++);
    }

    /**
     * Returns the Token at the top of the stack without removing it
     * @return the Token at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public Token peekToken() throws EmptyStackException {
        if (tokenHeadIndex >= tokenList.size()) {
            throw new EmptyStackException();
        }
        return tokenList.get(tokenHeadIndex);
    }

    /**
     * Resets the {@code TokenStack} to restore all the popped Tokens
     */
    public void resetStack() {
        tokenHeadIndex = 0;
    }

    public boolean isEmpty() {
        return tokenHeadIndex == tokenList.size();
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    /**
     * @return The last TokenType that was checked.
     */
    public TokenType getLastExpectedType() {
        return lastExpectedType;
    }

    /**
     * @return The TokenType of token currently on top of the stack.
     * @throws EmptyStackException if the stack is empty.
     */
    public TokenType getActualType() throws EmptyStackException {
        if (tokenHeadIndex >= tokenList.size()) {
            throw new EmptyStackException();
        }
        return tokenList.get(tokenHeadIndex).getType();
    }
}
```
###### \java\seedu\address\logic\parser\TokenType.java
``` java
package seedu.address.logic.parser;

/**
 * Represents the possible types a token can take, along with the regular expression it is specified by.
 */
public enum TokenType {
    /* Boolean Logic Operators */
    BINARYBOOL(" OR | AND ", "BINARYBOOL", "a boolean operator"),
    UNARYBOOL("NOT ", "UNARYBOOL", "a NOT operator"),
    LEFTPARENTHESES("\\(", "LEFTPARENTHESES", "a left parentheses"),
    RIGHTPARENTHESES("\\)", "RIGHTPARENTHESES", "a left parentheses"),
    COMPARATOR(">|=|<", "COMPARATOR", "a comparator, e.g. >"),

    /* String values */
    PREFIX_CODE("c/", "CPREFIX", "a prefix"),
    PREFIX_NAME("n/", "NPREFIX", "a prefix"),
    PREFIX_TAG("t/", "TPREFIX", "a prefix"),

    /* Numerical values */
    PREFIX_AMOUNT("a/", "APREFIX", "a prefix"),
    // Below used for find/notify conditions
    PREFIX_BOUGHT_RISE("b/\\+", "BRPREFIX", "a prefix"),
    PREFIX_BOUGHT_FALL("b/\\-", "BFPREFIX", "a prefix"),
    PREFIX_BOUGHT("b/", "BPREFIX", "a prefix"),
    PREFIX_HELD_RISE("h/\\+", "HRPREFIX", "a prefix"),
    PREFIX_HELD_FALL("h/\\-", "HFPREFIX", "a prefix"),
    PREFIX_HELD("h/", "HPREFIX", "a prefix"),
    PREFIX_MADE_RISE("m/\\+", "MRPREFIX", "a prefix"),
    PREFIX_MADE_FALL("m/\\-", "MFPREFIX", "a prefix"),
    PREFIX_MADE("m/", "MPREFIX", "a prefix"),
    PREFIX_PRICE_RISE("p/\\+", "PRPREFIX", "a prefix"),
    PREFIX_PRICE_FALL("p/\\-", "PFPREFIX", "a prefix"),
    PREFIX_PRICE("p/", "PPREFIX", "a prefix"),
    PREFIX_SOLD_RISE("s/\\+", "SRPREFIX", "a prefix"),
    PREFIX_SOLD_FALL("s/\\-", "SFPREFIX", "a prefix"),
    PREFIX_SOLD("s/", "SPREFIX", "a prefix"),
    PREFIX_WORTH_RISE("w/\\+", "WRPREFIX", "a prefix"),
    PREFIX_WORTH_FALL("w/\\-", "WFPREFIX", "a prefix"),
    PREFIX_WORTH("w/", "WPREFIX", "a prefix"),

    /* Value components */
    DECIMAL("\\-?[0-9]+\\.[0-9]+", "DECIMAL", "a decimal number"),
    NUM("\\-?[0-9]+", "NUM", "an integer"),
    STRING("[A-Za-z\\^\\-\\@\\./]+", "STRING", "a string"),
    SLASH("/", "SLASH", "a slash"),
    WHITESPACE("\\s", "WHITESPACE", "some white space"),
    NEWLINE("\\n", "NEWLINE", "a newline"),
    ELSE(".+", "ELSE", "some unknown character"),
    EOF("[^\\w\\W]", "EOF", "some end of the argument");

    final String typeName;
    final String regex;
    final String description;

    TokenType(final String regex, final String typeName, final String description) {
        this.regex = regex;
        this.typeName = typeName;
        this.description = description;
    }

    public String toString() {
        return this.regex;
    }

    /**
     * Checks if the {@code type} is a prefix type
     * @param type the type to be checked
     */
    public static boolean isPrefixType(TokenType type) {
        return type == PREFIX_AMOUNT
                || type == PREFIX_BOUGHT
                || type == PREFIX_CODE
                || type == PREFIX_HELD
                || type == PREFIX_MADE
                || type == PREFIX_NAME
                || type == PREFIX_PRICE
                || type == PREFIX_SOLD
                || type == PREFIX_TAG
                || type == PREFIX_WORTH;
    }
}
```
