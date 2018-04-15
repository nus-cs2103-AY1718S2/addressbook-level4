package seedu.progresschecker.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.UploadCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

//@@author Livian1107
/**
 * Parses input arguments and creates a new UploadCommand object
 */
public class UploadCommandParser  implements Parser<UploadCommand>  {
    /**
     * Parses the given {@code String} of arguments in the context of the UploadCommand
     * and returns an UploadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        String[] content = args.trim().split(" ");
        try {
            index = ParserUtil.parseIndex(content[0]);
            return new UploadCommand(index, content[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));
        } catch (IOException e) {
            throw new ParseException(
                    UploadCommand.MESSAGE_IMAGE_NOT_FOUND);
        }

    }
}
