package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.UploadCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Caijun7
/**
 * Parses input arguments and creates a new UploadCommand object
 */
public class UploadCommandParser implements Parser<UploadCommand> {
    private static final String SPLIT_TOKEN = " ";
    /**
     * Parses the given {@code String} of arguments in the context of the UploadCommand
     * and returns an UploadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));
        }
        String[] splitArgs = trimmedArgs.split(SPLIT_TOKEN);
        if (splitArgs.length == 1) {
            return new UploadCommand(splitArgs[0]);
        } else if (splitArgs.length == 2) {
            return new UploadCommand(splitArgs[0], splitArgs[1]);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));
        }

    }
}
