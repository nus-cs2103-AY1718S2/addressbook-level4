//@@author Eldon-Chung
package seedu.address.logic.conditionalparser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class LexerTest {

    private static final String AND_STRING = " AND ";
    private static final Token AND_TOKEN = new Token(TokenType.BINARYBOOL, AND_STRING);
    private static final String OR_STRING = " OR ";
    private static final Token OR_TOKEN = new Token(TokenType.BINARYBOOL, OR_STRING);
    private static final String NOT_STRING = "NOT ";
    private static final Token NOT_TOKEN = new Token(TokenType.UNARYBOOL, NOT_STRING);
    private static final String LEFT_PAREN_STRING = "(";
    private static final Token LEFT_PAREN_TOKEN = new Token(TokenType.LEFTPARENTHESES, LEFT_PAREN_STRING);
    private static final String RIGHT_PAREN_STRING = ")";
    private static final Token RIGHT_PARENT_TOKEN = new Token(TokenType.RIGHTPARENTHESES, RIGHT_PAREN_STRING);
    private static final String GREATER_STRING = ">";
    private static final Token GREATER_TOKEN = new Token(TokenType.COMPARATOR, GREATER_STRING);
    private static final String LESS_STRING = "<";
    private static final Token LESS_TOKEN = new Token(TokenType.COMPARATOR, LESS_STRING);
    private static final String EQUALS_STRING = "=";
    private static final Token EQUALS_TOKEN = new Token(TokenType.COMPARATOR, EQUALS_STRING);
    private static final String SPECIFIER_STRING = "s/";
    private static final Token SPECIFIER_TOKEN = new Token(TokenType.SPECIFIER, SPECIFIER_STRING);
    private static final String NUM_STRING = "999";
    private static final Token NUM_TOKEN = new Token(TokenType.NUM, NUM_STRING);
    private static final String STRING_STRING = "TESTING";
    private static final Token STRING_TOKEN = new Token(TokenType.STRING, STRING_STRING);
    private static final String SLASH_STRING = "/";
    private static final Token SLASH_TOKEN = new Token(TokenType.SLASH, SLASH_STRING);
    private Lexer lexer;

    @Before
    public void setUp() {
        this.lexer = new Lexer();

    }

    @Test
    public void lexBoolType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(AND_TOKEN));
        assertEquals(expectedList, this.lexer.lex(AND_STRING).getTokenStack());
        expectedList = new ArrayList<Token>(Arrays.asList(OR_TOKEN));
        assertEquals(expectedList, this.lexer.lex(OR_STRING).getTokenStack());
        expectedList = new ArrayList<Token>(Arrays.asList(NOT_TOKEN));
        assertEquals(expectedList, this.lexer.lex(NOT_STRING).getTokenStack());
    }

    @Test
    public void lexLeftParenthesesType() throws Exception {
        ArrayList<Token> expectedList;
        expectedList = new ArrayList<Token>(Arrays.asList(LEFT_PAREN_TOKEN));
        assertEquals(expectedList, this.lexer.lex(LEFT_PAREN_STRING).getTokenStack());
    }

    @Test
    public void lexRightParenthesesType() throws Exception {
        ArrayList<Token> expectedList;
        expectedList = new ArrayList<Token>(Arrays.asList(RIGHT_PARENT_TOKEN));
        assertEquals(expectedList, this.lexer.lex(RIGHT_PAREN_STRING).getTokenStack());
    }

    @Test
    public void lexComparatorType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(GREATER_TOKEN));
        assertEquals(expectedList, this.lexer.lex(GREATER_STRING).getTokenStack());
        expectedList = new ArrayList<Token>(Arrays.asList(LESS_TOKEN));
        assertEquals(expectedList, this.lexer.lex(LESS_STRING).getTokenStack());
        expectedList = new ArrayList<Token>(Arrays.asList(EQUALS_TOKEN));
        assertEquals(expectedList, this.lexer.lex(EQUALS_STRING).getTokenStack());
    }

    @Test
    public void lexSpecifierType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(SPECIFIER_TOKEN));
        assertEquals(expectedList, this.lexer.lex(SPECIFIER_STRING).getTokenStack());
    }

    @Test
    public void lexNumType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(NUM_TOKEN));
        assertEquals(expectedList, this.lexer.lex(NUM_STRING).getTokenStack());
    }

    @Test
    public void lexStringType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(STRING_TOKEN));
        assertEquals(expectedList, this.lexer.lex(STRING_STRING).getTokenStack());
    }

    @Test
    public void lexSlashType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(SLASH_TOKEN));
        assertEquals(expectedList, this.lexer.lex(SLASH_STRING).getTokenStack());
    }


    @Test
    public void lexGenericString() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(SPECIFIER_TOKEN, STRING_TOKEN, AND_TOKEN,
                SPECIFIER_TOKEN, STRING_TOKEN));
        assertEquals(Lists.reverse(expectedList), lexer.lex("s/TESTING AND s/TESTING").getTokenStack());
        expectedList = new ArrayList<Token>(Arrays.asList(SPECIFIER_TOKEN, LESS_TOKEN, NUM_TOKEN,
                OR_TOKEN, LEFT_PAREN_TOKEN, NOT_TOKEN, SPECIFIER_TOKEN, GREATER_TOKEN, NUM_TOKEN,
                AND_TOKEN, SPECIFIER_TOKEN, STRING_TOKEN, RIGHT_PARENT_TOKEN));
        assertEquals(Lists.reverse(expectedList), lexer.lex("s/<999 OR (NOT s/>999 AND s/TESTING)").getTokenStack());
    }
}
