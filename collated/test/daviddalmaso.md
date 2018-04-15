# daviddalmaso
###### /java/seedu/address/logic/commands/ExportCommandTest.java
``` java
public class ExportCommandTest {
    public final UserPrefs userPrefs = new UserPrefs();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validExportPortfolioCommand_success() {
        ExportType exportType = ExportType.PORTFOLIO;
        ExportCommand exportCommand = prepareCommand(exportType);

        String expectedMessage = String.format(ExportCommand.PORTFOLIO_MESSAGE_SUCCESS,
                userPrefs.getExportPortfolioFilePath());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.export(exportType);

        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
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
