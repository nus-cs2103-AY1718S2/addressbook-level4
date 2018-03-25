package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddMilestoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.dashboard.Date;
import seedu.address.model.student.dashboard.Milestone;

/**
 * Parses input arguments and create a new AddMilestoneCommand object
 */
public class AddMilestoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMilestoneCommand
     * and returns an AddMilestoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMilestoneCommand parse(String args) throws ParseException {
        assert args != null;

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_DATE, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultiMap, PREFIX_INDEX, PREFIX_DATE, PREFIX_DESCRIPTION)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMilestoneCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_INDEX).get());
            Date date = ParserUtil.parseDate(argMultiMap.getValue(PREFIX_DATE).get());
            String objective = argMultiMap.getValue(PREFIX_DESCRIPTION).get();

            Milestone milestone = new Milestone(date, objective);

            return new AddMilestoneCommand(index, milestone);
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
