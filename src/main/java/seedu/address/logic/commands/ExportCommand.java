package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.UserPrefs;
import seedu.address.model.export.ExportType;
import seedu.address.model.export.exceptions.CalendarAccessDeniedException;
import seedu.address.model.export.exceptions.ConnectivityIssueException;
import seedu.address.model.export.exceptions.InvalidFileNameException;

//@@author daviddalmaso
/**
 * Export different types of data from the application to the user
 */
public class ExportCommand extends UndoableCommand {

    public static final String CALENDAR_ACCESS_DENIED_MESSAGE =
            "Unable to export calendar: access denied";

    public static final String CALENDAR_MESSAGE_SUCCESS =
            "Successfully exported birthdays and appointments to Google Calendar";

    public static final String COMMAND_WORD = "export";

    public static final String CONNECTIVITY_ISSUE_MESSAGE =
            "Unable to export calendar: no internet connection";

    public static final String INVALID_FILE_MESSAGE =
            "Unable to export portfolio: invalid filename";

    public static final String PORTFOLIO_MESSAGE_SUCCESS =
            "Successfully exported portfolio to %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the specified type of content.\n"
            + "Parameters: ExportType (must be one of the following - calendar, portfolio)\n"
            + "Example: " + COMMAND_WORD + " portfolio";


    private static final Logger logger = LogsCenter.getLogger(ExportCommand.class);

    public final UserPrefs userPrefs = new UserPrefs();

    private ExportType typeToExport;
    private String filePath;

    public ExportCommand(ExportType typeToExport) {
        this.typeToExport = typeToExport;
        this.filePath = userPrefs.getExportPortfolioFilePath();
    }

    public ExportCommand(ExportType typeToExport, String filePath) {
        this.typeToExport = typeToExport;
        if (filePath == null || filePath.isEmpty()) {
            this.filePath = userPrefs.getExportPortfolioFilePath();
        } else {
            this.filePath = filePath;
        }
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        requireNonNull(typeToExport);
        try {
            if (typeToExport.equals(ExportType.PORTFOLIO)) {
                model.exportPortfolio(filePath);
                logger.info("Successfully exported portfolio");
                return new CommandResult(String.format(PORTFOLIO_MESSAGE_SUCCESS,
                        filePath));
            } else {
                model.exportCalendar();
                logger.info("Successfully exported calendar");
                return new CommandResult(CALENDAR_MESSAGE_SUCCESS);
            }
        } catch (InvalidFileNameException e) {
            logger.info("Unable to use " + filePath + " for export file path");
            throw new CommandException(INVALID_FILE_MESSAGE);
        } catch (CalendarAccessDeniedException e) {
            logger.info("Did not receive authorization for Google Calendar usage");
            throw new CommandException(CALENDAR_ACCESS_DENIED_MESSAGE);
        } catch (ConnectivityIssueException e) {
            logger.info("Unable to connect to the internet to export calendar");
            throw new CommandException(CONNECTIVITY_ISSUE_MESSAGE);
        }
    }
}
