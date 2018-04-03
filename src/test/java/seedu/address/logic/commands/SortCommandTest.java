//@@author luca590

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import javafx.collections.ObservableList;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

public class SortCommandTest {

    private Model testModel;

    //featureUnderTest_testScenario_expectedBehavior()

    /**
     *  helper function to set up model and import contacts to sort
     */
    public void setupModelWithImportedContacts() throws Exception {
        ImportContactsCommand icc = new ImportContactsCommand("data/Test_contacts_unsorted.csv");
        icc.model = new ModelManager();
        icc.executeUndoableCommand();
        testModel = icc.model;
    }

    /**
     * helper function to print people given model parameter
     * useful for debugging
     */
    public void printPeople(Model printModel) throws Exception {
        ReadOnlyAddressBook myBook = printModel.getAddressBook();
        ObservableList<Person> myPersonList = myBook.getPersonList();
        Iterator personIterator = myPersonList.iterator();

        Person p;
        while (personIterator.hasNext()) {
            p = (Person) personIterator.next();
            System.out.println(p.getName() + ", " + p.getEmail() + ", "
                    + p.getAddress() + ", " + p.getPhone());
        }
    }

    /**
     * helper function to check if PersonList in model is sorted
     * this function is contingent on importing "data/Test_contacts_unsorted.csv"
     * and not another file
     */
    public boolean checkSorted() {
        boolean isSorted = true;
        requireNonNull(testModel);
        Object[] parray = testModel.getFilteredPersonList().toArray();

        if (!parray[0].toString().substring(0, 1).equals("A")) {
            isSorted = false;
        }
        if (!parray[1].toString().substring(0, 1).equals("B")) {
            isSorted = false;
        }
        if (!parray[2].toString().substring(0, 1).equals("C")) {
            isSorted = false;
        }
        if (!parray[3].toString().substring(0, 1).equals("D")) {
            isSorted = false;
        }
        if (!parray[4].toString().substring(0, 1).equals("E")) {
            isSorted = false;
        }
        if (!parray[5].toString().substring(0, 1).equals("G")) {
            isSorted = false;
        }
        if (!parray[6].toString().substring(0, 1).equals("H")) {
            isSorted = false;
        }

        return isSorted;
    }

    @Test
    public void sortAddressBookInAddessBook_functionCallWithImportedData_personsAreInCorrectOrder()
            throws DuplicatePersonException, Exception {
        setupModelWithImportedContacts();
        assertFalse(checkSorted());
        testModel.sortAddressBookAlphabeticallyByName();
        assertTrue(checkSorted());
    }
}
//@@author
