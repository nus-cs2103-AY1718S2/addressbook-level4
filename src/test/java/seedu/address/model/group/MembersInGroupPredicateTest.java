//@@author jas5469
package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TypicalGroups;
import seedu.address.testutil.TypicalPersons;

public class MembersInGroupPredicateTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Group groupTest = TypicalGroups.GROUP_F;

    @Test
    public void test_groupDoesNotContainsMember_returnsFalse() {
        // Test Carl exist in group F
        MembersInGroupPredicate predicate = new MembersInGroupPredicate(groupTest);
        assertFalse(predicate.test(TypicalPersons.CARL));
    }

}
