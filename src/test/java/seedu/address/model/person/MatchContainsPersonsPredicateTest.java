package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalClients.BENSON;
import static seedu.address.testutil.TypicalClients.CARL;

import org.junit.Test;

import seedu.address.testutil.ClientBuilder;

//@@author Zhu-Jiahui
public class MatchContainsPersonsPredicateTest {

    @Test
    public void equals() {
        Client firstTestClient = BENSON;
        Client secondTestClient = CARL;

        MatchContainsPersonsPredicate firstPredicate =
                new MatchContainsPersonsPredicate(firstTestClient);
        MatchContainsPersonsPredicate secondPredicate =
                new MatchContainsPersonsPredicate(secondTestClient);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MatchContainsPersonsPredicate firstPredicateCopy =
                new MatchContainsPersonsPredicate(firstTestClient);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchContainsPersons_returnsTrue() {

        MatchContainsPersonsPredicate predicate =
                new MatchContainsPersonsPredicate(BENSON);
        assertTrue(predicate.test(new ClientBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
                .withCategory("s").build()));

    }

    @Test
    public void test_matchDoesNotContainPersons_returnsFalse() {

        MatchContainsPersonsPredicate predicate =
                new MatchContainsPersonsPredicate(BENSON);
        assertFalse(predicate.test(new ClientBuilder().withName("Benson")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
                .withCategory("s").build()));
    }
}
