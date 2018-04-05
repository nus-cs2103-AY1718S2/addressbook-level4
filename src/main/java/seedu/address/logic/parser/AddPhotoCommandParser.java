package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Scanner;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author crizyli
/**
 * Parses arguments for the AddPhotoCommand'
 */
public class AddPhotoCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPhotoCommand
     * and returns an AddPhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPhotoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        Scanner sc = new Scanner(trimmedArgs);
        if (!sc.hasNextInt()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(((Integer) sc.nextInt()).toString());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }

        if (!sc.hasNextLine()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }
        String path = sc.nextLine().trim();

        return new AddPhotoCommand(index, path);
    }
}
