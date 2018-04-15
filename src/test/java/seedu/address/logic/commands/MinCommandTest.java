//@@author Articho28

package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.testng.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.money.Money;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Tests the output of the MinCommand
 */

public class MinCommandTest {

    private Model expectedModelNoResultsFound;
    private Model expectedModelResultsFound;
    private int indexOfLowestBalance;
    private MinCommand minCommandNoResultsFound;
    private MinCommand minCommandResultsFound;
    private Model model;
    private Person actualPersonSelected;
    private Person expectedPersonSelected;


    @Before
    public void setUp() {
        expectedModelNoResultsFound = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModelResultsFound = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        indexOfLowestBalance = getIndexOfPersonWithLowestBalance(model.getAddressBook());

        minCommandNoResultsFound = new MinCommand();
        minCommandNoResultsFound.setData(model, new CommandHistory(), new UndoRedoStack());
        minCommandResultsFound = new MinCommand();
        minCommandResultsFound.setData(model, new CommandHistory(), new UndoRedoStack());

    }

    /**
     * Checks that it does nothing if all balances are positive.
     */
    @Test
    public void executeFindsNoResults() {
        if (indexOfLowestBalance == -1) {
            assertCommandSuccess(minCommandNoResultsFound, model,
                    MinCommand.MESSAGE_SUCCESS_NO_RESULT, expectedModelNoResultsFound);
        }
    }

    /**
     * Finds the same person and checks if their balance is the same.
     *
     * @throws DuplicatePersonException
     * @throws PersonNotFoundException
     */
    @Test
    public void executeFindsSamePerson() throws DuplicatePersonException, PersonNotFoundException {
        insertNegativeBalance(model, DANIEL, new Money("-10"));
        insertNegativeBalance(expectedModelResultsFound, DANIEL, new Money("-10"));
        indexOfLowestBalance = getIndexOfPersonWithLowestBalance(expectedModelResultsFound.getAddressBook());
        expectedPersonSelected = expectedModelResultsFound.getFilteredPersonList().get(indexOfLowestBalance);
        minCommandResultsFound.execute();
        actualPersonSelected = model.getFilteredPersonList().get(indexOfLowestBalance);
        assertExecutionSuccess(expectedPersonSelected, actualPersonSelected);
        assertTrue(actualPersonSelected.getMoney().value == expectedPersonSelected.getMoney().value);
    }

    //@@ author
    @Test
    public void executes_updatedBalanceAccordingly() throws Exception {
        model.getFilteredPersonList().get(0).setMoney(new Money("-100"));

        MinCommand minCommand = new MinCommand();
        minCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult commandResult = minCommand.execute();

        assertEquals(commandResult.feedbackToUser,
                MinCommand.MESSAGE_SUCCESS_FOUND + model.getFilteredPersonList().get(0).getName());
    }

    //@@author Articho28
    /**
     * Gets the index number of the person with the lowest balance. Returns -1 if all balances are non-negative.
     *
     * @param addressBook
     * @return
     */
    public int getIndexOfPersonWithLowestBalance(ReadOnlyAddressBook addressBook) {
        List<Person> persons = addressBook.getPersonList();
        int index = 0;
        double lowestBalance = 0.0;
        for (Person p : persons) {
            double currentBalance = p.getMoney().balance;
            if (currentBalance < lowestBalance) {
                index = persons.indexOf(p);
                lowestBalance = currentBalance;
            }
        }
        if (lowestBalance == 0.0) {
            index = -1;
            return index;
        }
        return index;
    }

    /**
     * Inserts a person with a negative balance instead
     *
     * @param modelToChange
     * @param toReplace
     * @param money
     * @throws PersonNotFoundException
     * @throws DuplicatePersonException
     */

    public void insertNegativeBalance(Model modelToChange, Person toReplace, Money money)
            throws PersonNotFoundException, DuplicatePersonException {
        Person edited = new Person(toReplace.getName(),
                toReplace.getPhone(),
                toReplace.getEmail(),
                toReplace.getAddress(),
                money,
                toReplace.getTags(),
                toReplace.getItems());
        modelToChange.updatePerson(toReplace, edited);
    }

    /**
     * Checks if selected people are the same in both models.
     */
    static void assertExecutionSuccess(Person expectedSelection, Person actualSelection) {
        assertTrue(expectedSelection.equals(actualSelection));
    }
}
