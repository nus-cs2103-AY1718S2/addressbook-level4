package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindMovieCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.movie.NameAndStartDateAndTagContainsKeywordsPredicate;
import seedu.address.model.movie.NameAndStartDateContainsKeywordsPredicate;
import seedu.address.model.movie.NameAndTagContainsKeywordsPredicate;
import seedu.address.model.movie.NameContainsKeywordsPredicate;
import seedu.address.model.movie.StartDateAndTagContainsKeywordsPredicate;
import seedu.address.model.movie.StartDateContainsKeywordsPredicate;
import seedu.address.model.movie.TagContainsKeywordsPredicate;
//@@author slothhy
/**
 * Parses input arguments and creates a new FindMovieCommand object
 */
public class FindMovieCommandParser implements Parser<FindMovieCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMovieCommand
     * and returns an FindMovieCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMovieCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_STARTDATE, PREFIX_TAG);

        if ((!arePrefixesPresent(argMultimap, PREFIX_NAME) && !arePrefixesPresent(argMultimap, PREFIX_STARTDATE)
                && !arePrefixesPresent(argMultimap, PREFIX_TAG))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMovieCommand.MESSAGE_USAGE));
        }

        String trimmedArgs = args.trim();
        String nameValues;
        String startDateValues;
        String tagValues;
        String[] nameKeywords;
        String[] startDateKeywords;
        String[] tagKeywords;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMovieCommand.MESSAGE_USAGE));
        } else {
            if (!argMultimap.getAllValues(PREFIX_NAME).isEmpty()
                    && !argMultimap.getAllValues(PREFIX_STARTDATE).isEmpty()
                    && !argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
                //name, startDate, tag present
                nameValues = argMultimap.getValue(PREFIX_NAME).get();
                startDateValues = argMultimap.getValue(PREFIX_STARTDATE).get();
                tagValues = argMultimap.getValue(PREFIX_TAG).get();
                nameKeywords = nameValues.split("\\s+");
                startDateKeywords = startDateValues.split("\\s+");
                tagKeywords = tagValues.split("\\s+");
                return new FindMovieCommand(new NameAndStartDateAndTagContainsKeywordsPredicate(
                        Arrays.asList(nameKeywords), Arrays.asList(startDateKeywords), Arrays.asList(tagKeywords)));
            } else if (!argMultimap.getAllValues(PREFIX_NAME).isEmpty()
                    && !argMultimap.getAllValues(PREFIX_STARTDATE).isEmpty()) {
                //name and startDate present
                nameValues = argMultimap.getValue(PREFIX_NAME).get();
                startDateValues = argMultimap.getValue(PREFIX_STARTDATE).get();
                nameKeywords = nameValues.split("\\s+");
                startDateKeywords = startDateValues.split("\\s+");
                return new FindMovieCommand(new NameAndStartDateContainsKeywordsPredicate(
                        Arrays.asList(nameKeywords), Arrays.asList(startDateKeywords)));
            } else if (!argMultimap.getAllValues(PREFIX_NAME).isEmpty()
                    && !argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
                //name and tag present
                nameValues = argMultimap.getValue(PREFIX_NAME).get();
                tagValues = argMultimap.getValue(PREFIX_TAG).get();
                nameKeywords = nameValues.split("\\s+");
                tagKeywords = tagValues.split("\\s+");
                return new FindMovieCommand(new NameAndTagContainsKeywordsPredicate(
                        Arrays.asList(nameKeywords), Arrays.asList(tagKeywords)));
            } else if (!argMultimap.getAllValues(PREFIX_STARTDATE).isEmpty()
                    && !argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
                //startDate and tag present
                startDateValues = argMultimap.getValue(PREFIX_STARTDATE).get();
                tagValues = argMultimap.getValue(PREFIX_TAG).get();
                startDateKeywords = startDateValues.split("\\s+");
                tagKeywords = tagValues.split("\\s+");
                return new FindMovieCommand(new StartDateAndTagContainsKeywordsPredicate(
                        Arrays.asList(startDateKeywords), Arrays.asList(tagKeywords)));
            } else if (!argMultimap.getAllValues(PREFIX_NAME).isEmpty()) {
                //name present only
                nameValues = argMultimap.getValue(PREFIX_NAME).get();
                nameKeywords = nameValues.split("\\s+");
                return new FindMovieCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            } else if (!argMultimap.getAllValues(PREFIX_STARTDATE).isEmpty()) {
                //startDate present only
                startDateValues = argMultimap.getValue(PREFIX_STARTDATE).get();
                startDateKeywords = startDateValues.split("\\s+");
                return new FindMovieCommand(new StartDateContainsKeywordsPredicate(Arrays.asList(startDateKeywords)));
            } else if (!argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
                //tag present only
                tagValues = argMultimap.getValue(PREFIX_TAG).get();
                tagKeywords = tagValues.split("\\s+");
                return new FindMovieCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
            } else {
                throw new ParseException("Wrong format");
            }
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
