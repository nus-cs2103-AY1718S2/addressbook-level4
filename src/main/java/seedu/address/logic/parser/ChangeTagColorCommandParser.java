package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses the given {@code String} of arguments in the context of the ChangeTagColorCommand
 * and returns an ChangeTagColorCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class ChangeTagColorCommandParser implements Parser<ChangeTagColorCommand> {

    @Override
    public ChangeTagColorCommand parse(String userInput) throws ParseException {
        String[] args = userInput.trim().split(" ");
        if (args.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTagColorCommand.MESSAGE_USAGE));
        }
        try {
            Tag tag = ParserUtil.parseTag(args[0]);
            String color = ParserUtil.parseColor((args[1]));
            return new ChangeTagColorCommand(tag.name, color);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
