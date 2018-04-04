package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.UserPrefs;
import seedu.address.model.export.ExportType;

/**
 * Export different types of data from the application to the user
 */
public class ExportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the specified type of content.\n"
            + "Parameters: ExportType (must be one of the following - calendar, portfolio)\n"
            + "Example: " + COMMAND_WORD + " portfolio";

    public static final String PORTFOLIO_MESSAGE_SUCCESS =
            "Successfully exported portfolio to %1$s";

    public static final String CALENDAR_MESSAGE_SUCCESS =
            "Successfully exported birthdays and appointments to Google Calendar";

    public final UserPrefs userPrefs = new UserPrefs();

    private ExportType typeToExport;

    public ExportCommand(ExportType typeToExport) {
        this.typeToExport = typeToExport;
    }

    @Override
    protected CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.export(typeToExport);
        if (typeToExport.equals(ExportType.PORTFOLIO)) {
            return new CommandResult(String.format(PORTFOLIO_MESSAGE_SUCCESS,
                    userPrefs.getExportPortfolioFilePath()));
        } else {
            return new CommandResult(CALENDAR_MESSAGE_SUCCESS);
        }
    }
}
