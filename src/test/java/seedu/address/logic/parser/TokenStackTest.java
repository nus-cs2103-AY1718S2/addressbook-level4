//@@author Eldon-Chung
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
