# software-1234
###### /java/seedu/address/logic/commands/CurrencyCommandTest.java
``` java
public class CurrencyCommandTest {
    private CurrencyCommand currencyCommand;


    @Before
    public void setUp() {
        Model model = new ModelManager();
        currencyCommand = new CurrencyCommand(INDEX_FIRST_PERSON, "USD", "SGD");
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
