package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_GRADUATION_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE_POINT_AVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;

import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    //@@author mhq199657
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXPECTED_GRADUATION_YEAR, PREFIX_RATING,
                        PREFIX_GRADE_POINT_AVERAGE, PREFIX_INTERVIEW_DATE);

        if (!isValidFilterCommandInput(argMultimap,
                PREFIX_EXPECTED_GRADUATION_YEAR, PREFIX_RATING, PREFIX_GRADE_POINT_AVERAGE, PREFIX_INTERVIEW_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        try {
            Predicate<Person> expectedGraduationYearPredicate = FilterUtil.parseExpectedGraduationYear(argMultimap
                    .getValue(PREFIX_EXPECTED_GRADUATION_YEAR));
            Predicate<Person> ratingPredicate = FilterUtil.parseRating(argMultimap.getValue(PREFIX_RATING));
            Predicate<Person> gpaPredicate =
                    FilterUtil.parseGradePointAverage(argMultimap.getValue(PREFIX_GRADE_POINT_AVERAGE));
            Predicate<Person> interviewDatePredicate =
                    FilterUtil.parseInterviewDate(argMultimap.getValue(PREFIX_INTERVIEW_DATE));
            // combine all predicates together using and
            Predicate<Person> combinedPredicate = combinePredicate(expectedGraduationYearPredicate,
                    ratingPredicate, gpaPredicate, interviewDatePredicate);
            return new FilterCommand(combinedPredicate);
        } catch (ParseException pe) {
            throw pe;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * checks whether the user input is of the correct format in the sense that it contains at least 1 prefix
     * @param argumentMultimap Parsed user input
     * @param prefixes Supported prefixes
     * @return whether the input is valid
     */
    private boolean isValidFilterCommandInput(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        boolean hasAnyPrefixes = false;
        for (Prefix p: prefixes) {
            if (arePrefixesPresent(argumentMultimap, p)) {
                hasAnyPrefixes = true;
                break;
            }
        }
        return hasAnyPrefixes;
    }

    /**
     * combines all the predicate into one predicate AND-connected
     * @param predicates all the predicates to be combined
     * @return a single predicate
     */
    private Predicate<Person> combinePredicate(Predicate<Person>... predicates) {
        Predicate<Person> combinedPredicate = null;
        for (Predicate<Person> p: predicates) {
            if (p != null) {
                if (combinedPredicate == null) {
                    combinedPredicate = p;
                } else {
                    combinedPredicate = combinedPredicate.and(p);
                }
            }
        }
        return combinedPredicate;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
