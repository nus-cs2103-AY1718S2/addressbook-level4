package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.Content;

/**
 * Parses input arguments and creates a new AddToDoCommand object
 */
public class AddToDoCommandParser implements Parser<AddToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddToDoCommand
     * and returns an AddToDoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddToDoCommand parse(String args) throws ParseException {

        try {
            Content content = ParserUtil.parseContent(args);

            ToDo todo = new ToDo(content);

            return new AddToDoCommand(todo);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE));
        }
    }
}
