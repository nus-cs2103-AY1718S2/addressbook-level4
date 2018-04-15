# daviddalmaso
###### /java/seedu/address/logic/commands/CountCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class CountCommandTest {

    private Model model;
    private Model expectedModel;
    private CountCommand countCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        countCommand = new CountCommand();
        countCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_countIsReturned_listsAllPersons() {
        assertCommandSuccess(
                countCommand,
                model,
                Integer.toString(model.getAddressBook().getPersonList().size())
                        + " persons in the address book",
                expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/ExportCommandTest.java
``` java
public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public final UserPrefs userPrefs = new UserPrefs();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validExportPortfolioCommand_success() throws InvalidFileNameException {
        ExportType exportType = ExportType.PORTFOLIO;
        ExportCommand exportCommand = prepareCommand(exportType);

        String expectedMessage = String.format(ExportCommand.PORTFOLIO_MESSAGE_SUCCESS,
                userPrefs.getExportPortfolioFilePath());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.exportPortfolio(userPrefs.getExportPortfolioFilePath());

        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidFileNameExportPortoflio_failure() throws InvalidFileNameException {
        String invalidFileName = "asdf/asdf/asdf";

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        thrown.expect(InvalidFileNameException.class);

        expectedModel.exportPortfolio(invalidFileName);
    }

    /**
     * Returns a {@code ExportCommand} with the parameter {@code exportType}.
     */
    private ExportCommand prepareCommand(ExportType exportType) {
        ExportCommand exportCommand = new ExportCommand(exportType);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return exportCommand;
    }

}
```
