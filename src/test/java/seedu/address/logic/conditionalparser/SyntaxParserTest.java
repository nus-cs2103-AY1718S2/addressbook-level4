//@@author Eldon-Chung
package seedu.address.logic.conditionalparser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.parser.Token;
import seedu.address.logic.parser.TokenStack;
import seedu.address.logic.parser.TokenType;

public class SyntaxParserTest {
    private static final Token BINARYBOOL = new Token(TokenType.BINARYBOOL, "");
    private static final Token UNARYBOOL = new Token(TokenType.UNARYBOOL, "");
    private static final Token LEFTPARENTHESES = new Token(TokenType.LEFTPARENTHESES, "");
    private static final Token RIGHTPARENTHESES = new Token(TokenType.RIGHTPARENTHESES, "");
    private static final Token COMPARATOR = new Token(TokenType.COMPARATOR, "");
    private static final Token PREFIXAMOUNT = new Token(TokenType.PREFIXAMOUNT, "");
    private static final Token NUM = new Token(TokenType.NUM, "");
    private static final Token STRING = new Token(TokenType.STRING, "");
    private static final Token SLASH = new Token(TokenType.SLASH, "");
    private static final Token WHITESPACE = new Token(TokenType.WHITESPACE, "");
    private static final Token NEWLINE = new Token(TokenType.NEWLINE, "");
    private static final Token ELSE = new Token(TokenType.ELSE, "");
    private static final Token EOF = new Token(TokenType.EOF, "");

    private SyntaxParser syntaxParser;
    private TokenStack tokenStack;


    private static SyntaxParser initParser(Token... tokens) {
        return new SyntaxParser(new TokenStack(new ArrayList<Token>(Arrays.asList(tokens))));
    }

    @Test
    public void parseCond() {
        syntaxParser = initParser(PREFIXAMOUNT, STRING, EOF);
        assertTrue(syntaxParser.cond());
        syntaxParser = initParser(PREFIXAMOUNT, COMPARATOR, COMPARATOR, NUM, EOF);
        assertTrue(syntaxParser.cond());
        syntaxParser = initParser(PREFIXAMOUNT, COMPARATOR, NUM, EOF);
        assertTrue(syntaxParser.cond());
        syntaxParser = initParser(STRING, EOF);
        assertFalse(syntaxParser.cond());
    }

    @Test
    public void term() {
        syntaxParser = initParser(PREFIXAMOUNT, STRING, EOF);
        assertTrue(syntaxParser.term());
        syntaxParser = initParser(UNARYBOOL, PREFIXAMOUNT, STRING, EOF);
        assertTrue(syntaxParser.term());
        syntaxParser = initParser(LEFTPARENTHESES, PREFIXAMOUNT,
                STRING, RIGHTPARENTHESES, EOF);
        assertTrue(syntaxParser.term());
        syntaxParser = initParser(LEFTPARENTHESES, STRING, EOF);
        assertFalse(syntaxParser.term());
    }

    @Test
    public void expression() {
        syntaxParser = initParser(PREFIXAMOUNT, STRING, EOF);
        assertTrue(syntaxParser.expression());
        syntaxParser = initParser(UNARYBOOL, PREFIXAMOUNT, STRING, EOF);
        assertTrue(syntaxParser.expression());
        syntaxParser = initParser(PREFIXAMOUNT, STRING, BINARYBOOL, PREFIXAMOUNT, STRING, EOF);
        assertTrue(syntaxParser.expression());
        syntaxParser = initParser(PREFIXAMOUNT, STRING, BINARYBOOL, LEFTPARENTHESES,
                PREFIXAMOUNT, STRING, BINARYBOOL, UNARYBOOL, PREFIXAMOUNT, STRING,
                RIGHTPARENTHESES, EOF);
        assertTrue(syntaxParser.expression());
        syntaxParser = initParser(PREFIXAMOUNT, STRING, BINARYBOOL, EOF);
        assertFalse(syntaxParser.expression());
    }

    @Test
    public void parse() {
        syntaxParser = initParser(PREFIXAMOUNT, STRING, PREFIXAMOUNT, EOF);
        assertFalse(syntaxParser.parse());
        syntaxParser = initParser(PREFIXAMOUNT, STRING, BINARYBOOL, LEFTPARENTHESES,
                PREFIXAMOUNT, STRING, BINARYBOOL, UNARYBOOL, PREFIXAMOUNT, STRING,
                RIGHTPARENTHESES, RIGHTPARENTHESES, EOF);
        assertFalse(syntaxParser.parse());
        syntaxParser = initParser(LEFTPARENTHESES, PREFIXAMOUNT, STRING, BINARYBOOL,
                PREFIXAMOUNT, COMPARATOR, NUM, RIGHTPARENTHESES, BINARYBOOL, PREFIXAMOUNT,
                COMPARATOR, NUM, EOF);
    }
}
