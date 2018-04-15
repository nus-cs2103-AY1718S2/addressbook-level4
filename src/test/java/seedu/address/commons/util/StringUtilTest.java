package seedu.address.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;

public class StringUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //---------------- Tests for isUnsignedPositiveInteger --------------------------------------

    @Test
    public void isUnsignedPositiveInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0"));  // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    private void assertExceptionThrown(Class<? extends Throwable> exceptionClass, String sentence, String word,
            Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsWordIgnoreCase(sentence, word);
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                   containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.getDetails(null);
    }

    //@@author yeggasd
    //---------------- Tests for isOddEven --------------------------------------

    @Test
    public void isOddEven() {

        // EP: empty strings
        assertFalse(StringUtil.isOddEven("")); // Boundary value
        assertFalse(StringUtil.isOddEven("  "));

        // EP: odd with white space
        assertFalse(StringUtil.isOddEven(" odd ")); // Leading/trailing spaces
        assertFalse(StringUtil.isOddEven("od d"));  // Spaces in the middle

        // EP: even with white space
        assertFalse(StringUtil.isOddEven(" even ")); // Leading/trailing spaces
        assertFalse(StringUtil.isOddEven("ev en"));  // Spaces in the middle

        // EP: multiple words
        assertFalse(StringUtil.isOddEven("odd even"));
        assertFalse(StringUtil.isOddEven("even asd"));
        assertFalse(StringUtil.isOddEven("odd dsa"));

        // EP: valid odd or even, should return true
        assertTrue(StringUtil.isOddEven("odd"));
        assertTrue(StringUtil.isOddEven("even"));

        //EP: valid odd or even with different upper and lower case, should return true
        assertTrue(StringUtil.isOddEven("OdD"));
        assertTrue(StringUtil.isOddEven("EvEn"));
    }

    //---------------- Tests for getOddEven --------------------------------------

    /*
     * Equivalence Partitions: null, empty String, one word, multiple words, odd, even
     * and with different upper and lower case
     */

    @Test
    public void getOddEven_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.getOddEven(null);
    }

    @Test
    public void getOddEven_invalidStringGiven_nullReturned() {
        assertTrue(StringUtil.getOddEven("") == null);
        assertTrue(StringUtil.getOddEven("word") == null);
        assertTrue(StringUtil.getOddEven("words words") == null);
        assertTrue(StringUtil.getOddEven("odd odd") == null);
    }

    @Test
    public void getOddEven_validStringGiven_correctResult() {
        assertEquals(StringUtil.getOddEven("even"), Index.fromZeroBased(0));
        assertEquals(StringUtil.getOddEven("odd"), Index.fromZeroBased(1));
        assertEquals(StringUtil.getOddEven("eVeN"), Index.fromZeroBased(0));
        assertEquals(StringUtil.getOddEven("oDd"), Index.fromZeroBased(1));

    }

    //---------------- Tests for isDay --------------------------------------

    @Test
    public void isDay() {

        // EP: empty strings
        assertFalse(StringUtil.isDay("")); // Boundary value
        assertFalse(StringUtil.isDay("  "));

        // EP: days with white space
        assertFalse(StringUtil.isDay(" monday ")); // Leading/trailing spaces
        assertFalse(StringUtil.isDay("mon day"));  // Spaces in the middle

        assertFalse(StringUtil.isDay(" tuesday ")); // Leading/trailing spaces
        assertFalse(StringUtil.isDay("tues day"));  // Spaces in the middle

        assertFalse(StringUtil.isDay(" wednesday ")); // Leading/trailing spaces
        assertFalse(StringUtil.isDay("wed day"));  // Spaces in the middle

        assertFalse(StringUtil.isDay(" thursday ")); // Leading/trailing spaces
        assertFalse(StringUtil.isDay("thurs day"));  // Spaces in the middle

        assertFalse(StringUtil.isDay(" friday ")); // Leading/trailing spaces
        assertFalse(StringUtil.isDay("fri day"));  // Spaces in the middle

        // EP: multiple words
        assertFalse(StringUtil.isDay("friday monday"));
        assertFalse(StringUtil.isDay("monday asd"));
        assertFalse(StringUtil.isDay("asd dsa"));

        // EP: valid days, should return true
        assertTrue(StringUtil.isDay("monday"));
        assertTrue(StringUtil.isDay("tuesday"));
        assertTrue(StringUtil.isDay("wednesday"));
        assertTrue(StringUtil.isDay("thursday"));
        assertTrue(StringUtil.isDay("friday"));


        //EP: valid odd or even with different upper and lower case,  should return true
        assertTrue(StringUtil.isDay("MoNdaY"));
        assertTrue(StringUtil.isDay("TueSday"));
        assertTrue(StringUtil.isDay("weDNesDAy"));
        assertTrue(StringUtil.isDay("THURSDAY"));
        assertTrue(StringUtil.isDay("FriDAY"));
    }

    //---------------- Tests for capitalize --------------------------------------

    /*
     * Equivalence Partitions: null, empty String, one letter, one word and one word with multiple cases
     */

    @Test
    public void capitalize_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.capitalize(null);
    }

    @Test
    public void capitalize_emptyStringGiven_throwsIndexOutOfBoundException() {
        thrown.expect(IndexOutOfBoundsException.class);
        StringUtil.capitalize("");
    }

    @Test
    public void capitalize_validStringGiven_correctResult() {
        assertEquals(StringUtil.capitalize("e"), "E");
        assertEquals(StringUtil.capitalize("even"), "Even");
        assertEquals(StringUtil.capitalize("eVeN"), "Even");
    }
}
