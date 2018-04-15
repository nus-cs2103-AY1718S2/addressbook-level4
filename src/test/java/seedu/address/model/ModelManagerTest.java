package seedu.address.model;

import static org.hamcrest.CoreMatchers.instanceOf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersonsAndAppointments.ALICE;
import static seedu.address.testutil.TypicalPersonsAndAppointments.ALICE_APPT;
import static seedu.address.testutil.TypicalPersonsAndAppointments.BENSON;
import static seedu.address.testutil.TypicalPersonsAndAppointments.BENSON_APPT;
import static seedu.address.testutil.TypicalPersonsAndAppointments.CARL_APPT;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.person.NameContainsFullKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    //@@author jlks96
    @Test
    public void getFilteredAppointmentList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredAppointmentList().remove(0);
    }
    //@@author

    //@@author ng95junwei
    @Test
    public void getTemplateList_return_observableList() {
        ModelManager modelManager = new ModelManager();
        assertThat(modelManager.getAllTemplates(), instanceOf(ObservableList.class));
    }
    //@@author

    //@@author luca590
    @Test
    public void fromModelManagerSortAddressBookAlphabeticallyByName_simpleFunctionCallWithValidData_sortedList()
            throws DuplicatePersonException {
        ModelManager testModel = new ModelManager();

        PersonBuilder pb = new PersonBuilder();
        Person p1 = pb.build();
        testModel.addPerson(p1);

        pb.withName("Car");
        Person p2 = pb.build();
        testModel.addPerson(p2);

        pb.withName("Bob");
        Person p3 = pb.build();
        testModel.addPerson(p3);


        testModel.sortAddressBookAlphabeticallyByName();

        Object[] parray = testModel.getFilteredPersonList().toArray();
        assertTrue(parray.length > 0);

        //substrings are used in testing to avoid the Date Added parameter
        assertEquals(parray[0].toString().substring(0, 5), "Alice");
        assertEquals(parray[1].toString().substring(0, 3), "Bob");
        assertEquals(parray[2].toString().substring(0, 3), "Car");
    }
    //@@ author

    @Test
    public void equals() throws DuplicateAppointmentException, AppointmentNotFoundException {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withAppointment(ALICE_APPT).withAppointment(BENSON_APPT).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredPersonList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsFullKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        //@@author jlks96
        //different filteredAppointmentList -> returns false
        modelManager.addAppointment(CARL_APPT);
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));
        //@@author

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.deleteAppointment(CARL_APPT);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
