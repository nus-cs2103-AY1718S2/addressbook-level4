package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ShowDashboardCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author yapni
/**
 * Parses input arguments and creates a new ShowDashboardCommand object
 */
public class ShowDashboardCommandParser implements Parser<ShowDashboardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowDashboardCommand
     * and returns a ShowDashboardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowDashboardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ShowDashboardCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowDashboardCommand.MESSAGE_USAGE));
        }
    }
}
