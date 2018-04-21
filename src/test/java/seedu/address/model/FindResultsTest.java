package seedu.address.model;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author tanhengyeow
public class FindResultsTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void containsWordIgnoreCase_validInput_success() {
        assertTrue(FindResults.getInstance()
                .containsWordIgnoreCase("Testing 1 2 3",
                        "Testing", "n/")); // exact word with exact case
        assertTrue(FindResults.getInstance()
                .containsWordIgnoreCase("Testing 1 2 3",
                        "tEsTIng", "n/")); // exact word with mixed case
        assertTrue(FindResults.getInstance()
                .containsWordIgnoreCase("Testing 1 2 3",
                        "  Testing  ", "n/")); // exact word with trailing spaces
        assertTrue(FindResults.getInstance()
                .containsWordIgnoreCase("Testing 1 2 3",
                        "  tEsTIng  ", "n/")); // exact word with mixed case + trailing spaces
    }

    @Test
    public void containsWordIgnoreCase_validInput_failure() {
        assertFalse(FindResults.getInstance()
                .containsWordIgnoreCase("Testing 1 2 3",
                        "test", "n/")); // non matching word

        assertFalse(FindResults.getInstance()
                .containsWordIgnoreCase("Testing 1 2 3",
                        " test    ", "n/")); // non matching word + trailing spaces
    }

    @Test
    public void containsWordIgnoreCase_nullInput_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        FindResults.getInstance()
                .containsWordIgnoreCase("Testing 1 2 3",
                        null, "n/"); // word is null
    }

    @Test
    public void containsFuzzyMatchIgnoreCase_validInput_success() {
        assertTrue(FindResults.getInstance()
                .containsFuzzyMatchIgnoreCase("Testing one two three",
                        "Testi",  "n/", 2)); // one word with exact case is a fuzzy match
        assertTrue(FindResults.getInstance()
                .containsFuzzyMatchIgnoreCase("Testing one two three",
                        "tEsTi", "n/", 2)); // one word with mixed case is a fuzzy match
        assertTrue(FindResults.getInstance()
                .containsFuzzyMatchIgnoreCase("Testing one two three",
                        "  Testi  ", "n/", 2)); // trailing spaces
        assertTrue(FindResults.getInstance()
                .containsFuzzyMatchIgnoreCase("Testing one two three",
                        "  tEsTi  ", "n/", 2)); // trailing spaces
    }

    @Test
    public void containsFuzzyMatchIgnoreCase_validInput_failure() {
        assertFalse(FindResults.getInstance()
                .containsFuzzyMatchIgnoreCase("Testing 1 2 3",
                        "alex", "n/", 2)); // non matching fuzzy match

        assertFalse(FindResults.getInstance()
                .containsFuzzyMatchIgnoreCase("Testing 1 2 3",
                        " alex    ", "n/", 2)); // trailing spaces
    }

    @Test
    public void containsFuzzyMatchIgnoreCase_nullInput_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        FindResults.getInstance()
                .containsFuzzyMatchIgnoreCase("Testing 1 2 3",
                        null, "n/", 2); // word is null
    }

    @Test
    public void containsPrefixIgnoreCase_validInput_success() {
        assertTrue(FindResults.getInstance()
                .containsPrefixIgnoreCase("Testing one two three",
                        "Tes", "n/")); // start with this prefix word + exact case
        assertTrue(FindResults.getInstance()
                .containsPrefixIgnoreCase("Testing one two three",
                        "tEsT", "n/")); // start with this prefix word + mixed case
        assertTrue(FindResults.getInstance()
                .containsPrefixIgnoreCase("Testing one two three",
                        "     Tes  ", "n/")); // trailing spaces
        assertTrue(FindResults.getInstance()
                .containsPrefixIgnoreCase("Testing one two three",
                        "  tEsT  ", "n/")); // trailing spaces
    }

    @Test
    public void containsPrefixIgnoreCase_validInput_failure() {
        assertFalse(FindResults.getInstance()
                .containsPrefixIgnoreCase("Testing 1 2 3",
                        "alex", "n/")); // non matching prefix word

        assertFalse(FindResults.getInstance()
                .containsPrefixIgnoreCase("Testing 1 2 3",
                        " alex    ", "n/")); // trailing spaces
    }

    @Test
    public void containsPrefixIgnoreCase_nullInput_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        FindResults.getInstance()
                .containsPrefixIgnoreCase("Testing 1 2 3",
                        null, "n/"); // word is null
    }

    @Test
    public void containsSuffixIgnoreCase_validInput_success() {
        assertTrue(FindResults.getInstance()
                .containsSuffixIgnoreCase("Testing one two three",
                        "three", "n/")); // end with this suffix word + exact case
        assertTrue(FindResults.getInstance()
                .containsSuffixIgnoreCase("Testing one two three",
                        "tHrEe", "n/")); // end with this suffix word + mixed case
        assertTrue(FindResults.getInstance()
                .containsSuffixIgnoreCase("Testing one two three",
                        "     ree  ", "n/")); // trailing spaces
        assertTrue(FindResults.getInstance()
                .containsSuffixIgnoreCase("Testing one two three",
                        "  ThrEE  ", "n/")); // trailing spaces
    }

    @Test
    public void containsSuffixIgnoreCase_validInput_failure() {
        assertFalse(FindResults.getInstance()
                .containsSuffixIgnoreCase("Testing 1 2 3",
                        "alex", "n/")); // non matching prefix word

        assertFalse(FindResults.getInstance()
                .containsSuffixIgnoreCase("Testing 1 2 3",
                        " alex    ", "n/")); // trailing spaces
    }

    @Test
    public void containsSuffixIgnoreCase_nullInput_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        FindResults.getInstance()
                .containsSuffixIgnoreCase("Testing 1 2 3",
                        null, "n/"); // word is null
    }

}
