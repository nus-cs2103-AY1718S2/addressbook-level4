//@@author jas5469
package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.TypicalGroups;
import seedu.address.testutil.TypicalPersons;

public class MembersInGroupPredicateTest {

    private Group groupTest = TypicalGroups.GROUP_F;

    @Test
    public void test_groupDoesNotContainsMember_returnsFalse() {
        // Test Carl exist in group F
        MembersInGroupPredicate predicate = new MembersInGroupPredicate(groupTest);
        assertFalse(predicate.test(TypicalPersons.CARL));
    }

    @Test
    public void test_groupContainMember_returnTrue() {
        Group group = new GroupBuilder().withPerson("Group F",
                TypicalPersons.ALICE).build();
        Person personToTest = TypicalPersons.ALICE;
        MembersInGroupPredicate predicate = new MembersInGroupPredicate(group);
        assertTrue(predicate.test(personToTest));
    }
}
