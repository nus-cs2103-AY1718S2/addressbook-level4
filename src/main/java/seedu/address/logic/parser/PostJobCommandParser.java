package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PostJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobTitle;
import seedu.address.model.job.Location;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

/***
 * Parses input arguments in the context of the PostJobCommand
 * and creates a new PostJobCommand object
 */

public class PostJobCommandParser implements Parser<PostJobCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PostJobCommand
     * and returns an PostJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PostJobCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_JOBTITLE, PREFIX_LOCATION, PREFIX_SKILLS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_JOBTITLE, PREFIX_LOCATION, PREFIX_SKILLS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PostJobCommand.MESSAGE_USAGE));
        }

        try {
            JobTitle jobTitle = ParserUtil.parseJobTitle(argMultimap.getValue(PREFIX_JOBTITLE)).get();
            Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION)).get();
            Skill skills = ParserUtil.parseSkill(argMultimap.getValue(PREFIX_SKILLS)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Job job = new Job(jobTitle, location, skills, tagList);

            return new PostJobCommand(job);
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
