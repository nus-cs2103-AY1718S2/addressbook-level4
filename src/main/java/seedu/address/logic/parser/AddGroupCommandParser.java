package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.model.person.UniquePersonList;


/**
 * Parses input arguments and creates a new AddGroupCommand object
 */
public class AddGroupCommandParser implements Parser<AddGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGroupCommand
     * and returns an AddGroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGroupCommand parse(String args) throws ParseException {

        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
        }

        try {
            Information information = ParserUtil.parseInformation(args);
            UniquePersonList personList = new UniquePersonList();
            Group group = new Group(information);

            return new AddGroupCommand(group);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
