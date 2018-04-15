package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.JobBuilder;
import seedu.address.testutil.PersonBuilder;

//@@author ChengSashankh
public class PersonMatchesJobPredicateTest {
    @Test
    public void equals() {
        Job firstJobPosting = new JobBuilder().withJobTitle("Sample Title").withLocation("Geylang")
                .withSkill("CSS").build();
        Job secondJobPosting = new JobBuilder().withJobTitle("Second Sample").withLocation("Tampines")
                .withSkill("HTML").build();

        PersonMatchesJobPredicate firstPredicate;
        firstPredicate = new PersonMatchesJobPredicate(firstJobPosting);
        PersonMatchesJobPredicate secondPredicate;
        secondPredicate = new PersonMatchesJobPredicate(secondJobPosting);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonMatchesJobPredicate firstPredicateCopy;
        firstPredicateCopy = new PersonMatchesJobPredicate(firstJobPosting);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personMatchesJob_returnsTrue() {
        // One keyword
        PersonMatchesJobPredicate predicate;
        predicate = new PersonMatchesJobPredicate(new JobBuilder().withLocation("Geylang").withSkill("ALL")
                .withTags("ALL").build());
        assertTrue(predicate.test(new PersonBuilder().withAddress("Geylang Street Area").build()));

        predicate = new PersonMatchesJobPredicate(new JobBuilder().withSkill("CSS").withLocation("ALL")
                .withTags("ALL").build());
        assertTrue(predicate.test(new PersonBuilder().withSkills("CSS").build()));

        // Multiple keywords
        predicate = new PersonMatchesJobPredicate(new JobBuilder().withSkill("CSS").withLocation("Geylang")
                .withTags("ALL").build());
        assertTrue(predicate.test(new PersonBuilder().withSkills("CSS").withAddress("Geylang Street Area").build()));

        predicate = new PersonMatchesJobPredicate(new JobBuilder().withSkill("CSS").withLocation("Geylang")
                .withTags("FreshGrad").build());
        assertTrue(predicate.test(new PersonBuilder().withSkills("CSS").withAddress("Geylang Street Area")
                .withTags("FreshGrad").build()));
    }

    @Test
    public void test_personDoesNotMatchJobs_returnsFalse() {
        // Non-matching jobs and candidates
        PersonMatchesJobPredicate predicate = new PersonMatchesJobPredicate(new JobBuilder().withSkill("UnknownSkill")
                .withLocation("Batcave").withTags("UnknownTag").build());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Keywords match location, but does not match skills or tags
        predicate = new PersonMatchesJobPredicate(new JobBuilder().withSkill("UnknownSkill")
                .withLocation("Matching Location").withTags("UnknownTag").build());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Matching Street").withTags("school", "friends")
                .withSkills("Non-matching skills").build()));
    }

}
//@@author
