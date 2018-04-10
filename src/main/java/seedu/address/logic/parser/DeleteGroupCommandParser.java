package seedu.address.logic.parser;
//@@author SuxianAlicia
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Group;

/**
 * Parses the given {@code String} of arguments in the context of the DeleteGroupCommand
 * and returns an DeleteGroupCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {

    @Override
    public DeleteGroupCommand parse(String userInput) throws ParseException {
        try {
            Group group = ParserUtil.parseGroup(userInput);
            return new DeleteGroupCommand(group);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
        }
    }
}
