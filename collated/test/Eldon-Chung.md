# Eldon-Chung
###### \java\seedu\address\logic\conditionalparser\SyntaxParserTest.java
``` java
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
```
###### \java\seedu\address\logic\parser\BuyCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.TokenType.PREFIXAMOUNT;
import static seedu.address.logic.parser.TokenType.PREFIXNAME;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandTarget;

public class BuyCommandParserTest {
    private static final String INDEX_AS_STRING = "1";
    private static final String INT_AS_STRING = "50";
    private static final String FLOAT_AS_STRING = "50.01";
    private static final String INVALID_VALUE_AS_STRING = "asd";

    private BuyCommandParser parser = new BuyCommandParser();

    private BuyCommand constructBuyCommand(String indexAsString, String valueAsString) throws IllegalValueException {
        return new BuyCommand(new CommandTarget(ParserUtil.parseIndex(indexAsString)),
                ParserUtil.parseDouble(valueAsString));
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
        String commandString = buildCommandString(INDEX_AS_STRING, PREFIXAMOUNT.toString(), INT_AS_STRING);
        Command command = constructBuyCommand(INDEX_AS_STRING, INT_AS_STRING);
        assertParseSuccess(parser, commandString, command);
        commandString = buildCommandString(INDEX_AS_STRING, PREFIXAMOUNT.toString(), FLOAT_AS_STRING);
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
        commandString = buildCommandString(INDEX_AS_STRING, PREFIXAMOUNT.toString());
        assertParseFailure(parser, commandString, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE);

        // invalid prefix
        String commandString = buildCommandString(INDEX_AS_STRING, PREFIXNAME.toString(), INDEX_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        // invalid value
        commandString = buildCommandString(INDEX_AS_STRING, PREFIXNAME.toString(), INVALID_VALUE_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        // empty preamble
        commandString = buildCommandString(PREFIXNAME.toString(), INT_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\TokenStackTest.java
``` java
package seedu.address.logic.parser;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.TokenType.NUM;
import static seedu.address.logic.parser.TokenType.STRING;
import static seedu.address.testutil.TestUtil.NUM_TOKEN;
import static seedu.address.testutil.TestUtil.STRING_TOKEN;
import static seedu.address.testutil.TestUtil.WHITESPACE_TOKEN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TokenStackTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TokenStack initTokenStack(Token... tokens) {
        return new TokenStack(new ArrayList<Token>(Arrays.asList(tokens)));
    }

    @Test
    public void tokenStackConstructorRemovesWhiteSpace() {
        TokenStack tokenStack = initTokenStack(STRING_TOKEN, NUM_TOKEN);
        assertEquals(Arrays.asList(STRING_TOKEN, NUM_TOKEN), tokenStack.getTokenList());
        tokenStack = initTokenStack(STRING_TOKEN, WHITESPACE_TOKEN, NUM_TOKEN);
        assertEquals(Arrays.asList(STRING_TOKEN, NUM_TOKEN), tokenStack.getTokenList());
    }

    @Test
    public void matchAndPopTokenType_returnsTrueFalse() throws Exception {
        TokenStack tokenStack;
        tokenStack = initTokenStack(STRING_TOKEN);
        assertTrue(tokenStack.matchAndPopTokenType(STRING));
        tokenStack = initTokenStack(STRING_TOKEN, NUM_TOKEN);
        assertTrue(tokenStack.matchAndPopTokenType(STRING));
        assertTrue(tokenStack.matchAndPopTokenType(NUM));
        tokenStack = initTokenStack(STRING_TOKEN);
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
        tokenStack = initTokenStack(STRING_TOKEN);
        tokenStack.matchAndPopTokenType(STRING);
        tokenStack.matchAndPopTokenType(STRING);
    }

    @Test
    public void matchTokenType_returnsTrueFalse() throws Exception {
        TokenStack tokenStack;
        tokenStack = initTokenStack(STRING_TOKEN);
        assertTrue(tokenStack.matchTokenType(STRING));
        tokenStack = initTokenStack(STRING_TOKEN, NUM_TOKEN);
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
        TokenStack tokenStack = initTokenStack(STRING_TOKEN);
        assertEquals(STRING_TOKEN, tokenStack.popToken());
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
        TokenStack tokenStack = initTokenStack(STRING_TOKEN);
        assertEquals(STRING_TOKEN, tokenStack.peekToken());
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
        TokenStack tokenStack = initTokenStack(STRING_TOKEN, NUM_TOKEN, STRING_TOKEN, STRING_TOKEN, NUM_TOKEN);
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
        tokenStack = initTokenStack(STRING_TOKEN);
        tokenStack.popToken();
        assertTrue(tokenStack.isEmpty());
    }

    @Test
    public void getLastExpectedType() throws Exception {
        TokenStack tokenStack = initTokenStack(STRING_TOKEN);
        assertNull(tokenStack.getLastExpectedType());
        tokenStack.matchAndPopTokenType(STRING);
        assertEquals(STRING, tokenStack.getLastExpectedType());
        tokenStack = initTokenStack(STRING_TOKEN, NUM_TOKEN);
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
        TokenStack tokenStack = initTokenStack(STRING_TOKEN);
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
