package seedu.address.model.person;
//@@author SuxianAlicia
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPreferences.NECKLACES;
import static seedu.address.testutil.TypicalPreferences.SHOES;
import static seedu.address.testutil.TypicalPreferences.VIDEO_GAMES;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PreferencesContainKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Arrays.asList(VIDEO_GAMES.tagName, SHOES.tagName);
        List<String> secondPredicateKeywordList = Collections.singletonList(NECKLACES.tagName);

        PreferencesContainKeywordsPredicate firstPredicate =
                new PreferencesContainKeywordsPredicate(firstPredicateKeywordList);
        PreferencesContainKeywordsPredicate secondPredicate =
                new PreferencesContainKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PreferencesContainKeywordsPredicate firstPredicateCopy =
                new PreferencesContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_preferencesContainsKeywords_returnsTrue() {
        PreferencesContainKeywordsPredicate predicate;

        // One keyword
        predicate = new PreferencesContainKeywordsPredicate(Collections.singletonList(VIDEO_GAMES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withPreferences(VIDEO_GAMES.tagName).build()));

        // Multiple keywords
        predicate = new PreferencesContainKeywordsPredicate(Arrays.asList(NECKLACES.tagName, SHOES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withPreferences(NECKLACES.tagName, SHOES.tagName).build()));

        // Only one matching keyword
        predicate = new PreferencesContainKeywordsPredicate(Arrays.asList(NECKLACES.tagName, SHOES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withPreferences(VIDEO_GAMES.tagName, SHOES.tagName).build()));

        // Mixed-case keywords
        predicate = new PreferencesContainKeywordsPredicate(Arrays.asList("NeCkLaCes", "ShoES"));
        assertTrue(predicate.test(new PersonBuilder().withPreferences(NECKLACES.tagName, SHOES.tagName).build()));
    }

    @Test
    public void test_personPreferencesDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PreferencesContainKeywordsPredicate predicate =
                new PreferencesContainKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPreferences(VIDEO_GAMES.tagName).build()));

        // Non-matching keyword
        predicate = new PreferencesContainKeywordsPredicate(Collections.singletonList(VIDEO_GAMES.tagName));
        assertFalse(predicate.test(new PersonBuilder().withPreferences(SHOES.tagName, NECKLACES.tagName).build()));
    }
}
