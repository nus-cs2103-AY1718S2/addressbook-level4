package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.ExpectedGraduationYearInKeywordsRangePredicate;
import seedu.address.model.person.Person;

/**
 * A utility class for parsing FilterCommand
 */
public class FilterUtil {
    /**
     * Parses a Optional of  predicateString to a Predicate used to filter Person
     * @param predicateString a predicate string read from user input
     * @return a Predicate for filter command
     * @throws IllegalValueException
     */
    public static Predicate<Person> parseExpectedGraduationYear(Optional<String> predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        if (predicateString.isPresent()) {
            return parseExpectedGraduationYear(predicateString.get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses a predicateString to a Predicate used to filter Person
     * @param predicateString a predicate string read from user input
     * @return a Predicate for filter command
     * @throws IllegalValueException
     */
    public static Predicate<Person> parseExpectedGraduationYear(String predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        String[] predicateStrings = predicateString.split(",");
        Arrays.stream(predicateStrings).map(String::trim).toArray(unused -> predicateStrings);
        if (predicateStrings.length == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        Predicate<Person> predicate = processExpectedGraduationYearPredicateStrings(predicateStrings);
        return predicate;

    }

    /**
     * Parses the string array of all single predicate strings to a predicate
     * @param predicateStrings array of predicateString
     * @return the predicate user demanded
     * @throws IllegalValueException
     */
    private static Predicate<Person> processExpectedGraduationYearPredicateStrings(String[] predicateStrings)
            throws IllegalValueException {
        List<Predicate<Person>> allPredicates = new ArrayList<Predicate<Person>>();
        for (String s: predicateStrings) {
            Predicate<Person> predicate = formExpectedGraduationYearPredicateFromPredicateString(s);
            allPredicates.add(predicate);
        }
        return combineAllPredicates(allPredicates);

    }

    /**
     * Combines a list of predicate into a single predicate
     * @param predicateList list of predicates
     * @return a single equivalent predicate
     */
    private static Predicate<Person> combineAllPredicates(List<Predicate<Person>> predicateList) {
        assert(predicateList.size() >= 1);
        Predicate<Person> allPredicates = predicateList.get(0);
        for (int i = 1; i < predicateList.size(); i++) {
            allPredicates = allPredicates.or(predicateList.get(i));
        }
        return allPredicates;
    }

    /**
     * Form a single predicate from a single predicate string
     * @param s a single predicate string
     * @return a single predicate
     * @throws IllegalValueException
     */
    private static Predicate<Person> formExpectedGraduationYearPredicateFromPredicateString(String s)
            throws IllegalValueException {
        FilterRange<ExpectedGraduationYear> filterRange;
        if (s.contains("-")) { //It is a range
            String[] range = s.split("-");
            if (range.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            } else if (ExpectedGraduationYear.isValidExpectedGraduationYear(range[0].trim())
                    && ExpectedGraduationYear.isValidExpectedGraduationYear(range[1].trim())) {
                filterRange = new FilterRange<ExpectedGraduationYear>(
                        new ExpectedGraduationYear(range[0].trim()), new ExpectedGraduationYear(range[1].trim()));
            } else {
                throw new IllegalValueException(ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
            }

        } else { //It is a value instead
            if (ExpectedGraduationYear.isValidExpectedGraduationYear(s.trim())) {
                filterRange = new FilterRange<ExpectedGraduationYear>(new ExpectedGraduationYear(s));
            } else {
                throw new IllegalValueException(ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
            }
        }
        Predicate<Person> predicate = new ExpectedGraduationYearInKeywordsRangePredicate(filterRange);
        return predicate;
    }
}
