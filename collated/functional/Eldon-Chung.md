# Eldon-Chung
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public FindCommand() {
        ;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult("Command acknowledged! Results are still a WIP");
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof FindCommand); // short circuit if same class
    }
}
```
###### \java\seedu\address\logic\conditionalparser\SyntaxParser.java
``` java
package seedu.address.logic.conditionalparser;

import seedu.address.logic.parser.TokenStack;
import seedu.address.logic.parser.TokenType;

/**
 * Parses tokenized boolean logic statements to verify correctness
 */
public class SyntaxParser {

    private TokenStack tokenStack;

    public SyntaxParser(TokenStack tokenStack) {
        this.tokenStack = tokenStack;
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
        tokenStack.matchAndPopTokenType(TokenType.COMPARATOR);

        return (tokenStack.matchAndPopTokenType(TokenType.NUM) || tokenStack.matchAndPopTokenType(TokenType.STRING));
    }

    /**
     * Checks if the top of the tokenStack is currently a prefix TokenType.
     */
    private boolean isPrefix() {
        if (!tokenStack.isEmpty() && TokenType.isPrefixType(tokenStack.getActualType())) {
            tokenStack.popToken();
            return true;
        }
        return false;
    }

}
```
###### \java\seedu\address\logic\parser\ArgumentTokenizer.java
``` java

package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizes arguments string of the form: {@code preamble <prefix>value <prefix>value ...}<br>
 *     e.g. {@code some preamble text t/ 11.00 t/12.00 k/ m/ July}  where prefixes are {@code t/ k/ m/}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code k/} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. An argument may be repeated and all its values will be accumulated e.g. the value of {@code t/}
 *    in the above example.<br>
 */
public class ArgumentTokenizer {

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
        return String.format(CAPTURING_GROUP_REGEX_PATTERN, type.name(), type.regex);
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
                if (m.group(type.name()) == null) {
                    continue;
                }
                tokenList.add(new Token(type, m.group(type.name())));
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
    BINARYBOOL("( OR | AND )"),
    UNARYBOOL("NOT "),
    LEFTPARENTHESES("\\("),
    RIGHTPARENTHESES("\\)"),
    COMPARATOR("(>|=|<)+"),
    PREFIXAMOUNT("a/"),
    PREFIXNAME("n/"),
    PREFIXPROFIT("p/"),
    PREFIXEMAIL("e/"),
    PREFIXTAG("t/"),
    DECIMAL("[0-9]+\\.[0-9]+"),
    NUM("[1-9][0-9]*"),
    STRING("[A-Za-z\\^\\-\\@\\./]+"),
    SLASH("/"),
    WHITESPACE("\\s"),
    NEWLINE("\\n"),
    ELSE(".+"),
    EOF("[^\\w\\W]");

    final String regex;

    TokenType(final String regex) {
        this.regex = regex;
    }

    public String toString() {
        return this.regex;
    }

    /**
     * Checks if the {@code type} is a prefix type
     * @param type the type to be checked
     */
    public static boolean isPrefixType(TokenType type) {
        return type == PREFIXAMOUNT
                || type == PREFIXNAME
                || type == PREFIXPROFIT
                || type == PREFIXEMAIL
                || type == PREFIXTAG;
    }
}
```
