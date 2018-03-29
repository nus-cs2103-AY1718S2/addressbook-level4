//@@author Eldon-Chung
package seedu.address.logic.conditionalparser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.collect.Lists;

public class SyntaxParserTest {
    private static final Token BINARYBOOL = new Token(TokenType.BINARYBOOL, "");
    private static final Token UNARYBOOL = new Token(TokenType.UNARYBOOL, "");
    private static final Token LEFTPARENTHESES = new Token(TokenType.LEFTPARENTHESES, "");
    private static final Token RIGHTPARENTHESES = new Token(TokenType.RIGHTPARENTHESES, "");
    private static final Token COMPARATOR = new Token(TokenType.COMPARATOR, "");
    private static final Token SPECIFIER = new Token(TokenType.SPECIFIER, "");
    private static final Token NUM = new Token(TokenType.NUM, "");
    private static final Token STRING = new Token(TokenType.STRING, "");
    private static final Token SLASH = new Token(TokenType.SLASH, "");
    private static final Token WHITESPACE = new Token(TokenType.WHITESPACE, "");
    private static final Token NEWLINE = new Token(TokenType.NEWLINE, "");
    private static final Token ELSE = new Token(TokenType.ELSE, "");

    private SyntaxParser syntaxParser;
    private TokenStack tokenStack;


    private static SyntaxParser initParser(Token... tokens) {
        return new SyntaxParser(new TokenStack(Lists.reverse(Arrays.asList(tokens))));
    }

    @Test
    public void parseCond() {
        syntaxParser = initParser(SPECIFIER, STRING);
        assertTrue(syntaxParser.cond());
        syntaxParser = initParser(SPECIFIER, COMPARATOR, COMPARATOR, NUM);
        assertTrue(syntaxParser.cond());
        syntaxParser = initParser(SPECIFIER, COMPARATOR, NUM);
        assertTrue(syntaxParser.cond());
        syntaxParser = initParser(STRING);
        assertFalse(syntaxParser.cond());
    }

    @Test
    public void term() {
        syntaxParser = initParser(SPECIFIER, STRING);
        assertTrue(syntaxParser.term());
        syntaxParser = initParser(UNARYBOOL, SPECIFIER, STRING);
        assertTrue(syntaxParser.term());
        syntaxParser = initParser(LEFTPARENTHESES, SPECIFIER,
                STRING, RIGHTPARENTHESES);
        assertTrue(syntaxParser.term());
        syntaxParser = initParser(LEFTPARENTHESES, STRING);
        assertFalse(syntaxParser.term());
    }

    @Test
    public void expression() {
        syntaxParser = initParser(SPECIFIER, STRING);
        assertTrue(syntaxParser.expression());
        syntaxParser = initParser(UNARYBOOL, SPECIFIER, STRING);
        assertTrue(syntaxParser.expression());
        syntaxParser = initParser(SPECIFIER, STRING, BINARYBOOL, SPECIFIER, STRING);
        assertTrue(syntaxParser.expression());
        syntaxParser = initParser(SPECIFIER, STRING, BINARYBOOL, LEFTPARENTHESES,
                SPECIFIER, STRING, BINARYBOOL, UNARYBOOL, SPECIFIER, STRING,
                RIGHTPARENTHESES);
        assertTrue(syntaxParser.expression());
        syntaxParser = initParser(SPECIFIER, STRING, BINARYBOOL);
        assertFalse(syntaxParser.expression());
    }

    @Test
    public void parse() {
        syntaxParser = initParser(SPECIFIER, STRING, SPECIFIER);
        assertFalse(syntaxParser.parse());
        syntaxParser = initParser(SPECIFIER, STRING, BINARYBOOL, LEFTPARENTHESES,
                SPECIFIER, STRING, BINARYBOOL, UNARYBOOL, SPECIFIER, STRING,
                RIGHTPARENTHESES, RIGHTPARENTHESES);
        assertFalse(syntaxParser.parse());
        syntaxParser = initParser(LEFTPARENTHESES, SPECIFIER, STRING, BINARYBOOL,
                SPECIFIER, COMPARATOR, NUM, RIGHTPARENTHESES, BINARYBOOL, SPECIFIER,
                COMPARATOR, NUM);
    }
}
