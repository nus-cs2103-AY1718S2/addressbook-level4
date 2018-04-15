// @@author kush1509
package seedu.address.model.skill;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.JobBuilder;

public class JobSkillContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        JobSkillContainsKeywordsPredicate firstPredicate =
                new JobSkillContainsKeywordsPredicate(firstPredicateKeywordList);
        JobSkillContainsKeywordsPredicate secondPredicate =
                new JobSkillContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobSkillContainsKeywordsPredicate firstPredicateCopy =
                new JobSkillContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different job -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_skillContainsKeywords_returnsTrue() {
        // One keyword
        JobSkillContainsKeywordsPredicate predicate =
                new JobSkillContainsKeywordsPredicate(Collections.singletonList("developer"));
        assertTrue(predicate.test(new JobBuilder().withSkills("developer", "geek").build()));

        // Multiple keywords
        predicate = new JobSkillContainsKeywordsPredicate(Arrays.asList("developer", "geek"));
        assertTrue(predicate.test(new JobBuilder().withSkills("developer", "geek").build()));

        // Only one matching keyword
        predicate = new JobSkillContainsKeywordsPredicate(Arrays.asList("developer", "accountant"));
        assertTrue(predicate.test(new JobBuilder().withSkills("accountant", "manager").build()));

        // Mixed-case keywords
        predicate = new JobSkillContainsKeywordsPredicate(Arrays.asList("deVeloper", "acCounTant"));
        assertTrue(predicate.test(new JobBuilder().withSkills("developer", "accountant").build()));
    }

    @Test
    public void test_positionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        JobSkillContainsKeywordsPredicate predicate =
                new JobSkillContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new JobBuilder().withSkills("developer").build()));

        // Non-matching keyword
        predicate = new JobSkillContainsKeywordsPredicate(Arrays.asList("projectmanager"));
        assertFalse(predicate.test(new JobBuilder().withSkills("developer", "designer").build()));

        // Keywords match position, team, location and number of positions, but does not match skill
        predicate = new JobSkillContainsKeywordsPredicate(
                Arrays.asList("Engineer", "Hardware", "2", "Main", "Street"));
        assertFalse(predicate.test(new JobBuilder().withSkills("developer").withPosition("Engineer")
                .withTeam("Hardware").withLocation("Main Street").withNumberOfPositions("2").build()));
    }
}
