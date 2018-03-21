package seedu.recipe.logic.parser;

import seedu.recipe.logic.commands.UploadCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new UploadCommand object
 */
public class UploadCommandParser implements Parser<UploadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UploadCommand
     * and returns an UploadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] tagKeywords = trimmedArgs.split("\\s+");
        String filename = tagKeywords[0];
        if (filename.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));

        }
        String XmlExtensionFilename = ParserUtil.parseFilename(filename);

        return new UploadCommand(XmlExtensionFilename);
    }
}
