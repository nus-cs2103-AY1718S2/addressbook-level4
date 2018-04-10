package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Task;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRIORITY,
                                            PREFIX_DEADLINE, PREFIX_DESCRIPTION,
                                            PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DEADLINE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            //@@author dominickenn
            Priority priority;
            if (arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
                priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)).get();
            } else {
                priority = ParserUtil.parsePriority(Priority.LOWEST_PRIORITY_LEVEL);
            }
            //@@author
            Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE)).get();
            Description description;
            if (arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)) {
                description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION)).get();
            } else {
                description = ParserUtil.parseDescription("");
            }
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Task task = new Task(name, priority, deadline, description, tagList);

            return new AddCommand(task);
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
