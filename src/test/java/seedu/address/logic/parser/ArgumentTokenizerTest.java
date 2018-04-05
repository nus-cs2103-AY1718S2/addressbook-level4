package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TestUtil.AND_STRING;
import static seedu.address.testutil.TestUtil.AND_TOKEN;
import static seedu.address.testutil.TestUtil.EOF_TOKEN;
import static seedu.address.testutil.TestUtil.EQUALS_STRING;
import static seedu.address.testutil.TestUtil.EQUALS_TOKEN;
import static seedu.address.testutil.TestUtil.GREATER_STRING;
import static seedu.address.testutil.TestUtil.GREATER_TOKEN;
import static seedu.address.testutil.TestUtil.LEFT_PAREN_STRING;
import static seedu.address.testutil.TestUtil.LEFT_PAREN_TOKEN;
import static seedu.address.testutil.TestUtil.LESS_STRING;
import static seedu.address.testutil.TestUtil.LESS_TOKEN;
import static seedu.address.testutil.TestUtil.NOT_STRING;
import static seedu.address.testutil.TestUtil.NOT_TOKEN;
import static seedu.address.testutil.TestUtil.NUM_STRING;
import static seedu.address.testutil.TestUtil.NUM_TOKEN;
import static seedu.address.testutil.TestUtil.OR_STRING;
import static seedu.address.testutil.TestUtil.OR_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_STRING;
import static seedu.address.testutil.TestUtil.PREFIX_TOKEN;
import static seedu.address.testutil.TestUtil.RIGHT_PARENT_TOKEN;
import static seedu.address.testutil.TestUtil.RIGHT_PAREN_STRING;
import static seedu.address.testutil.TestUtil.STRING_STRING;
import static seedu.address.testutil.TestUtil.STRING_TOKEN;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class ArgumentTokenizerTest {

    private final TokenType aSlash = TokenType.PREFIXAMOUNT;
    private final TokenType pSlash = TokenType.PREFIXPROFIT;
    private final TokenType tSlash = TokenType.PREFIXTAG;

    @Test
    public void tokenizeToArgumentMultimap_emptyArgsString_noValues() {
        String argsString = "  ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeToArgumentMultimap(argsString, aSlash);

        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, aSlash);
    }

    private void assertPreamblePresent(ArgumentMultimap argMultimap, String expectedPreamble) {
        assertEquals(expectedPreamble, argMultimap.getPreamble());
    }

    private void assertPreambleEmpty(ArgumentMultimap argMultimap) {
        assertTrue(argMultimap.getPreamble().isEmpty());
    }

    /**
     * Asserts all the arguments in {@code argMultimap} with {@code prefixTokenType} match the {@code expectedValues}
     * and only the last value is returned upon calling {@code ArgumentMultimap#getValue(Prefix)}.
     */
    private void assertArgumentPresent(ArgumentMultimap argMultimap, TokenType prefixTokenType,
                                       String... expectedValues) {

        // Verify the last value is returned
        assertEquals(expectedValues[expectedValues.length - 1], argMultimap.getValue(prefixTokenType).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argMultimap.getAllValues(prefixTokenType).size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argMultimap.getAllValues(prefixTokenType).get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentMultimap argMultimap, TokenType prefix) {
        assertFalse(argMultimap.getValue(prefix).isPresent());
    }

    @Test
    public void tokenizeToArgumentMultimap_noPrefixes_allTakenAsPreamble() {
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeToArgumentMultimap(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
        assertPreamblePresent(argMultimap, argsString.trim());

    }

    @Test
    public void tokenizeToArgumentMultimap_oneArgument() {
        // Preamble present
        String argsString = "  Some preamble string a/ Argument value ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeToArgumentMultimap(argsString, aSlash);
        assertPreamblePresent(argMultimap, "Some preamble string");
        assertArgumentPresent(argMultimap, aSlash, "Argument value");

        // No preamble
        argsString = " p/   Argument value ";
        argMultimap = ArgumentTokenizer.tokenizeToArgumentMultimap(argsString, pSlash);
        assertPreambleEmpty(argMultimap);
        assertArgumentPresent(argMultimap, pSlash, "Argument value");

    }

    @Test
    public void tokenizeToArgumentMultimap_multipleArguments() {
        // Only two arguments are present
        String argsString = "SomePreambleString t/ dashp/tValue a/aSlash value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeToArgumentMultimap(argsString, aSlash, tSlash, pSlash);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, aSlash, "aSlash value");
        assertArgumentPresent(argMultimap, tSlash, "dashp/tValue");
        assertArgumentAbsent(argMultimap, pSlash);

        // All three arguments are present
        argsString = "Different Preamble String p/111 t/ dashT-Value a/aSlash value";
        argMultimap = ArgumentTokenizer.tokenizeToArgumentMultimap(argsString, aSlash, tSlash, pSlash);
        assertPreamblePresent(argMultimap, "Different Preamble String");
        assertArgumentPresent(argMultimap, aSlash, "aSlash value");
        assertArgumentPresent(argMultimap, tSlash, "dashT-Value");
        assertArgumentPresent(argMultimap, pSlash, "111");

        /* Also covers: Reusing of the tokenizeToArgumentMultimapr multiple times */

        // Reuse tokenizeToArgumentMultimap on an empty string to ensure ArgumentMultimap is correctly reset
        // (i.e. no stale values from the previous tokenizing remain)
        argsString = "";
        argMultimap = ArgumentTokenizer.tokenizeToArgumentMultimap(argsString, aSlash, tSlash, pSlash);
        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, aSlash);
    }

    @Test
    public void tokenizeToArgumentMultimap_multipleArgumentsWithRepeats() {
        // Two arguments repeated, some have empty values
        String argsString = "SomePreambleString t/ dashT-Value p/ p/ t/ another dashT-value a/ aSlash value t/";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeToArgumentMultimap(argsString, aSlash, tSlash, pSlash);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, aSlash, "aSlash value");
        assertArgumentPresent(argMultimap, tSlash, "dashT-Value", "another dashT-value", "");
        assertArgumentPresent(argMultimap, pSlash, "", "");
    }

    @Test
    public void tokenizeToArgumentMultimap_multipleArgumentsJoined() {
        String argsString = "SomePreambleStringa/ aSlash joinedt/joined t/ not joinedp/joined";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeToArgumentMultimap(argsString, aSlash, tSlash, pSlash);
        assertPreamblePresent(argMultimap, "SomePreambleStringa/ aSlash joinedt/joined");
        assertArgumentAbsent(argMultimap, aSlash);
        assertArgumentPresent(argMultimap, tSlash, "not joinedp/joined");
        assertArgumentAbsent(argMultimap, pSlash);
    }

    @Test
    public void lexBoolType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(AND_TOKEN, EOF_TOKEN));
        assertEquals(expectedList, ArgumentTokenizer.tokenizeToTokenStack(AND_STRING).getTokenList());
        expectedList = new ArrayList<Token>(Arrays.asList(OR_TOKEN, EOF_TOKEN));
        assertEquals(expectedList, ArgumentTokenizer.tokenizeToTokenStack(OR_STRING).getTokenList());
        expectedList = new ArrayList<Token>(Arrays.asList(NOT_TOKEN, EOF_TOKEN));
        assertEquals(expectedList, ArgumentTokenizer.tokenizeToTokenStack(NOT_STRING).getTokenList());
    }

    @Test
    public void lexLeftParenthesesType() throws Exception {
        ArrayList<Token> expectedList;
        expectedList = new ArrayList<Token>(Arrays.asList(LEFT_PAREN_TOKEN, EOF_TOKEN));
        assertEquals(expectedList, ArgumentTokenizer.tokenizeToTokenStack(LEFT_PAREN_STRING).getTokenList());
    }

    @Test
    public void lexRightParenthesesType() throws Exception {
        ArrayList<Token> expectedList;
        expectedList = new ArrayList<Token>(Arrays.asList(RIGHT_PARENT_TOKEN, EOF_TOKEN));
        assertEquals(expectedList, ArgumentTokenizer.tokenizeToTokenStack(RIGHT_PAREN_STRING).getTokenList());
    }

    @Test
    public void lexComparatorType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(GREATER_TOKEN, EOF_TOKEN));
        assertEquals(expectedList, ArgumentTokenizer.tokenizeToTokenStack(GREATER_STRING).getTokenList());
        expectedList = new ArrayList<Token>(Arrays.asList(LESS_TOKEN, EOF_TOKEN));
        assertEquals(expectedList, ArgumentTokenizer.tokenizeToTokenStack(LESS_STRING).getTokenList());
        expectedList = new ArrayList<Token>(Arrays.asList(EQUALS_TOKEN, EOF_TOKEN));
        assertEquals(expectedList, ArgumentTokenizer.tokenizeToTokenStack(EQUALS_STRING).getTokenList());
    }

    @Test
    public void lexPrefixType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(PREFIX_TOKEN, EOF_TOKEN));
        assertEquals(expectedList,
                ArgumentTokenizer.tokenizeToTokenStack(PREFIX_STRING, TokenType.PREFIXAMOUNT).getTokenList());
    }

    @Test
    public void lexNumType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(NUM_TOKEN, EOF_TOKEN));
        assertEquals(expectedList, ArgumentTokenizer.tokenizeToTokenStack(NUM_STRING).getTokenList());
    }

    @Test
    public void lexStringType() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(STRING_TOKEN, EOF_TOKEN));
        assertEquals(expectedList, ArgumentTokenizer.tokenizeToTokenStack(STRING_STRING).getTokenList());
    }

    @Test
    public void lexGenericString() throws Exception {
        ArrayList<Token> expectedList;

        expectedList = new ArrayList<Token>(Arrays.asList(PREFIX_TOKEN, STRING_TOKEN, AND_TOKEN,
                PREFIX_TOKEN, STRING_TOKEN, EOF_TOKEN));
        assertEquals(expectedList,
                ArgumentTokenizer.tokenizeToTokenStack("a/TESTING AND a/TESTING",
                        TokenType.PREFIXAMOUNT).getTokenList());
        expectedList = new ArrayList<Token>(Arrays.asList(PREFIX_TOKEN, LESS_TOKEN, NUM_TOKEN,
                OR_TOKEN, LEFT_PAREN_TOKEN, NOT_TOKEN, PREFIX_TOKEN, GREATER_TOKEN, NUM_TOKEN,
                AND_TOKEN, PREFIX_TOKEN, STRING_TOKEN, RIGHT_PARENT_TOKEN, EOF_TOKEN));
        assertEquals(expectedList,
                ArgumentTokenizer.tokenizeToTokenStack("a/<999 OR (NOT a/>999 AND a/TESTING)",
                        TokenType.PREFIXAMOUNT).getTokenList());
    }

}
