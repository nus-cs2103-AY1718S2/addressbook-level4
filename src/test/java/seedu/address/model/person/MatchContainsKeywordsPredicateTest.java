package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalClients.BENSON;
import static seedu.address.testutil.TypicalClients.CARL;

import org.junit.Test;

import seedu.address.testutil.ClientBuilder;


//@@author Zhu-Jiahui

public class MatchContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Client firstTestClient = BENSON;
        Client secondTestClient = CARL;

        MatchContainsKeywordsPredicate firstPredicate =
                new MatchContainsKeywordsPredicate(firstTestClient);
        MatchContainsKeywordsPredicate secondPredicate =
                new MatchContainsKeywordsPredicate(secondTestClient);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MatchContainsKeywordsPredicate firstPredicateCopy =
                new MatchContainsKeywordsPredicate(firstTestClient);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchContainsKeywords_returnsTrue() {

        //all matches
        MatchContainsKeywordsPredicate predicate =
                new MatchContainsKeywordsPredicate(BENSON);
        assertTrue(predicate.test(new ClientBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
                .withCategory("s").build()));

        //some matches
        predicate = new MatchContainsKeywordsPredicate(BENSON);
        assertTrue(predicate.test(new ClientBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765431")
                .withTags("owesMoney", "friends").withLocation("south").withGrade("s2").withSubject("physics")
                .withCategory("s").build()));

        //location, grade subject all matches but the rest of the attributes dont match
        predicate = new MatchContainsKeywordsPredicate(BENSON);
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("85355255")
                .withTags("friends").withLocation("north").withGrade("s2").withSubject("physics")
                .withCategory("s").build()));

        //1 match
        predicate = new MatchContainsKeywordsPredicate(BENSON);
        assertTrue(predicate.test(new ClientBuilder().withName("Benson Meier").build()));

    }

    @Test
    public void test_matchDoesNotContainKeywords_returnsFalse() {

        //grade, subject and location no match
        MatchContainsKeywordsPredicate predicate = new MatchContainsKeywordsPredicate(BENSON);
        assertFalse(predicate.test(new ClientBuilder().withName("Benson")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").withLocation("south").withGrade("s1").withSubject("chemistry")
                .withCategory("s").build()));

    }
}

