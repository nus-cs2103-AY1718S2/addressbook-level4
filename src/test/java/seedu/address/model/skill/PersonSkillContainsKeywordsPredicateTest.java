package seedu.address.model.skill;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonSkillContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonSkillContainsKeywordsPredicate firstPredicate =
                new PersonSkillContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonSkillContainsKeywordsPredicate secondPredicate =
                new PersonSkillContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonSkillContainsKeywordsPredicate firstPredicateCopy =
                new PersonSkillContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_skillContainsKeywords_returnsTrue() {
        // One keyword
        PersonSkillContainsKeywordsPredicate predicate =
                new PersonSkillContainsKeywordsPredicate(Collections.singletonList("developer"));
        assertTrue(predicate.test(new PersonBuilder().withSkills("developer", "geek").build()));

        // Multiple keywords
        predicate = new PersonSkillContainsKeywordsPredicate(Arrays.asList("developer", "geek"));
        assertTrue(predicate.test(new PersonBuilder().withSkills("developer", "geek").build()));

        // Only one matching keyword
        predicate = new PersonSkillContainsKeywordsPredicate(Arrays.asList("developer", "accountant"));
        assertTrue(predicate.test(new PersonBuilder().withSkills("accountant", "manager").build()));

        // Mixed-case keywords
        predicate = new PersonSkillContainsKeywordsPredicate(Arrays.asList("deVeloper", "acCounTant"));
        assertTrue(predicate.test(new PersonBuilder().withSkills("developer", "accountant").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonSkillContainsKeywordsPredicate predicate =
                new PersonSkillContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSkills("developer").build()));

        // Non-matching keyword
        predicate = new PersonSkillContainsKeywordsPredicate(Arrays.asList("projectmanager"));
        assertFalse(predicate.test(new PersonBuilder().withSkills("developer", "designer").build()));

        // Keywords match name, phone, email and address, but does not match skill
        predicate = new PersonSkillContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@company.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withSkills("developer").withPhone("12345")
                .withEmail("alice@company.com").withAddress("Main Street").withName("Alice").build()));
    }
}
