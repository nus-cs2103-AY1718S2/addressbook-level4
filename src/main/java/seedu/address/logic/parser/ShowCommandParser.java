package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Ang-YC
/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Parse the arguments
        String requestedString = args.trim();
        ShowCommand.Panel requestedPanel = parsePanel(requestedString);
        return new ShowCommand(requestedPanel);
    }

    /**
     * Parses {@code panel} into a {@code ShowCommand.Panel} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified panel is invalid (not info or resume).
     */
    private ShowCommand.Panel parsePanel(String panel) throws ParseException {
        String trimmed = panel.trim();

        if (panel.equals(ShowCommand.PANEL_INFO)) {
            return ShowCommand.Panel.INFO;
        } else if (panel.equals(ShowCommand.PANEL_RESUME)) {
            return ShowCommand.Panel.RESUME;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }
    }
}
