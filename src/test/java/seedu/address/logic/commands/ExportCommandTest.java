package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.export.ExportType;

public class ExportCommandTest {
    public final UserPrefs userPrefs = new UserPrefs();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_validExportCalendarCommand_success() {
        ExportType exportType = ExportType.CALENDAR;
        ExportCommand exportCommand = prepareCommand(exportType);

        String expectedMessage = ExportCommand.CALENDAR_MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.export(exportType);

        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }

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
