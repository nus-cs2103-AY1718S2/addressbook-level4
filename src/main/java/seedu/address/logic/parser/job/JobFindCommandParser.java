// author kush1509
package seedu.address.logic.parser.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.job.JobFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.LocationContainsKeywordsPredicate;
import seedu.address.model.job.PositionContainsKeywordsPredicate;
import seedu.address.model.skill.JobSkillContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new JobFindCommand object
 */
public class JobFindCommandParser implements Parser<JobFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the JobFindCommand
     * and returns an JobFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public JobFindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POSITION, PREFIX_SKILL, PREFIX_LOCATION);

        if (!(arePrefixesPresent(argMultimap, PREFIX_POSITION)
                || arePrefixesPresent(argMultimap, PREFIX_SKILL)
                || arePrefixesPresent(argMultimap, PREFIX_LOCATION))
                || (arePrefixesPresent(argMultimap, PREFIX_POSITION) && arePrefixesPresent(argMultimap, PREFIX_SKILL))
                || (arePrefixesPresent(argMultimap, PREFIX_POSITION)
                && arePrefixesPresent(argMultimap, PREFIX_LOCATION))
                || (arePrefixesPresent(argMultimap, PREFIX_SKILL) && arePrefixesPresent(argMultimap, PREFIX_LOCATION))
                || (arePrefixesPresent(argMultimap, PREFIX_SKILL) && arePrefixesPresent(argMultimap, PREFIX_LOCATION)
                && arePrefixesPresent(argMultimap, PREFIX_POSITION))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_POSITION)) {
            List<String> testnovalue = argMultimap.getAllValues(PREFIX_POSITION);
            if (testnovalue.contains("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));
            }
            String[] positionKeywords = argMultimap.getValue(PREFIX_POSITION).get().split("\\W+");
            return new JobFindCommand(new PositionContainsKeywordsPredicate(Arrays.asList(positionKeywords)));
        } else if (arePrefixesPresent(argMultimap, PREFIX_SKILL)) {
            List<String> testnovalue = argMultimap.getAllValues(PREFIX_SKILL);
            if (testnovalue.contains("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));
            }
            String[] tagKeywords = argMultimap.getValue(PREFIX_SKILL).get().split("\\W+");
            return new JobFindCommand(new JobSkillContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
        } else if (arePrefixesPresent(argMultimap, PREFIX_LOCATION)) {
            List<String> testnovalue = argMultimap.getAllValues(PREFIX_LOCATION);
            if (testnovalue.contains("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));
            }
            String[] tagKeywords = argMultimap.getValue(PREFIX_LOCATION).get().split("\\W+");
            return new JobFindCommand(new LocationContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
        }   else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
