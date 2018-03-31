package seedu.address.logic.parser.job;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_POSITIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.job.JobAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.Job;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new JobAddCommand object
 */
public class JobAddCommandParser implements Parser<JobAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the JobAddCommand
     * and returns an JobAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public JobAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_POSITION, PREFIX_TEAM, PREFIX_LOCATION, PREFIX_NUMBER_OF_POSITIONS, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_POSITION, PREFIX_TEAM, PREFIX_LOCATION,
                PREFIX_NUMBER_OF_POSITIONS, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobAddCommand.MESSAGE_USAGE));
        }
        try {
            Position position = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION)).get();
            Team team = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM)).get();
            Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION)).get();
            NumberOfPositions numberOfPositions =
                    ParserUtil.parseNumberOfPositions(argMultimap.getValue(PREFIX_NUMBER_OF_POSITIONS)).get();

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Job job = new Job(position, team, location, numberOfPositions, tagList);

            return new JobAddCommand(job);
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
