package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.PersonBuilder;

public class UniquePersonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //@@author Caijun7
    @Test
    public void importPerson_validPerson_success() throws Exception {
        UniquePersonList uniquePersonList = new UniquePersonList();
        Person validPerson = new PersonBuilder().build();
        uniquePersonList.importPerson(validPerson);
        assertEquals(Arrays.asList(validPerson), uniquePersonList.asObservableList());
    }
    //@@author

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }
}
