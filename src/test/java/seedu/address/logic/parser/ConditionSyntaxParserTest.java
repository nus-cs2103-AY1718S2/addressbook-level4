package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

//@@author Eldon-Chung
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
