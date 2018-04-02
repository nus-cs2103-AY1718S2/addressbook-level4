package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteMilestoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMilestoneCommand object
 */
public class DeleteMilestoneCommandParser implements Parser<DeleteMilestoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMilestoneCommand
     * and returns an DeleteMilestoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMilestoneCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_MILESTONE_INDEX);

        if (!argMultimap.arePrefixesPresent(PREFIX_INDEX, PREFIX_MILESTONE_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteMilestoneCommand.MESSAGE_USAGE));
        }

        try {
            Index studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
            Index milestoneIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MILESTONE_INDEX).get());

            return new DeleteMilestoneCommand(studentIndex, milestoneIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
