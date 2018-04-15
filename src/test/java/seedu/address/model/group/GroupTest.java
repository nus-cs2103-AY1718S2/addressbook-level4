//@@author jas5469
package seedu.address.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.GroupBuilder;

public class GroupTest {
    //@@author LeonidAgarth
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Group groupA = new GroupBuilder().withInformation("Group A").build();
    private Group groupA2 = new GroupBuilder().withInformation("Group A").build();
    private Group groupB = new GroupBuilder().withInformation("Group B").build();


    @Test
    public void hashCodeAndString_test() {
        assertEquals(groupA.hashCode(), groupA.hashCode());
        assertEquals(groupA.hashCode(), groupA2.hashCode());
        assertNotEquals(groupA.hashCode(), groupB.hashCode());

        assertEquals(groupA.toString(), groupA.toString());
        assertEquals(groupA.toString(), groupA2.toString());
        assertNotEquals(groupA.toString(), groupB.toString());
    }

    @Test
    public void addPerson() throws Exception {
        groupA.addPerson(ALICE);
        assertNotEquals(groupA, groupA2);
        groupA2.addPerson(ALICE);
        assertEquals(groupA, groupA2);

        groupA.addPerson(BENSON);
        groupA2.addPerson(CARL);
        assertNotEquals(groupA, groupA2);
        groupA.addPerson(CARL);
        groupA2.addPerson(BENSON);
        assertEquals(groupA, groupA2);
    }

    @Test
    public void addPerson_duplicatePerson_throwsException() throws Exception {
        groupA.addPerson(ALICE);
        groupA.addPerson(BENSON);
        groupA.addPerson(CARL);
        thrown.expect(DuplicatePersonException.class);
        groupA.addPerson(BENSON);
    }

    @Test
    public void removePerson() throws Exception {
        groupA.addPerson(ALICE);
        groupA.addPerson(BENSON);
        groupA.addPerson(CARL);
        groupA2.addPerson(ALICE);
        groupA2.addPerson(BENSON);
        groupA2.addPerson(CARL);

        groupA.removePerson(ALICE);
        assertNotEquals(groupA, groupA2);
        groupA2.removePerson(ALICE);
        assertEquals(groupA, groupA2);

        groupA.removePerson(BENSON);
        groupA2.removePerson(CARL);
        assertNotEquals(groupA, groupA2);
        groupA.removePerson(CARL);
        groupA2.removePerson(BENSON);
        assertEquals(groupA, groupA2);
    }

    @Test
    public void removePerson_personNotFound_throwsException() throws Exception {
        groupA.addPerson(ALICE);
        groupA.addPerson(CARL);
        thrown.expect(PersonNotFoundException.class);
        groupA.removePerson(BENSON);
    }

    //@@author jas5469
    @Test
    public void compare_notEqualGroups() throws Exception {
        assertNotEquals(groupA, groupB);
    }

    @Test
    public void compare_equalGroups() throws Exception {
        assertEquals(groupA, groupA2);
    }
    //@@author
}
