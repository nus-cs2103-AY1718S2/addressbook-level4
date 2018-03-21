package seedu.organizer.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.AddSubtaskCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.Name;

/**
 * Parses input arguments and creates a new AddSubtaskCommand object
 */
public class AddSubtaskCommandParser implements Parser<AddSubtaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSubtaskCommand
     * and returns an AddSubtaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSubtaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubtaskCommand.MESSAGE_USAGE));
        }

        Optional<Name> name = Optional.empty();
        try {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        Subtask toAdd = null;
        if (name.isPresent()) {
            toAdd = new Subtask(name.get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubtaskCommand.MESSAGE_USAGE));
        }

        return new AddSubtaskCommand(index, toAdd);
    }
}
