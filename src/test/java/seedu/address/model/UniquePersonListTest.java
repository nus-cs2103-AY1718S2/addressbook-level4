package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.TypicalAddressBook;
import seedu.address.testutil.TypicalPersons;

public class UniquePersonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }

    @Test
    public void sort_ascendingOrder_success() {
        // Setup actual result
        AddressBook addressBook = TypicalAddressBook.getTypicalAddressBook();
        addressBook.sortPersons();
        ObservableList<Person> actualPersonList = addressBook.getPersonList();

        // Setup expected result
        List<Person> personList = TypicalPersons.getTypicalPersons();
        personList.sort((person1, person2) -> person1.getName().toString()
                .compareToIgnoreCase(person2.getName().toString()));
        ObservableList<Person> expectedPersonList = FXCollections.observableList(personList);

        assertEquals(actualPersonList, expectedPersonList);
    }

    @Test
    public void sort_descendingOrder_fail() {
        // Setup actual result
        AddressBook addressBook = TypicalAddressBook.getTypicalAddressBook();
        addressBook.sortPersons();
        ObservableList<Person> actualPersonList = addressBook.getPersonList();

        // Setup expected result
        List<Person> personList = TypicalPersons.getTypicalPersons();
        personList.sort((person1, person2) -> person2.getName().toString()
                .compareToIgnoreCase(person1.getName().toString()));
        ObservableList<Person> expectedPersonList = FXCollections.observableList(personList);

        assertNotEquals(actualPersonList, expectedPersonList);
    }
}
