package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

//@@author Ang-YC
public class PersonTest {

    @Test
    public void infoEquals() {
        Person alice = TypicalPersons.ALICE;
        Person clone = new PersonBuilder(alice).build();

        // Same instance
        assertTrue(alice.infoEquals(alice));

        // Clone
        assertTrue(alice.infoEquals(clone));

        // Different people
        assertFalse(alice.infoEquals(TypicalPersons.BOB));
    }
}
