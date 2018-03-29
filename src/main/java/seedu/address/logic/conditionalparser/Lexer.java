//@@author Eldon-Chung

package seedu.address.logic.conditionalparser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;


/**
 * Contains the lexical analysis method to tokenize input strings into a list of {@code Token}.
 */
public class Lexer {

    private static final int REGEX_STRING_PATTERN_OFFSET = 1;
    private static final String CAPTURING_GROUP_REGEX_PATTERN = "|(?<%s>%s)";
    private Pattern pattern;

    public Lexer() {
        StringBuilder regexPatternBuffer = new StringBuilder();
        for (TokenType type : TokenType.values()) {
            regexPatternBuffer.append(String.format(CAPTURING_GROUP_REGEX_PATTERN, type.name(), type.regex));
        }

        this.pattern = Pattern.compile(regexPatternBuffer.substring(REGEX_STRING_PATTERN_OFFSET));
    }

    /**
     * Lexically analyses and tokenizes a string of arguments based on the {@code TokenType} specification.
     * @return a list of {@code Token} based on the argument string provided in reverse order.
     */

    public TokenStack lex(String args) {
        ArrayList<Token> tokenList = new ArrayList<Token>();
        Matcher m = pattern.matcher(args);
        while (m.find()) {
            for (TokenType type : TokenType.values()) {
                if (m.group(type.name()) == null || type == TokenType.WHITESPACE) {
                    continue;
                }

                tokenList.add(new Token(type, m.group(type.name())));
            }
        }
        return new TokenStack(Lists.reverse(tokenList));
    }

}
