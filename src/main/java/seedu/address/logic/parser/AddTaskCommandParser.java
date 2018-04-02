package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.dashboard.Task;

//@@author yapni
/**
 * Parses input arguments and create a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws NullPointerException if args is null
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args,
                PREFIX_INDEX, PREFIX_MILESTONE_INDEX, PREFIX_NAME, PREFIX_DESCRIPTION);

        if (!argMultiMap.arePrefixesPresent(PREFIX_INDEX, PREFIX_MILESTONE_INDEX, PREFIX_NAME, PREFIX_DESCRIPTION)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            Index studentIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_INDEX).get());
            Index milestoneIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_MILESTONE_INDEX).get());
            String name = argMultiMap.getValue(PREFIX_NAME).get();
            String description = argMultiMap.getValue(PREFIX_DESCRIPTION).get();

            Task task = new Task(name, description);

            return new AddTaskCommand(studentIndex, milestoneIndex, task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
