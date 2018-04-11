//@@author jas5469
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddMembersToGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;

/**
 * Parses input arguments and creates a new AddMembersToGroupCommand object
 */
public class AddMembersToGroupCommandParser implements Parser<AddMembersToGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMembersToGroupCommand
     * and returns an AddMembersToGroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMembersToGroupCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMembersToGroupCommand.MESSAGE_USAGE));
        }

        try {
            Index index= ParserUtil.parseIndex(argMultimap.getPreamble());
            Information information = ParserUtil.parseInformation(argMultimap.getValue(PREFIX_GROUP).get());
            Group group = new Group(information);
            return new AddMembersToGroupCommand(index, group);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
