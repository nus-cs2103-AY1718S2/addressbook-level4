package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.StreamCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author TeyXinHui
/**
 * Parses input arguments and creates a new StreamCommand object
 */
public class StreamCommandParser implements Parser<StreamCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StreamCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        Index targetIndex;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StreamCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        try {
            targetIndex = ParserUtil.parseIndex(nameKeywords[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StreamCommand.MESSAGE_USAGE));
        }
        if (nameKeywords.length != 2 || !nameKeywords[1].matches("-?\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StreamCommand.MESSAGE_USAGE));
        }
        int type = Integer.parseInt(nameKeywords[1]);

        return new StreamCommand(targetIndex, type);
    }

}
//@@author
