package seedu.address.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.util.OverdueTagPredicate;
import seedu.address.testutil.TaskBuilder;

//@@author Kyomian
public class OverdueTagPredicateTest {

    @Test
    public void test_tagContainsOverdue_returnsTrue() {
        // task has only one tag
        OverdueTagPredicate predicate = new OverdueTagPredicate();
        assertTrue(predicate.test(new TaskBuilder().withTags("Overdue").build()));

        // task has multiple tags
        assertTrue(predicate.test(new TaskBuilder().withTags("Overdue", "Important").build()));
    }

    @Test
    public void test_tagContainsNoOverdue_returnsFalse() {
        OverdueTagPredicate predicate = new OverdueTagPredicate();
        assertFalse(predicate.test(new TaskBuilder().withTags("Important").build()));
    }
}
