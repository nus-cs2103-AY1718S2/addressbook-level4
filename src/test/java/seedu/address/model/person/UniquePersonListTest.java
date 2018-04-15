package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;

public class UniquePersonListTest {

    //@@author jonleeyz
    @Test
    public void testHashcode_symmetric() throws DuplicatePersonException {
        UniquePersonList uniquePersonListA = new UniquePersonList();
        UniquePersonList uniquePersonListB = new UniquePersonList();
        UniquePersonList uniquePersonListC = new UniquePersonList();
        UniquePersonList uniquePersonListD = new UniquePersonList();
        Person samplePerson = new Person();
        uniquePersonListC.add(samplePerson);
        uniquePersonListD.add(samplePerson);

        assertEquals(uniquePersonListA.hashCode(), uniquePersonListB.hashCode());
        assertEquals(uniquePersonListC.hashCode(), uniquePersonListD.hashCode());
        assertNotEquals(uniquePersonListA.hashCode(), uniquePersonListC.hashCode());
        assertNotEquals(uniquePersonListA.hashCode(), uniquePersonListD.hashCode());
        assertNotEquals(uniquePersonListB.hashCode(), uniquePersonListC.hashCode());
        assertNotEquals(uniquePersonListB.hashCode(), uniquePersonListD.hashCode());
    }
    //@@author
}
