package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author yapni
/**
 * Parses input arguments and create a new DeleteTaskCommand object
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskCommand
     * and returns a DeleteTaskCommand object for execution.
     * @throws NullPointerException if args is null
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args,
                PREFIX_INDEX, PREFIX_MILESTONE_INDEX, PREFIX_TASK_INDEX);

        if (!argMultiMap.arePrefixesPresent(PREFIX_INDEX, PREFIX_MILESTONE_INDEX, PREFIX_TASK_INDEX)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        }

        try {
            Index studentIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_INDEX).get());
            Index milestoneIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_MILESTONE_INDEX).get());
            Index taskIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_TASK_INDEX).get());

            return new DeleteTaskCommand(studentIndex, milestoneIndex, taskIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
