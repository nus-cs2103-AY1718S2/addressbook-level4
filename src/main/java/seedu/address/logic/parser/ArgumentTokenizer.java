//@@author Eldon-Chung

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

    //@@author Eldon-Chung
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
    //@@author

    /**
     * Tokenizes an argument string and returns an {@code ArgumentMultimap} object that maps prefixes to their
     * respective argument values. Only the given prefixes will be recognized in the arguments string.
     *
     * @param argString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixTokenTypes   prefix Tokens to tokenize the arguments string with
     * @return           ArgumentMultimap object that maps prefixes to their arguments
     */
    public static ArgumentMultimap tokenizeToArgumentMultimap(String argString, TokenType... prefixTokenTypes) {
        List<Token> tokenList = lex(argString, prefixTokenTypes);
        List<PrefixTokenPosition> prefixTokenPositionList = findAllTokenTypePositions(tokenList, prefixTokenTypes);

        return mapPrefixToArguments(tokenList, prefixTokenPositionList);
    }

    /**
     * Tokenizes an argument string and returns a {@code List<Token>} object that is a list of all the tokens.
     * Whitespace between strings will be ignored.
     *
     * @param argString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixTokenTypes   prefix Tokens to tokenize the arguments string with
     * @return           {@code List<Token>} object contains a list of all the tokens
     */
    public static TokenStack tokenizeToTokenStack(String argString, TokenType... prefixTokenTypes) {
        return new TokenStack(lex(argString, prefixTokenTypes));
    }

    /**
     * Extracts prefixes and their argument values, and returns an {@code ArgumentMultimap} object that maps the
     * extracted prefixes to their respective arguments. Prefixes are extracted based on their zero-based positions in
     * {@code argsString}.
     *
     * @param tokenList  TokenList representing a String of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixTokenPositionList Zero-based positions of all prefixes in {@code argsString}
     * @return                ArgumentMultimap object that maps prefixes to their arguments
     */
    private static ArgumentMultimap mapPrefixToArguments(List<Token> tokenList,
                                                     List<PrefixTokenPosition> prefixTokenPositionList) {
        ArgumentMultimap argMultiMap = new ArgumentMultimap();

        // Sort the prefixTokenPositionList so that the prefixes that come first in the tokenList appear first
        prefixTokenPositionList.sort((prefixToken1, prefixToken2) ->
                prefixToken1.getStartPosition() - prefixToken2.getStartPosition()
        );

        // Add a dummy to the end of the list, since we need to access two elements in succession each time
        prefixTokenPositionList.add(new PrefixTokenPosition(TokenType.EOF, tokenList.size()));

        // Extract the beginning strings for the first prefix token, if any
        String preambleString = extractPreambleString(tokenList, prefixTokenPositionList.get(0));
        argMultiMap.put(TokenType.STRING, preambleString);

        // Map all prefixes to their arguments
        for (int posListIdx = 0; posListIdx < prefixTokenPositionList.size() - 1; posListIdx++) {
            PrefixTokenPosition currentPrefixTokenPosition = prefixTokenPositionList.get(posListIdx);
            PrefixTokenPosition nextPrefixTokenPosition = prefixTokenPositionList.get(posListIdx + 1);
            String argumentString = extractArgumentsToString(tokenList, currentPrefixTokenPosition,
                    nextPrefixTokenPosition);
            argMultiMap.put(currentPrefixTokenPosition.getTokenType(), argumentString);
        }

        return argMultiMap;
    }

    //@@author Eldon-Chung
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
    //@@author

    /**
     * Finds all zero-based prefix token positions in the given arguments string.
     *
     * @param tokenList tokenList representing the string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixTokenTypes  prefix TokenTypes to find in the arguments string
     * @return           List of zero-based prefix positions in the given arguments string
     */
    private static List<PrefixTokenPosition> findAllTokenTypePositions(List<Token> tokenList,
                                                                       TokenType... prefixTokenTypes) {
        List<PrefixTokenPosition> positions = new ArrayList<>();

        for (TokenType prefix : prefixTokenTypes) {
            positions.addAll(findTokenTypePositions(tokenList, prefix));
        }

        return positions;
    }

    /**
     * {@see findAllTokenTypePositions}
     */
    private static List<PrefixTokenPosition> findTokenTypePositions(List<Token> tokenList, TokenType prefixTokenType) {
        List<PrefixTokenPosition> positions = new ArrayList<>();

        int prefixPosition = findTokenTypePosition(tokenList, prefixTokenType, 0);
        while (prefixPosition != -1) {
            PrefixTokenPosition extendedTokenType = new PrefixTokenPosition(prefixTokenType, prefixPosition);
            positions.add(extendedTokenType);
            prefixPosition = findTokenTypePosition(tokenList, prefixTokenType, prefixPosition + 1);
        }

        return positions;
    }

    /**
     * Returns the index of the first occurrence of {@code prefixTokenType} in
     * {@code tokenList} starting from index {@code fromIndex}. Returns -1 if no
     * such occurrence can be found.
     */
    private static int findTokenTypePosition(List<Token> tokenList, TokenType prefixTokenType, int fromIndex) {
        for (int index = fromIndex; index < tokenList.size(); index++) {
            if (tokenList.get(index).hasType(prefixTokenType)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Represents a prefix token's position in an arguments string.
     */
    private static class PrefixTokenPosition {
        private int startPosition;
        private final TokenType prefixTokenType;

        PrefixTokenPosition(TokenType prefixTokenType, int startPosition) {
            this.prefixTokenType = prefixTokenType;
            this.startPosition = startPosition;
        }

        int getStartPosition() {
            return this.startPosition;
        }

        TokenType getTokenType() {
            return this.prefixTokenType;
        }
    }


}
