# software-1234
###### /java/seedu/address/logic/commands/ListBalancesCommandTest.java
``` java

package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.money.Money;

public class ListBalancesCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executes_updatesFilteredListAccordingly() throws Exception {
        model.getFilteredPersonList().get(0).setMoney(new Money("100"));

        ListPositiveBalanceCommand listPositiveBalanceCommand = new ListPositiveBalanceCommand();
        listPositiveBalanceCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listPositiveBalanceCommand.execute();

        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            assertEquals(model.getAddressBook().getPersonList().get(i), model.getFilteredPersonList().get(i));
        }

        model.getAddressBook().getPersonList().get(0).setMoney(new Money("-100"));
        model.getAddressBook().getPersonList().get(1).setMoney(new Money("-150"));
        model.getAddressBook().getPersonList().get(3).setMoney(new Money("-200"));

        ListNegativeBalanceCommand listNegativeBalanceCommand = new ListNegativeBalanceCommand();
        listNegativeBalanceCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listNegativeBalanceCommand.execute();

        assertEquals(model.getAddressBook().getPersonList().get(0), model.getFilteredPersonList().get(0));
        assertEquals(model.getAddressBook().getPersonList().get(1), model.getFilteredPersonList().get(1));
        assertEquals(model.getAddressBook().getPersonList().get(3), model.getFilteredPersonList().get(2));

    }

    @Test
    public void noPositiveOrNegativeAmounts() throws Exception {

        ListNegativeBalanceCommand listNegativeBalanceCommand = new ListNegativeBalanceCommand();
        listNegativeBalanceCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listNegativeBalanceCommand.execute();

        assertEquals(0, model.getFilteredPersonList().size());

        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            model.getAddressBook().getPersonList().get(i).setMoney(new Money("-100"));
        }

        ListPositiveBalanceCommand listPositiveBalanceCommand = new ListPositiveBalanceCommand();
        listPositiveBalanceCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listPositiveBalanceCommand.execute();

        assertEquals(0, model.getFilteredPersonList().size());

    }
}
```
###### /java/seedu/address/logic/commands/WipeCommandTest.java
``` java
public class WipeCommandTest {
    private Model model;
    private Model expectedModel;
    private WipeBalancesCommand wipeBalancesCommand;
    private double balance;


    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        wipeBalancesCommand = new WipeBalancesCommand();

        wipeBalancesCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        wipeBalancesCommand.execute();

    }

    @Test
    public void executes_getsWipeSuccess() {
        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            assertEquals(0.0, model.getFilteredPersonList().get(i).getMoney().balance, 0.001);
        }

    }
}
```
