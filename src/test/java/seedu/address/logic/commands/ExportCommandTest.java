package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.export.ExportType;
import seedu.address.model.export.exceptions.InvalidFileNameException;

//@@author daviddalmaso
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
