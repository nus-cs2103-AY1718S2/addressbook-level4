package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURIES_HISTORY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddInjuriesHistoryCommand;
import seedu.address.logic.commands.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author chuakunhong

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class AddInjuriesHistoryCommandParser implements Parser<AddInjuriesHistoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddInjuriesHistoryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INJURIES_HISTORY);

        if (!argMultimap.arePrefixesPresent(PREFIX_INJURIES_HISTORY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    AddInjuriesHistoryCommand.MESSAGE_USAGE));
        }
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    AddInjuriesHistoryCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (ParserUtil.parseInjuriesHistory(argMultimap.getValue(PREFIX_INJURIES_HISTORY)).get().toString().isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                        AddInjuriesHistoryCommand.MESSAGE_USAGE)));
        } else {
            ParserUtil.parseInjuriesHistory(argMultimap.getValue(PREFIX_INJURIES_HISTORY))
                    .ifPresent(editPersonDescriptor::setInjuriesHistory);
        }
        return new AddInjuriesHistoryCommand(index, editPersonDescriptor);
    }
    //@@author
}
