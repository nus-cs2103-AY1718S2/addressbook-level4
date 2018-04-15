//@@author luca590

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import javafx.collections.ObservableList;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    //featureUnderTest_testScenario_expectedBehavior()

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
    public boolean checkSorted(Model myModel) {
        boolean isSorted = true;
        requireNonNull(myModel);
        Object[] parray = myModel.getFilteredPersonList().toArray();

        if (parray.length < 1) {
            return false;
        }

        if (!parray[0].toString().substring(0, 1).equals("A")) {
            isSorted = false;
        }
        if (!parray[1].toString().substring(0, 1).equals("B")) {
            isSorted = false;
        }
        if (!parray[2].toString().substring(0, 1).equals("C")) {
            isSorted = false;
        }

        return isSorted;
    }

    @Test
    public void executeUndoableCommand_sortImportedPersons_personsCorrectlySorted() throws Exception {
        PersonBuilder pb = new PersonBuilder();
        pb.withName("Aal");
        Person p1 = pb.build();
        pb.withName("Ccar");
        Person p2 = pb.build();
        pb.withName("Bbob");
        Person p3 = pb.build();

        SortCommand sc = new SortCommand();
        sc.model = new ModelManager();

        sc.model.addPerson(p1);
        sc.model.addPerson(p2);
        sc.model.addPerson(p3);

        assertFalse(checkSorted(sc.model));
        CommandResult cr = sc.executeUndoableCommand();
        assertTrue(checkSorted(sc.model));
    }

    @Test
    public void callSortParser_createAddressBookParser_returnSortCommand() throws ParseException {
        AddressBookParser abp = new AddressBookParser();
        Command sc1 = abp.parseCommand("sort");
        Command sc2 = abp.parseCommand("so");

        requireNonNull(sc1);
        requireNonNull(sc2);
    }

}
//@@author
